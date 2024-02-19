package wacc

import scala.collection.mutable._

object X86IRGenerator {

  /** Flag to indicate whether the exit function is used in the program
    */
  var exitFunc: Boolean = false

  val regTracker = new RegisterTracker

  /** Generates the intermediate representation for the given AST
    * @param ast
    *   \- The AST of the program to be translated
    * @return
    *   The intermediate representation of the program
    */
  def generateIR(ast: Program): Buffer[Instruction] = {
    val instructions: Buffer[Instruction] = new ListBuffer[Instruction].empty
    instructions ++= List(
      Directive("intel_syntax noprefix"),
      Directive("globl main"),
      Directive("section .rodata"),
      Directive("text"),
      Label("main"),
      PushRegisters(List(FP, G0)),
      Mov(FP, SP)
    )
    instructions ++= astToIR(ast)

    instructions += Mov(Dest, Immediate32(0)) // Return 0 if no exit function
    instructions ++= List(
      PopRegisters(List(G0, FP)),
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
    case Program(funcList, stat) => {
      val funcIR = new ListBuffer[Instruction]
      for (func <- funcList) {
        funcIR.appendAll(astToIR(func))
      }
      val ir = new ListBuffer[Instruction]
      val statInstrs = for (s <- stat) yield astToIR(s)
      ir ++= statInstrs.flatten
      ir.appendAll(funcIR)
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
