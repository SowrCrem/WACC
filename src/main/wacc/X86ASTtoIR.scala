package wacc

import scala.collection.mutable._
import InstrCond._

object X86IRGenerator {

  /** The library function generator
    */
  val lib: LibFunGenerator = new LibFunGenerator()

  val regTracker = new RegisterTracker

  val stringLiterals: Map[String, String] = Map()
  var rodataDirectives: ListBuffer[Directive] = ListBuffer()

  var labelCounter: Int = 0

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

  // ------- Statement IR Generation -----------------------------------------------//

  def statToIR(stat: Stat): Buffer[Instruction] = stat match {
    case IdentAsgn(typeNode, ident, expr) => {
      // Dynamically determine the size of the variable from the typeNode
      val varSize = typeNode.size / 8

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
      printToIR(expr, false)
    }
    case Println(expr) => {
      lib.setPrintLnFlag(true)
      printToIR(expr, true)
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

  def printToIR(expr: Expr, println: Boolean): Buffer[Instruction] = {
    val func = expr.typeNode match {
      case StringTypeNode() => {
        lib.setPrintStringFlag(true)
        CallInstr("print_string")
      }
      case BoolTypeNode() => {
        lib.setPrintBoolFlag(true)
        CallInstr("print_bool")
      }
      case CharTypeNode() => {
        lib.setPrintCharFlag(true)
        CallInstr("print_char")
      }
      case IntTypeNode() => {
        lib.setPrintIntFlag(true)
        CallInstr("print_int")
      }
      case _ => {
        printf("Type printing: :" + expr.typeNode.toString())
        CallInstr("print_reference")
      }

    }
    exprToIR(expr) ++= ListBuffer(
      PushRegisters(List(Dest), InstrSize.fullReg),
      PopRegisters(List(Dest), InstrSize.fullReg),
      Mov(Dest, Dest, InstrSize.fullReg),
      Mov(Arg0, Dest, InstrSize.fullReg)
    ) ++= {
      if (!println) {
        ListBuffer(func)
      } else {

        ListBuffer(func, CallInstr("print_ln"))
      }
    }
  }

  // --------------- Expression IR Generation -------------------------------------//

  def exprToIR(expr: Position): Buffer[Instruction] = expr match {

    case Brackets(expr) => {
      exprToIR(expr)
    }
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
    case And(expr1, expr2) => {
      // evaluate each expression before performing the and operation
      val setup = binOpSetup(expr1, expr2)
      labelCounter += 1
      setup ++= ListBuffer(
        Cmp(G1, Immediate32(1), InstrSize.fullReg),
        JumpIfCond(s"and_false${labelCounter}", InstrCond.notEqual),
        Cmp(G2, Immediate32(1), InstrSize.fullReg),
        Label(s"and_false${labelCounter}"),
        SetByteIfCond(Dest, InstrCond.equal, InstrSize.eigthReg),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
      )
    }
    case Or(expr1, expr2) => {
      val setup = binOpSetup(expr1, expr2)
      labelCounter += 1
      setup ++= ListBuffer(
        Cmp(G1, Immediate32(1), InstrSize.fullReg),
        JumpIfCond(s"or_true${labelCounter}", InstrCond.equal),
        Cmp(G2, Immediate32(1), InstrSize.fullReg),
        Label(s"or_true${labelCounter}"),
        SetByteIfCond(Dest, InstrCond.equal, InstrSize.eigthReg),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
      )
    }
    case Equals(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.equal)
    }
    case GreaterThan(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.greaterThan)
    }
    case LessThan(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.lessThan)
    }
    case NotEquals(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.notEqual)
    }
    case GreaterThanEq(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.greaterThanEqual)
    }
    case LessThanEq(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.lessThanEqual)
    }
    case Plus(expr1, expr2) => {
      val setup = binOpSetup(expr1, expr2)
      setup ++= ListBuffer(
        Mov(Dest, G1, InstrSize.halfReg),
        AddInstr(Dest, G2, InstrSize.halfReg),


      )
    }
  }

  def binOpSetup(expr1: Expr, expr2: Expr): Buffer[Instruction] = {
    val expr1IR = exprToIR(expr1)
    val expr2IR = exprToIR(expr2)
    expr1IR ++ ListBuffer(
      Mov(G1, Dest, InstrSize.fullReg)
    ) ++ expr2IR ++ ListBuffer(Mov(G2, Dest, InstrSize.fullReg))
  }

  def compBinOp(expr1: Expr, expr2: Expr, comparison : InstrCond) = {
    val setup = binOpSetup(expr1, expr2)
    setup ++= ListBuffer(
      Cmp(G1, G2, InstrSize.fullReg),
      SetByteIfCond(Dest, comparison, InstrSize.eigthReg),
      MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
    )
  }

  // for testing
  def reset(): Unit = {
    stringLiterals.clear()
    rodataDirectives.clear()
    lib.reset()
    labelCounter = 0
  }

}

case class VariableScope(scope: Int, name: String)
