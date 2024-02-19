package wacc
import scala.collection.mutable._

class StackMachine {

  /** Stack simulated via stack object */
  val stackSim : Stack[VariableScope] = Stack()
  
  /** Index of the current stack frame */
  val frameIndex = 0

  /** size of stack */
  def stackSize: Int = stackSim.size

  val scratchRegisters = Stack(G0, G1, G2, G3)

  def pushToStack(variables : VariableScope, value : Int, size : Int): Buffer[Instruction] = {
    /** @TODO */
    val prefix = size match {
      case 2 => ""
      case 4 => "e"
      case 8 => "r"
    }
    null
  }

}
