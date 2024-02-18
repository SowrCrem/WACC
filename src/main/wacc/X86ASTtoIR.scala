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

class RegisterTracker {

  val NUM_SCRATCH_REGS = 3

  val frameIndex

  /** Stack of available registers */
  val available = Stack(G0, G1, G2, G3, G4, G5, G6, Arg5, Arg4, Arg3, Arg2, Arg1, Arg0, Dest)

  /** Stack of used registers */
  val used: Map[IdentScope, Register] = HashMap()

  /** Stack of registers representing the state of the assembly stack */
  val stack: Stack[Register] = new Stack[Register]

  /**
    * Assigns am available register to a variable,
    * or assigns a stack location if no registers are available.
    * @param scope The scope of the variable (i.e. depth of the symbol table)
    * @param name The name of the variable
    * @return The register or stack location assigned to the variable
    * 
    * @PRE: No variable with the same name and scope is already assigned 
    * (should fail semantic check)
    * @POST: The variable is assigned to a register or stack location
    */
  def assignAndGetVar(scope: Int, name: String): Address = {
    /* Push to stack if no registers are available */
    if (available.size == 0) {
      val canPushToStack = used.minBy({case (k, v) => k.scope})
      assignToStack(canPushToStack._2 + "_"+ canPushToStack._1.scope.toString)
      freeRegister(canPushToStack)
      assert(available.size > 0, "No registers available")
    }
    val variable = IdentScope(scope, name)
    val reg = available.pop()
    used.addOne((variable -> reg))
    reg
  }

  private def freeRegister(mappedVal: IdentScope -> Register): Unit = {
    available.push(mappedVal._2) // Push the register back to the available stack
    used.remove(mappedVal._1)    // Remove the variable from the used stack
  }
  /**
    * Deallocate all variables in the most local scope
    * @param scope The index of the lowest scope to deallocate
    */
  def exitLastScope(scope: Int, stackframeIndex: Int): Unit = {
    /* Remove all variables in the given scope from the used registers */
    val toRemove = used.filter({case (k, v) => k.scope == scope})
    for ((k : IdentScope, v : Register) <- toRemove) {
      available.push(v.asInstanceOf[Register with Product with java.io.Serializable])
      used.remove(k)
    }
    /* Remove all variables in the given scope from the stack */
    while (stack.size > stackframeIndex) {
      stack.pop()
    }
  }

  /**
    * Assigns a stack address to a variable
    */
  def assignToStack(name: String): StackAddress = {
    /** @TODO: Implement */
    null
  }
}

case class IdentScope(scope: Int, name: String)
