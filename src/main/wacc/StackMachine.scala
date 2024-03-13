package wacc
import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import parsley.internal.machine.instructions.Pop
import java.util.HashMap

/** Special Grade Cursed Object
  */
object StackMachine {

  var stackFrameCounter = 0;


  private var frames: List[StackFrame] = List().empty


  def putVarOnStack(name: String): Unit = {

    frames.last.declaredVars += name
  }

  private def frameTotalSize(frame: StackFrame): Int = {
    val baseSize = frame.localVarSize

    // 2 * 4 bytes for the return address and the frame pointer if the frame is that of a function
    // otherwise 0 because the frame is that of a loop or a conditional block
    val additionalSize = if (frame.isFunction) 2 * 4 else 0

    baseSize + additionalSize

  }

  /**
   * Calculates the offset of a variable in the stack frames.
   *
   * @param name The name of the variable to find the offset for.
   * Calculates the offset of a variable within a frame and the total offset of the frame.
   * The offset is calculated in relation to the frame's base address.
   *
   * @return An optional tuple containing the offset of the variable and the total offset of the frame.
   *         Returns None if the variable is not found in any of the frames.
   */
  def offset(name: String): (Option[(Int, Int)], Boolean, StackFrame) = {

    var totalOffset = 0

    if (frames.isEmpty) {
      println("No frames in stack")
    }

    printf("Looking for %s in stack\n", name)
    printStack()

    frames.reverse.foreach(frame => {
      frame.findVarOffset(name) match {
        case -1 => {
          totalOffset += (frameTotalSize(frame))
          printf("not in this frame? totalOffset is now %d\n", totalOffset)
        }
        case offset => {
          printf(
            "found %s in frame with offset %d\n",
            name,
            offset + totalOffset + 8
          )

          return (Some((offset + 8, totalOffset)), frame.definedVarMap(name), frame)
        }
      }
    })

    (None, false, null)
  }

  /**
    * Add a new stack frame to the stack
    *
    * @param symbolTable The symbol table for the current scope
    * @param opParamList The parameter list for the current scope if it is a function
    * @return
    */
  def addFrame(
      symbolTable: SymbolTable,
      opParamList: Option[ParamList]
  ): ListBuffer[Instruction] = {

    // Create a new stack frame for the current scope
    val newFrame = new StackFrame(symbolTable, opParamList, stackFrameCounter)

    println("adding frame:")
    newFrame.printFrame()
    frames :+= newFrame

    // List of instructions to decrement the stack pointer by the size of the local variables in the stack frame
    val decrementStackInstr = new ListBuffer[Instruction]().empty

    var size = (newFrame.localVarSize / 8) + 1

    println("adding frame with size: " + size)

    // Decrement the stack pointer by the size of the local variables in the stack frame
    while (size > 1024) {
      decrementStackInstr += DecrementStackPointerNB(1024)
      size -= 1024
    }

    decrementStackInstr += DecrementStackPointerNB(size)

    // List of instructions to push the frame pointer onto the stack and set it to the stack pointer
    ListBuffer(PushRegisters(List(FP), InstrSize.fullReg)) ++ ListBuffer(Mov(FP, SP, InstrSize.fullReg)) ++ decrementStackInstr

  }

  def popFrame(): ListBuffer[Instruction] = {

    // List of instructions to increment the stack pointer by the size of the local variables in the stack frame
    val incrementStackInstr = new ListBuffer[Instruction]().empty

    var size = (frames.last.localVarSize / 8) + 1

    printf("popping frame with size: %d\n", size)

    frames.last.printFrame()

    if (size > 0) {
      incrementStackInstr += IncrementStackPointerNB(size)
    }

    // Remove the last frame from the stack
    frames = frames.dropRight(1)

    incrementStackInstr ++ ListBuffer(PopRegisters(List(FP), InstrSize.fullReg))
  }

  def printStack(): Unit = {
    println("Printing stack --------------------")
    frames.reverse.foreach(frame => {
      println(">--------> Start Frame <--------<")
      frame.printFrame()
      println(">--------> End Frame <--------<")
    })
    println("End of stack --------------------")
  }

}

/** StackFrame class */

// Each stack frame is a dictionary of variables and their types for a given scope in the program
// (e.g. a function, a loop, a conditional block, etc.)
class StackFrame(symbolTable: SymbolTable, opParamList: Option[ParamList], stackFrameCounter: Int) {

  // The size of the local variables in the stack frame
  var localVarSize: Int = 0

  val definedVarMap : mutable.Map[String, Boolean] = mutable.HashMap[String, Boolean]()

  val lazyToLabel : mutable.HashMap[String, String] = mutable.HashMap[String, String]()


  // The size of the parameters in the stack frame
  var pushedArgSize = 0

  // Map of variable names to their offsets in the stack frame
  val varMap: mutable.Map[String, Int] = mutable.HashMap[String, Int]()

  // Set of declared variables in the stack frame
  var declaredVars: Set[String] = Set[String]().empty

  // Boolean to check if the current stack frame is that of a function
  var isFunction = false;

  initFrame()

  // Function to initialize the stack frame from the symbol table corresponding to the current scope
  private def initFrame(): Unit = {
    for ((name, typenode) <- symbolTable.dictionary) {
      if (typenode.isParam) {
        declaredVars += name
        pushedArgSize += typenode.size
        localVarSize += typenode.size
      } else {
        if (!typenode.isInstanceOf[Func]) {
          varMap += (name -> localVarSize)
          localVarSize += typenode.size
          printf("Adding %s to stack frame with size %d\n", name, localVarSize)
        }
      }

      if (symbolTable.isLazyVar(name)) {
        definedVarMap.put(name, false);
        lazyToLabel.put(name, "lazy_" + name + "_" + stackFrameCounter.toString());
      } else {
        definedVarMap.put(name, true);
      }
    }

    println("definedVarMap is\n" + definedVarMap)

    opParamList match {
      case Some(paramList) => {

        // if a parameter list is present, the current stack frame is that of a function
        isFunction = true

        /** offsets for function parameters are calculated by starting from the
          * end of the local variables and accounting for the space taken by the
          * return address and the frame pointer (2 * 8 bytes), simulating the
          * typical layout of a stack frame in x86 where parameters are pushed
          * onto the stack in reverse order after local variables and control
          * information
          */
        var currentOffset = 2 * 8 + 8
        for (p <- paramList.paramList.reverse) {
          varMap.addOne(p.ident.value, -(currentOffset))
          currentOffset += p.typeNode.size
        }

      }
      case None => {}
    }
  }

  def findVarOffset(name: String): Int = {
    if (varMap.contains(name)) {

      return varMap(name)
    } else {
      println("Variable not found in current frame:" + name + "\n")
      this.printFrame()
      return -1
    }
  }

  // Debugging function to print the stack frame
  def printFrame(): Unit = {
    println("Local variable size: " + localVarSize)
    println("Pushed argument size: " + pushedArgSize)
    println("Declared variables: " + declaredVars)
    println("Variable map: " + varMap)
  }

}
