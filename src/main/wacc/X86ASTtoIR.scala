package wacc

import scala.collection.mutable._

object X86IRGenerator {

  var exitFunc: Boolean = false;

  def generateIR(ast: Program): Buffer[Instruction] = {
    val instructions: Buffer[Instruction] = new ListBuffer[Instruction].empty
    instructions ++= List(
      Directive("intel_syntax noprefix"),
      Directive("globl main"),
      Directive("section .rodata"),
      Directive("text"),
      Label("main"),
      PushRegisters(List(FP, REG(1))),
      Mov(FP, SP)
    )
    instructions ++= astToIR(ast)

    if (exitFunc) {
      instructions ++= exitIR
    }

    instructions += Mov(REG(0), Immediate(0)) // Return 0 if no exit function
    instructions ++= List(
      PopRegisters(List(REG(1), FP)),
      ReturnInstr(),
    )

  }

  def astToIR(position: Position): Buffer[Instruction] = position match {
    case Program(funcList, stat) => {
      val funcIR = new ListBuffer[Instruction]
      for (func <- funcList) {
        funcIR.appendAll(astToIR(func))
      }
      val ir = new ListBuffer[Instruction]
      ir.appendAll(funcIR)
    }
    case Exit(IntLiter(value)) => {
      exitFunc = true;
      ListBuffer(
        Mov(REG(0), Immediate(value.toInt)),
        Mov(REG(5), REG(0)),
        CallInstr("exit")
      )
    }
  }

  val exitIR: List[Instruction] = List(
    Label("_exit"),
    Push(FP),
    Mov(FP, SP),
    AndInstr(SP, Immediate(-16)),
    CallPLT("exit"),
    Mov(SP, FP),
    Pop(FP),
    ReturnInstr()// Restore frame pointer and return
  )

}
