package wacc
import scala.collection.mutable._

class RegisterTracker {

  val NUM_SCRATCH_REGS = 3

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
  def assignAndGetVar(scope: Int, name: String): Tuple2[Register, Boolean] = {
    /* Push to stack if no registers are available */
    var evictedFlag = false
    if (available.size == 0) {
      val canPushToStack = used.minBy({case (k, v) => k.scope})
      assignToStack(canPushToStack._1.name + ("_") + canPushToStack._1.scope.toString)
      freeRegister(canPushToStack)
      evictedFlag = true
      assert(available.size > 0, "No registers available")
    }
    val variable = IdentScope(scope, name)
    val reg = available.pop()
    used.addOne((variable -> reg))
    Tuple2(reg, evictedFlag)
  }

  /**
    * Frees a register by pushing it back to the available stack
    * from the used stack
    * @param mappedVal The mapping from variable to register for the register to be freed
  */
  private def freeRegister(mappedVal: (IdentScope, Register)): Unit = {
    available.push(
      mappedVal._2.asInstanceOf[Register with Product with java.io.Serializable]
      )                       // Push the register back to the available stack
    used.remove(mappedVal._1) // Remove the variable from the used stack
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
