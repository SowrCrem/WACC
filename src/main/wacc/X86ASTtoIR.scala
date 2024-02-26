package wacc

import scala.collection.mutable._

object X86IRGenerator {

  /** The library function generator
    */
  val lib: LibFunGenerator = new LibFunGenerator()

  val regTracker = new RegisterTracker

  val stringLiterals: Map[String, String] = Map()
  var rodataDirectives: ListBuffer[Directive] = ListBuffer()

  /** Adds a string literal to the rodata section of the assembly code
    * @param literal
    *   \- The string literal to be added
    * @return
    *   The label of the string literal
    */
  def addStringLiteral(literal: String): String = {
    stringLiterals.getOrElseUpdate(
      literal, {
        val label = s"string${stringLiterals.size}"
        rodataDirectives += Directive(s"int ${literal.length()}")
        rodataDirectives += Directive(s"$label: .asciz \"$literal\"")
        label
      }
    )
  }

  /** Generates the intermediate representation for the given AST
    * @param ast
    *   \- The AST of the program to be translated
    * @return
    *   The intermediate representation of the program
    */
  def generateIR(ast: Program): Buffer[Instruction] = {
    val instructions: Buffer[Instruction] = new ListBuffer[Instruction].empty

    // Add the necessary directives
    val initDirectives = List(
      Directive("intel_syntax noprefix"),
      Directive("globl main")
    )

    val mainInitialisation = ListBuffer(
      Directive("text"),
      Label("main"),
      PushRegisters(List(FP), InstrSize.fullReg),
      Mov(FP, SP, InstrSize.fullReg)
    )

    // instructions ++= mainInitialisation

    ast.symbolTable.printSymbolTable()

    // Add the main frame
    val addMainFrame = StackMachine.addFrame(ast.symbolTable, None)

    val intermediateRepresentation = astToIR(ast)

    // Pop the main frame
    val decrementStackInstr = StackMachine.popFrame()
    // instructions ++= decrementStackInstr

    instructions ++= initDirectives
    instructions ++= Directive("section .rodata") +: rodataDirectives
    instructions ++= mainInitialisation
    instructions ++= intermediateRepresentation
    instructions ++= decrementStackInstr

    instructions += Mov(
      Dest,
      Immediate32(0),
      InstrSize.fullReg
    ) // Return 0 if no exit function
    instructions ++= List(
      ReturnInstr()
    )

    instructions ++= lib.addLibFuns()

    instructions
  }

  /** Converts the given AST to intermediate representation by recursively
    * traversing the AST and converting each node to a list of instructions in
    * the IR.
    * @param position
    * @return
    *   The intermediate representation of the given AST
    */
  def astToIR(position: Position): Buffer[Instruction] = position match {
    case prog @ Program(funcList, stat) => {

      val funcIR = new ListBuffer[Instruction]
      for (func <- funcList) {
        funcIR.appendAll(astToIR(func))
      }

      val ir = new ListBuffer[Instruction]
      val statInstrs = for (s <- stat) yield statToIR(s)
      ir ++= statInstrs.flatten
      ir.appendAll(funcIR)
    }

  }

  def statToIR(stat: Stat): Buffer[Instruction] = stat match {
    case IdentAsgn(typeNode, ident, expr) => {
      // Dynamically determine the size of the variable from the typeNode
      val varSize = typeNode.size

      // // Step 1: Allocate space on the stack based on the variable size
      val instructions =
        ListBuffer[Instruction](DecrementStackPointerNB(varSize))

      // Step 2: Initialize the variable with the given expression
      instructions ++= exprToIR(expr)

      // Step 3: Update StackMachine context with the new variable
      StackMachine.putVarOnStack(ident.value)

      StackMachine.printStack()

      StackMachine.offset(ident.value) match {
        case Some(offset) => {
          instructions += Mov(FPOffset(offset), Dest, InstrSize.fullReg)
        }
        case None => {
          throw new RuntimeException("Variable not found in stack")
        }
      }

      instructions
    }
    case AsgnEq(lhs, rhs) => {
      lhs match {
        case Ident(ident) => {
          val instructions = exprToIR(rhs)
          StackMachine.offset(ident) match {
            case Some(offset) => {
              instructions += Mov(FPOffset(offset), Dest, InstrSize.fullReg)
            }
            case None => {
              throw new RuntimeException("Variable not found in stack")
            }
          }
          instructions
        }
        // need to have cases for pairs and array elements later
      }
    }
    case Print(expr) => {
      lib.setprintStringFlag(true)

      printToIR(expr)
    }
    case Exit(expr) => {
      lib.setExitFlag(true)
      exprToIR(expr) ++ ListBuffer(
        Mov(Arg0, Dest, InstrSize.fullReg),
        CallInstr("exit")
      )
    }
    case Skip() => {
      ListBuffer()
    }
  }

  def printToIR(expr: Expr): Buffer[Instruction] = {
    val func = expr match {
      case ArrayElem(i, elist) => {
        elist.head match {
          case CharLiter(value) => {
            CallInstr("print_string")
          }
          case _ => {
            CallInstr("print_reference")
          }
        }
      }
      case StringLiter(pos) => {
        CallInstr("print_string")
      }
      case BoolLiter(pos) => {
        CallInstr("print_bool")
      }
      case CharLiter(pos) => {
        CallInstr("print_char")
      }
      case IntLiter(pos) => {
        CallInstr("print_int")
      }
      case _ => {
        CallInstr("print_reference")
      }
    }

     exprToIR(expr) ++= ListBuffer(
        PushRegisters(List(Dest), InstrSize.fullReg),
        PopRegisters(List(Dest), InstrSize.fullReg),
        Mov(Dest, Dest, InstrSize.fullReg),
        Mov(Arg0, Dest, InstrSize.fullReg),
      ) ++= ListBuffer(func)
  }

  def exprToIR(expr: Position): Buffer[Instruction] = expr match {

    case IntLiter(value) => {

      val instructions = ListBuffer[Instruction]().empty
      // If the expression is an integer literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value), InstrSize.fullReg)

    }
    // Handle other types of expressions
    case BoolLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a boolean literal, we can simply move the value to the variable
      instructions += Mov(
        Dest,
        Immediate32(if (value) 1 else 0),
        InstrSize.fullReg
      )

    }
    case CharLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a character literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value.toInt), InstrSize.fullReg)
    }
    case StringLiter(value) => {
      val label = addStringLiteral(value)
      ListBuffer(
        LoadEffectiveAddress(
          Dest,
          RegisterLabelAddress(IP, LabelAddress(label)),
          InstrSize.fullReg
        )
      )
    }
    case Ident(ident) => {
      StackMachine.offset(ident) match {
        case Some(offset) => {
          ListBuffer(Mov(Dest, FPOffset(offset), InstrSize.fullReg))
        }
        case None => {
          throw new RuntimeException("Variable not found in stack")
        }
      }
    }

  }

}

case class VariableScope(scope: Int, name: String)
