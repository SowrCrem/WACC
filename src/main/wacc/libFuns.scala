package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr

class LibFunGenerator {

  private var exitFlag : Boolean = false
  private var printStringFlag : Boolean = false
  private var printIntFlag : Boolean = false
  private var printBoolFlag : Boolean = false
  private var printCharFlag : Boolean = false
  private var printReferenceFlag : Boolean = false



  /**
    * Adds the library functions to the IR based
    *  on flags set in the compiler
    * @return
    */  
  def addLibFuns(): ListBuffer[Instruction] = {
    val libFuns = new ListBuffer[Instruction]()
    if (exitFlag) {
      libFuns ++= exitIR
    }
    if (printStringFlag) {
      libFuns ++= printStringIR
    }

    libFuns
  }




  
  def setExitFlag(flag: Boolean): Unit = {
    exitFlag = flag
  }

  /** The intermediate representation for the exit function
    */
  val exitIR: List[Instruction] = List(
    Label("_exit"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    CallPLT("exit"),
    Mov(SP, FP,  InstrSize.fullReg),
    PopRegisters(List(FP),  InstrSize.fullReg),
    ReturnInstr() // Restore frame pointer and return
  )



  def setprintStringFlag(flag: Boolean): Unit = {
    printStringFlag = flag
  }

  /**
    * The intermediate representation for the print string function
    */

  val printStringIR: List[Instruction] = List(
    Directive("section ,rodata"),

    Label("_print_string"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    Mov(G0, Arg0, InstrSize.fullReg),

  )
}
