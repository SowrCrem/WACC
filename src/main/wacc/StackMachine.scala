package wacc
import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import parsley.internal.machine.instructions.Pop


/**
  *  Special Grade Cursed Object
  * */
object StackMachine {

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

  def offset(name: String): Option[Int] = {

    var totalOffset = 0

    if (frames.isEmpty) {
      println("No frames in stack")
    }

    frames.reverse.foreach(frame => {
      frame.findVarOffset(name) match {
        case -1     => { totalOffset += frameTotalSize(frame) 
                          printf("not in this frame?")}
        case offset => return Some(totalOffset + offset)
      }
    })

    None
  }

  def addFrame(
      symbolTable: SymbolTable,
      opParamList: Option[ParamList]
  ): ListBuffer[Instruction] = {

    // Create a new stack frame for the current scope
    val newFrame = new StackFrame(symbolTable, opParamList)

    println("adding frame:")
    newFrame.printFrame()
    frames :+= newFrame

    // List of instructions to decrement the stack pointer by the size of the local variables in the stack frame
    val decrementStackInstr = new ListBuffer[Instruction]().empty

    var size = newFrame.localVarSize/8

    println("adding frame with size: " + size)

    // Decrement the stack pointer by the size of the local variables in the stack frame
    while (size > 1024) {
      decrementStackInstr += DecrementStackPointerNB(1024)
      size -= 1024
    }

    decrementStackInstr += DecrementStackPointerNB(size)
    

    // List of instructions to push the frame pointer onto the stack and set it to the stack pointer
    ListBuffer(PushRegisters(List(FP),  InstrSize.fullReg)) ++ decrementStackInstr ++ ListBuffer(
      Mov(FP, SP,  InstrSize.fullReg)
    )

  }

  def popFrame(): ListBuffer[Instruction] = {


    // List of instructions to increment the stack pointer by the size of the local variables in the stack frame
    val incrementStackInstr = new ListBuffer[Instruction]().empty

    var size = frames.last.localVarSize/8

    printf("popping frame with size: %d\n", size)

    frames.last.printFrame()
  
    if (size > 0) {
      incrementStackInstr += IncrementStackPointerNB(size)
    }

    // Remove the last frame from the stack
    frames = frames.dropRight(1)

    incrementStackInstr ++ ListBuffer(PopRegisters(List(FP),  InstrSize.fullReg))
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
class StackFrame(symbolTable: SymbolTable, opParamList: Option[ParamList]) {

  // The size of the local variables in the stack frame
  var localVarSize: Int = 0

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
      } else {
        if (!typenode.isInstanceOf[Func]) {
          varMap += (name -> localVarSize)
          localVarSize += typenode.size
          printf("Adding %s to stack frame with size %d\n", name, localVarSize)
        }
      }
    }

    opParamList match {
      case Some(paramList) => {

        // if a parameter list is present, the current stack frame is that of a function
        isFunction = true

        /**  
          * offsets for function parameters are calculated by starting from the end of the
          * local variables and accounting for the space taken by the return address and the
          * frame pointer (2 * 4 bytes), simulating the typical layout of a stack frame in x86
          * where parameters are pushed onto the stack in reverse order after local variables
          * and control information
        */
        var currentOffset = localVarSize + 2 * 4
        for (p <- paramList.paramList.reverse) {
          varMap.addOne(p.ident.value, currentOffset)
          currentOffset += p.typeNode.size
        }

      }
      case None => {}
    }
  }

  def findVarOffset(name: String): Int = {
    if (varMap.contains(name)) 
    {

      return varMap(name)
    } else {
      print("Variable not found in stack:" + name)
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
