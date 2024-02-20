package wacc

import scala.collection.mutable._

object X86IRGenerator {

  /** Flag to indicate whether the exit function is used in the program
    */
  var exitFunc: Boolean = false

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
    stringLiterals.getOrElseUpdate(literal, {
      val label = s"string${stringLiterals.size}"
      rodataDirectives += Directive(s"$label: .asciz \"$literal\"")
      label
    })
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
    val initDirectives =  List(
      Directive("intel_syntax noprefix"),
      Directive("globl main"),
    )

    val mainInitialisation = ListBuffer(Directive("text"), Label("main"), PushRegisters(List(FP)), Mov(FP, SP))

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

    instructions += Mov(Dest, Immediate32(0)) // Return 0 if no exit function
    instructions ++= List(
      ReturnInstr()
    )


    if (exitFunc) {
      instructions ++= exitIR
    }

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
    case prog@Program(funcList, stat) => {

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
          instructions += Mov(FPOffset(offset), Dest)
        }
        case None => {
          throw new RuntimeException("Variable not found in stack")
        }
      }

      instructions
    }
    case Exit(IntLiter(value)) => {
      exitFunc = true;
      println("reached")
      ListBuffer(
        Mov(Dest, Immediate32(value)),
        Mov(Arg0, Dest),
        CallInstr("exit")
      )
    }
    case Skip() => {
      ListBuffer()
    }
  }

  def exprToIR(expr: Position): Buffer[Instruction] = expr match {

    case IntLiter(value) => {

      val instructions = ListBuffer[Instruction]().empty
      // If the expression is an integer literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value))

    }
    // Handle other types of expressions
    case BoolLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a boolean literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(if (value) 1 else 0))
  
    }

    case CharLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a character literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value.toInt))
    }
    case StringLiter(value) =>{
      val label = addStringLiteral(value)
      ListBuffer(LoadEffectiveAddress(Dest, RegisterLabelAddress(IP, LabelAddress(label))))
    }

  }

  /** The intermediate representation for the exit function
    */
  val exitIR: List[Instruction] = List(
    Label("_exit"),
    PushRegisters(List(FP)),
    Mov(FP, SP),
    AndInstr(SP, Immediate32(-16)),
    CallPLT("exit"),
    Mov(SP, FP),
    PopRegisters(List(FP)),
    ReturnInstr() // Restore frame pointer and return
  )

}

case class VariableScope(scope: Int, name: String)
