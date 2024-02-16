package wacc

import scala.collection.mutable._

object X86IRGenerator {

  /** 
    * Flag to indicate whether the exit function is used in the program
  */
  var exitFunc: Boolean = false;
  
  val stack: Stack[Register] = new Stack[Register]

  /** 
    * Stack of available registers
  */
  var usedRegs: Map[String, Register] = Map()
  
  val availableRegs = new Stack[Register] {
    /** 
     * Pops a register from the stack of available registers
     * and pushes it onto the stack of used registers
     * @return The register that was popped
     */
    def popToUsed(variable: String): Register = {
        val reg = super.pop()
        usedRegs += (variable -> reg)
        reg
      }
  }
  
  usedRegs = new Map[String, Register] {
    /** 
     * Pops a register from the stack of used registers
     * and pushes it onto the stack of available registers
     * @return The register that was popped
    */
    def removeToAvailable(reg: Register): Boolean = {
      //TODO: Implement
    }
  }

//   def asgnNextAvailableReg(): Register = {
//     val reg = availableRegs.popToUsed()
//   }

  /** 
   * Generates the intermediate representation for the given AST
   * @param ast - The AST of the program to be translated
   * @return The intermediate representation of the program
  */
  def generateIR(ast: Program): Buffer[Instruction] = {
    availableRegs.pushAll(List(
        G0, G1, G2, G3, G4, G5, G6,
        Arg0, Arg1, Arg2, Arg3, Arg4, Arg5,
        Dest
    ))
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
      ReturnInstr(),
      )
    if (exitFunc) {
      instructions ++= exitIR
    }
    instructions
  }

  /**
    * Converts the given AST to intermediate representation
    * by recursively traversing the AST and converting each node
    * to a list of instructions in the IR.
    * @param position
    * @return The intermediate representation of the given AST
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
}

  /**
    * The intermediate representation for the exit function
    */
  val exitIR: List[Instruction] = List(
    Label("_exit"),
    Push(FP),
    Mov(FP, SP),
    AndInstr(SP, Immediate32(-16)),
    CallPLT("exit"),
    Mov(SP, FP),
    Pop(FP),
    ReturnInstr()// Restore frame pointer and return
  )

}
