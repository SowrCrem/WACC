package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr

class LibFunGenerator {


  private var dataCounter = 0

  private var exitFlag : Boolean = false
  private var printStringFlag : Boolean = false
  private var printIntFlag : Boolean = false
  private var printBoolFlag : Boolean = false
  private var printCharFlag : Boolean = false
  private var printReferenceFlag : Boolean = false



  /**printStringIR
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
      var rodata = printDataIR("%.*s") 
      val x = rodata ++ printStringIR
      libFuns ++= x
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
    Label("_print_string"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    Mov(Arg2, Arg0, InstrSize.fullReg),
    Mov(Arg1, Reg32(Arg0, -4), InstrSize.halfReg),
    LoadEffectiveAddress(Arg0, OffsetRegLabel(IP, LabelAddress(s"_prints_string${dataCounter}")), InstrSize.fullReg),
    Mov(Dest, Immediate32(0), InstrSize.eigthReg),
    CallPLT("printf"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush"),
    Mov(SP, FP,  InstrSize.fullReg),
    PopRegisters(List(FP),  InstrSize.fullReg),
    ReturnInstr() // Restore frame pointer and return
  )




  def printDataIR(data: String): List[Instruction] = {
    val label = s"_prints_string${dataCounter}"
    val dataIR = List(
      Directive("section .rodata"),
      Directive(s"int ${data.length()}"),
      Directive(s"$label: .asciz \"$data\""),
      Directive("text")
    )
    dataCounter += 1
    dataIR
  }
}
