/**
 * This file contains the implementation of the LibFunGenerator class, which is responsible for generating
 * the intermediate representation (IR) for the library functions used in the WACC compiler.
 *
 * The LibFunGenerator class provides methods for adding library functions to the IR based on flags set in the compiler.
 * It also includes methods for setting the flags and generating the IR for specific library functions such as printString,
 * printInt, printBool, printLn, and printChar.
 *
 * The generated IR can be used as input for further compilation steps or code generation.
 */
package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr
import parsley.internal.deepembedding.singletons.Offset

class LibFunGenerator {

  /** The counter for the number of data strings
    */
  private var dataCounter = -1

  // -------------------- Flags for the library functions ---------------------------------------

  // The flags indicate that their corresponding functions must be added to the assembly output if set to true
  private var exitFlag: Boolean = false
  private var printStringFlag: Boolean = false
  private var printIntFlag: Boolean = false
  private var printBoolFlag: Boolean = false
  private var printCharFlag: Boolean = false
  private var printReferenceFlag: Boolean = false
  private var printLnFlag: Boolean = false
  private var overflowFlag: Boolean = false
  private var divideByZeroFlag: Boolean = false
  private var outOfMemoryFlag: Boolean = false

  /** Adds the library functions to the IR based on flags set in the compiler
    * @return
    */
  def addLibFuns(): ListBuffer[Instruction] = {

    val libFuns = new ListBuffer[Instruction]()
    if (overflowFlag) {
      libFuns ++= overflow.createErrMessageIR() ++ overflow.generateErrIR()
    }
    if (divideByZeroFlag) {
      libFuns ++= divideByZero.createErrMessageIR() ++ divideByZero.generateErrIR()
    }
    if (exitFlag) {
      libFuns ++= exitIR
    }
    if (outOfMemoryFlag) {
      libFuns ++= outOfMemory.createErrMessageIR() ++ outOfMemory.generateErrIR()
    }
    libFuns ++= addPrintFunc(printStringFlag, printDataIR("%.*s"), "printString")
    libFuns ++= addPrintFunc(printIntFlag, printIntDataIR("%d"), "printInt")
    libFuns ++= addPrintFunc(printBoolFlag, printBoolDataIR(), "printBool")
    libFuns ++= addPrintFunc(printLnFlag, printlnDataIR(""), "printLn")
    libFuns ++= addPrintFunc(printCharFlag, printCharDataIR(), "printChar")

    libFuns
  }
  
  /** 
    * A generic error type for the library functions
    * note the generateErrIR method is generic and may need to be overridden
    * 
    * To define a new case object: 
    * 1. Create a new case object that extends ErrType
    * 2. Define the labelName, dataName, and errMessage fields
    * 3. Override the generateErrIR method (optional, if the error handling is different from the default implementation)
    * 
    * See examples below for overflow, divideByZero, and outOfMemory
  */
  private sealed abstract trait ErrType {
    val labelName: String
    val dataName: String
    val errMessage: String
    // val printErrFlag = false
    
    /**
      * Generates the intermediate representation (IR) for the data section (i.e. error message) of the library functions
      * @return the IR for the data section as a list of instructions
      */
    def createErrMessageIR(): List[Instruction] = {
      createDataIRNoCounter(errMessage, dataName)
    }

    /** 
      * Generates the intermediate representation (IR) for the library functions
      * @return the IR for the library functions as a list of instructions
    */
    def generateErrIR(): List[Instruction] = {
      setPrintStringFlag(true)
      List(
        Label(labelName),
        AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
        LoadEffectiveAddress(
          Arg0,
          OffsetRegLabel(IP, LabelAddress(dataName)),
          InstrSize.fullReg
        ),
        CallInstr("print_string"),
        Mov(Arg0, Immediate32(-1), InstrSize.eigthReg),
        CallPLT("exit")
      )
    }
  }
  private case object overflow extends ErrType {
    val labelName = "_errOverflow"
    val dataName = "_overflow_string"
    val errMessage: String = "fatal error: integer overflow or underflow occurred\\n"
  }
  private case object divideByZero extends ErrType {
    val labelName = "_errDivByZero"
    val dataName = "_divide_by_zero_string"
    val errMessage: String = "fatal error: division or modulo by zero\\n"
  }
  private case object outOfMemory extends ErrType {
    val labelName = "_errOutOfMemory"
    val dataName = "_array_out_of_memory"
    val errMessage: String = "fatal error: out of memory\\n"
  }

