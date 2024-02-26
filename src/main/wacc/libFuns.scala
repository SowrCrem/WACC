package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr
import parsley.internal.deepembedding.singletons.Offset

class LibFunGenerator {


  private var dataCounter = -1

  private var exitFlag : Boolean = false
  private var printStringFlag : Boolean = false
  private var printIntFlag : Boolean = false
  private var printBoolFlag : Boolean = false
  private var printCharFlag : Boolean = false
  private var printReferenceFlag : Boolean = false
  private var printLnFlag : Boolean = false



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
      val x = rodata ++ printStringIR()
      libFuns ++= x
    }
    if (printIntFlag) {
      var rodata = printIntDataIR("%d")
      val x = rodata ++ printIntIr()
      libFuns ++= x
    }
    if (printLnFlag) {
      var rodata = printlnDataIR("")
      val x = rodata ++ printLnIR()
      libFuns ++= x
    }

    libFuns
  }




  
  def setExitFlag(flag: Boolean): Unit = {
    exitFlag = flag
  }

  /** The intermediate representation for the exit function
    */
  def exitIR: List[Instruction] = List(
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

  def printStringIR(): List[Instruction] = List(
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


  def setPrintLnFlag(flag: Boolean): Unit = {
    printLnFlag = flag
  }

  def printLnIR() : List[Instruction] = List(
    Label("_print_ln"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    LoadEffectiveAddress(Arg0, OffsetRegLabel(IP, LabelAddress(s"_println_string${dataCounter}")), InstrSize.fullReg),
    CallPLT("puts"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush"),
    Mov(SP, FP,  InstrSize.fullReg),
    PopRegisters(List(FP),  InstrSize.fullReg),
    ReturnInstr()
  )

  def setPrintIntFlag(flag: Boolean): Unit = {
    printIntFlag = flag
  }

  def printIntIr() : List[Instruction] = List(
    Label("_print_int"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    Mov(Arg1, Arg0, InstrSize.fullReg),
    LoadEffectiveAddress(Arg0, OffsetRegLabel(IP, LabelAddress(s"_printi_string${dataCounter}")), InstrSize.fullReg),
    Mov(Dest, Immediate32(0), InstrSize.eigthReg),
    CallPLT("printf"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush"),
    Mov(SP, FP,  InstrSize.fullReg),
    PopRegisters(List(FP),  InstrSize.fullReg),
    ReturnInstr()
  )




  def createDataIR(data: String, labelPrefix: String): List[Instruction] = {
    dataCounter += 1
    val label = s"${labelPrefix}_string${dataCounter}"
    val dataIR = List(
      Directive("section .rodata"),
      Directive(s"int ${data.length()}"),
      Directive(s"$label: .asciz \"$data\""),
      Directive("text")
    )
    dataIR
  }

  def printDataIR(data: String): List[Instruction] = {
    createDataIR(data, "_prints")
  }

  def printlnDataIR(data: String): List[Instruction] = {
    createDataIR(data, "_println")
  }

  def printIntDataIR(data: String): List[Instruction] = {
    createDataIR(data, "_printi")
  }

  
  //for testing purposes
  def reset() : Unit = {
    dataCounter = -1
    exitFlag = false
    printStringFlag = false
    printLnFlag = false
  }

}
