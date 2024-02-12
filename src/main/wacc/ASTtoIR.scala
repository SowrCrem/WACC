package wacc
import scala.collection.mutable._

object IRGenerator {

  var exitFunc: Boolean = false;

  def generateIR(ast: Program): Buffer[Instruction] = {
    val instructions: Buffer[Instruction] = new ListBuffer[Instruction].empty
    instructions ++= List(
      Directive("data"),
      Directive("align 4"),
      Directive("text"),
      Directive("global main"),
      Label("main"),
      PushRegisters(List(Register("fp"), Register("lr"))),
      PushRegisters(List(Register("r8"), Register("r10"), Register("r12"))),
      Mov(Register("fp"), Register("sp"), AL())
    )
    instructions ++= astToIR(ast)

    if (exitFunc) {
      instructions ++= exitIR
    }

    instructions += Mov(
      Register("r0"),
      Immediate(0),
      AL()
    ) // Set return value to 0

    instructions ++= List(
      PopRegisters(List(Register("r8"), Register("r10"), Register("r12"))),
      PopRegisters(List(Register("fp"), Register("pc")))
    )

  }

  def astToIR(position: Position): Buffer[Instruction] = position match {
    case Program(funcList, stat) => {
      val funcIR = new ListBuffer[Instruction]
      for (func <- funcList) {
        funcIR.appendAll(astToIR(func))
      }
      val ir = new ListBuffer[Instruction]
      ir.appendAll(astToIR(stat))
      ir.appendAll(funcIR)

    }
    case Exit(IntLiter(value)) => {
      exitFunc = true;
      Buffer(
        Ldr(Register("r0"), Immediate(value.toInt), AL()),
        BranchLink("_exit")
      )
    }
  }

  val exitIR: List[Instruction] = List(
    Label("_exit"),
    PushRegisters(List(Register("fp"), Register("r1"))),
    Mov(Register("fp"), Register("sp"), AL()),
    BitClear(Register("sp"), Register("sp"), 0x7),
    BranchLink("exit"), // Call to external exit function
    Mov(Register("sp"), Register("fp"), AL()),
    PopRegisters(
      List(Register("fp"), Register("pc"))
    ) // Restore frame pointer and return
  )

}