  def setOverflowFlag(flag: Boolean): Unit = {
    overflowFlag = flag
  }
  
  def setDivideByZeroFlag(flag: Boolean): Unit = {
    divideByZeroFlag = flag
  }
  /** Adds the print function to the IR based on the flag set in the compiler
    * @param flag
    *   determines whether to add the print function to the IR
    * @param dataGenerator
    *   the generator for the data section of the print function
    * @param printLabel
    *   the label for the print function
    * @return
    *   the IR for the print function
    */
  private def addPrintFunc(
      flag: Boolean,
      dataGenerator: => List[Instruction],
      printLabel: String
  ): List[Instruction] = {
    if (flag) {
      val rodata = dataGenerator
      rodata ++ createPrintIR(printLabel)
    } else {
      List.empty[Instruction] // Return an empty list if the flag is not set
    }
  }

  def setExitFlag(flag: Boolean): Unit = {
    exitFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the exit function
   */
  def exitIR: List[Instruction] = List(
    Label("_exit"),
    PushRegisters(List(FP), InstrSize.fullReg),
    Mov(FP, SP, InstrSize.fullReg),
    AndInstr(SP, Immediate32(-16), InstrSize.fullReg),
    CallPLT("exit"),
    Mov(SP, FP, InstrSize.fullReg),
    PopRegisters(List(FP), InstrSize.fullReg),
    ReturnInstr() // Restore frame pointer and return
  )

  /**
   * Generates the intermediate representation (IR) for creating the print functions
   * @param label
   *   determines which print function to create
   * @return
   */
  def createPrintIR(label: String): List[Instruction] = {
    def commonPrologue(labelName: String): List[Instruction] = List(
      Label(labelName),
      PushRegisters(List(FP), InstrSize.fullReg),
      Mov(FP, SP, InstrSize.fullReg),
      AndInstr(SP, Immediate32(-16), InstrSize.fullReg)
    )

    def commonEpilogue(): List[Instruction] = List(
      Mov(SP, FP, InstrSize.fullReg),
      PopRegisters(List(FP), InstrSize.fullReg),
      ReturnInstr()
    )

    val printCase = label match {
      case "printString" => commonPrologue("_print_string") ++ printStringIR()
      case "printInt"    => commonPrologue("_print_int") ++ printIntIr()
      case "printBool"   => commonPrologue("_print_bool") ++ printBoolIr()
      case "printLn"     => commonPrologue("_print_ln") ++ printLnIR()
      case "printChar"   => commonPrologue("_print_char") ++ printCharIR()
    }

    printCase ++ commonEpilogue()

  }

  def setPrintStringFlag(flag: Boolean): Unit = {
    printStringFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the print string function
   */
  def printStringIR(): List[Instruction] = List(
    Mov(Arg2, Arg0, InstrSize.fullReg),
    Mov(Arg1, Reg32(Arg0, -4), InstrSize.halfReg),
    LoadEffectiveAddress(
      Arg0,
      OffsetRegLabel(IP, LabelAddress(s"_prints_string${dataCounter}")),
      InstrSize.fullReg
    ),
    Mov(Dest, Immediate32(0), InstrSize.eigthReg),
    CallPLT("printf"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush")
  )

  def setPrintLnFlag(flag: Boolean): Unit = {
    printLnFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the print ln function
   */
  def printLnIR(): List[Instruction] = List(
    LoadEffectiveAddress(
      Arg0,
      OffsetRegLabel(IP, LabelAddress(s"_println_string${dataCounter}")),
      InstrSize.fullReg
    ),
    CallPLT("puts"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush")
  )

  def setPrintIntFlag(flag: Boolean): Unit = {
    printIntFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the print int function
   */
  def printIntIr(): List[Instruction] = List(
    Mov(Arg1, Arg0, InstrSize.fullReg),
    LoadEffectiveAddress(
      Arg0,
      OffsetRegLabel(IP, LabelAddress(s"_printi_string${dataCounter}")),
      InstrSize.fullReg
    ),
    Mov(Dest, Immediate32(0), InstrSize.eigthReg),
    CallPLT("printf"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush")
  )

  def setPrintBoolFlag(flag: Boolean): Unit = {
    printBoolFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the print bool function
   */
  def printBoolIr(): List[Instruction] = List(
    Cmp(Arg0, Immediate32(0), InstrSize.eigthReg),
    JumpIfCond("print_true", InstrCond.notEqual),
    LoadEffectiveAddress(
      Arg2,
      OffsetRegLabel(IP, LabelAddress(s"_printb_false")),
      InstrSize.fullReg
    ),
    Jump("print_end"),
    Label("print_true"),
    LoadEffectiveAddress(
      Arg2,
      OffsetRegLabel(IP, LabelAddress(s"_printb_true")),
      InstrSize.fullReg
    ),
    Label("print_end"),
    Mov(Arg1, Reg32(Arg2, -4), InstrSize.halfReg),
    LoadEffectiveAddress(
      Arg0,
      OffsetRegLabel(IP, LabelAddress(s"_printb_string0")),
      InstrSize.fullReg
    ),
    Mov(Dest, Immediate32(0), InstrSize.eigthReg),
    CallPLT("printf"),
    Mov(Arg0, Immediate32(0), InstrSize.fullReg),
    CallPLT("fflush")
  )

  def setPrintCharFlag(flag: Boolean): Unit = {
    printCharFlag = flag
  }

  /**
   * Generates the intermediate representation (IR) for the print char function
   */
  def printCharIR(): List[Instruction] = {
    List(
      Mov(Arg1, Arg0, InstrSize.eigthReg),
      LoadEffectiveAddress(
        Arg0,
        OffsetRegLabel(IP, LabelAddress(s"_printc_string${dataCounter}")),
        InstrSize.fullReg
      ),
      Mov(Dest, Immediate32(0), InstrSize.eigthReg),
      CallPLT("printf"),
      Mov(Arg0, Immediate32(0), InstrSize.fullReg),
      CallPLT("fflush")
    )
  }

  /**
   * Generates the intermediate representation (IR) for the data section of library functions
   * @param data
   * @param labelPrefix
   *   the prefix for the label
   * @return
   */
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

  def createDataIRNoCounter(
      data: String,
      labelPrefix: String
  ): List[Instruction] = {
    val label = s"${labelPrefix}"
    val dataIR = List(
      Directive("section .rodata"),
      Directive(s"int ${data.length()}"),
      Directive(s"$label: .asciz \"$data\""),
      Directive("text")
    )
    dataIR
  }

  /** The intermediate representation creator for the data section of library
    * functions taking a list of strings and a list of label prefixes which are
    * used to create the labels corresponding to the strings customised by the
    * label prefix
    * @param data
    * @param labelPrefixes
    * @return
    */
  def createDataIR(
      data: List[String],
      labelPrefixes: List[String]
  ): List[Instruction] = {
    val dataIR = ListBuffer(Directive("section .rodata"))
    for (i <- data.indices) {
      dataCounter += 1
      val label = s"${labelPrefixes(i)}"
      dataIR += Directive(s"int ${data(i).length()}")
      dataIR += Directive(s"$label: .asciz \"${data(i)}\"")
    }
    dataIR += Directive("text")
    dataIR.toList
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

  def printBoolDataIR(): List[Instruction] = {
    createDataIR(
      List("false", "true", "%.*s"),
      List("_printb_false", "_printb_true", "_printb_string0")
    )
  }

  def printCharDataIR(): List[Instruction] = {
    createDataIR("%c", "_printc")
  }

  /** Resets the flags and data counter
    * @return
    */
  def reset(): Unit = {
    dataCounter = -1
    exitFlag = false
    printStringFlag = false
    printLnFlag = false
    printIntFlag = false
    printBoolFlag = false
  }

}
