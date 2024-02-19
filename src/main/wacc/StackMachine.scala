package wacc
import scala.collection.mutable._

class StackMachine {

  /** Stack simulated via stack object */
  val stackSim : Stack[VariableScope] = Stack()
  
  /** Index of the current stack frame */
  val frameIndex = 0

  /** size of stack */
  def stackSize: Int = stackSim.size

  def pushToStack(variables : VariableScope): Buffer[Instruction] = {
    /** @TODO */
    null
  }

}
