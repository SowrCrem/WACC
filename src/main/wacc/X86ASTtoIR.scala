package wacc

import scala.collection.mutable._
import InstrCond._
import ArithmOperations._
import Constants._
import parsley.internal.machine.instructions.Instr
import java.security.Identity
import scala.collection.immutable.ListMapBuilder



object X86IRGenerator {



  object AsgnType extends Enumeration {
    type AsgnType = Value
    val Reassign, Declare, Retrieve = Value
  }

  import AsgnType._

  /** The library function generator
    */
  val lib: LibFunGenerator = new LibFunGenerator()

  val regTracker = new RegisterTracker

  val lazyLabelToInstruction : Map[String, List[Instruction]] = new HashMap[String, List[Instruction]]()

  val stringLiterals: Map[String, String] = Map()
  var rodataDirectives: ListBuffer[Directive] = ListBuffer()
  var labelCounter: Int = 0

  /** Adds a string literal to the rodata section of the assembly code
    * @param literal
    *   \- The string literal to be added
    * @return
    *   The label of the string literal
    */
  def addStringLiteral(literal: String): String = {
    stringLiterals.getOrElseUpdate(
      literal, {
        val label = s"string${stringLiterals.size}"
        rodataDirectives += Directive(s"int ${literal.length()}")
        // account for \0 \b \t \n \f \r \" \' \\
        val escapedLiteral = (literal + "\\0")
          .replace("\\", "\\\\")
          .replace("\'", "\\\'")
          .replace("\"", "\\\"")
          // .replace("\u0000", "\\0")
          .replace("\b", "\\b")
          .replace("\t", "\\t")
          .replace("\n", "\\n")
          .replace("\f", "\\f")
          .replace("\r", "\\r")
        rodataDirectives += Directive(s"$label: .asciz \"$escapedLiteral\"")
        label
      }
    )
  }

  /** Generates the intermediate representation for the given AST
    * @param ast
    *   \- The AST of the program to be translated
    * @return
    *   The intermediate representation of the program
    */
  def generateIR(ast: Program): Buffer[Instruction] = {
    val instructions: Buffer[Instruction] = new ListBuffer[Instruction].empty

    // Add the necessary directives
    val initDirectives = List(
      Directive("intel_syntax noprefix"),
      Directive("globl main")
    )

    val mainInitialisation = ListBuffer(
      Directive("text"),
      Label("main")
    )

    // instructions ++= mainInitialisation

    ast.symbolTable.printSymbolTable()

    // Add the main frame
    val addMainFrame = StackMachine.addFrame(ast.symbolTable, None)

    val intermediateRepresentation = astToIR(ast)

    // Pop the main frame
    val decrementStackInstr = StackMachine.popFrame()
    // instructions ++= decrementStackInstr

    instructions ++= initDirectives
    instructions ++= Directive("section .rodata") +: rodataDirectives
    instructions ++= mainInitialisation
    instructions ++= addMainFrame
    instructions ++= intermediateRepresentation
    instructions ++= decrementStackInstr

    instructions += Mov(
      Dest,
      Immediate32(0),
      InstrSize.fullReg 
    ) // Return 0 if no exit function
    instructions ++= List(
      ReturnInstr()
    )


    // add lazy labels to instructions

    for ((label, instrs) <- lazyLabelToInstruction) {
      instructions ++= ListBuffer(
        Label(s"_${label}")
      ) ++= instrs ++= ListBuffer(
        ReturnInstr()
      )
    }

    // Add the library functions
    instructions ++= lib.addLibFuns()

    // Add the rodata directives
    instructions ++= functionGenerator.generateFunctionCode()

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
    case prog @ Program(funcList, stat) => {

      val funcIR = new ListBuffer[Instruction]

      for (func <- funcList) {
        functionGenerator.addFunction(func.ident.value, func, ListBuffer())
      }

      val ir = new ListBuffer[Instruction]
      val statInstrs = for (s <- stat) yield statToIR(s)
      ir ++= statInstrs.flatten
      ir.appendAll(funcIR)
    }
  }

  def generateFunctionIR(func: Func, table: SymbolTable, paramList: ParamList): ListBuffer[Instruction] = {

    val setupStack = StackMachine.addFrame(table, Some(paramList))


    val body = {
      val table = func.symbolTable
      val stats = for (s <- func.statList) yield statToIR(s)
      stats.flatten
    }

    val popStack = StackMachine.popFrame()

    var modifiedBody = ListBuffer[Instruction]()
    var foundReturn = false


    for (instr <- body) {
      if (instr.isInstanceOf[ReturnInstr]) {
        foundReturn = true
        modifiedBody ++= popStack
      }
      modifiedBody += instr
    }

    // ListBuffer() ++ setupStack ++ modifiedBody
    if (foundReturn) {
      ListBuffer() ++ setupStack ++ modifiedBody
    } else {
      // Account for void functions with no returns
      ListBuffer() ++ setupStack ++ modifiedBody ++ popStack
    }

  }

  // ------- Stack Machine Abstraction -------------------------------------------//


  /**
    * Moves the stack pointer to the address of the variable
    * 
    * @param fpchange The change in the frame pointer
    * @return a pair of instruction lists - (a) to move the stack pointer to the address of the variable and (b) to restore the stack to the previous state
    */
  def goToVarStackAddress(fpchange : Int) : (ListBuffer[Instruction], ListBuffer[Instruction]) = {
    goToVarStackAddress(fpchange, 0)
  }

  /**
    * Moves the stack pointer to the address of the variable
    * 
    * @param fpchange The change in the frame pointer
    * @param offset The offset of the variable within the frame (taken from the start of the next frame)
    * @return a pair of instruction lists - (a) to move the stack pointer to the address of the variable and (b) to restore the stack to the previous state
    */
  def goToVarStackAddress(fpchange : Int, offset : Int) : (ListBuffer[Instruction], ListBuffer[Instruction]) = {
    (ListBuffer(
      AddInstr(SP, Immediate32(fpchange + offset), InstrSize.fullReg),
      PopRegisters(List(FP), InstrSize.fullReg)
    ), 
    ListBuffer(
      PushRegisters(List(FP), InstrSize.fullReg),
      SubInstr(SP, Immediate32(fpchange + offset), InstrSize.fullReg),
      Mov(FP, SP, InstrSize.fullReg)
    ))
  } 

  /**
    * Finds the variable in the current frame given the offset
    * 
    * @param offset The offset of the variable within the frame
    * @return The list of instructions to find the variable in current frame and move it to the destination register
    */
  def findVarInCurrFrame(offset: Int) : ListBuffer[Instruction] = {
    ListBuffer(
      Mov(Dest, FPOffset(offset), InstrSize.fullReg)
    )
  }

  def moveExprToAddress(offset : Int) : ListBuffer[Instruction] = {
    ListBuffer(
      Mov(FPOffset(offset), Dest, InstrSize.fullReg)
    )
  }

  /**
    * Assigns the value in variable provided to equal the value in the destination register (updates the stack)
    *
    * @param ident The identifier of the variable
    * @return The list of instructions to assign the value in the destination register to the variable
    */
  def findingVarOnStackIR(ident : String, asgnType : AsgnType) : ListBuffer[Instruction] = {
    findingVarOnStackIR(ident, ListBuffer[Instruction](), asgnType)
  }


  /**
    * Given a variable, find it in the stack, complete a set of instructions using the variable and push the variable back onto the stack
    *
    * @param ident The identifier of the variable
    * @param instrs The list of instructions to append to
    * @return The list of instructions to find the variable in the stack and complete the set of instructions using the variable
  */
  def findingVarOnStackIR(ident: String, instrs : ListBuffer[Instruction], asgnType : AsgnType) : ListBuffer[Instruction] = {
    findingVarOnStackIR(ident, instrs, 0, asgnType)
  }

  def processInstructions(
    asgnType: AsgnType,
    offset: Int,
    extraOffset: Int,
    fpchange: Int,
    instrs: ListBuffer[Instruction])
  : ListBuffer[Instruction] = {
    val moveInstr = asgnType match {
      case (Declare | Reassign) => moveExprToAddress(offset - extraOffset)
      case Retrieve => findVarInCurrFrame(offset - extraOffset)
    }
    fpchange match {
      case 0 => instrs ++= moveInstr
      case _ => {
        goToVarStackAddress(fpchange, MAX_REGSIZE) match {
          case (setupStack, restoreStack) => {
            val instructions = ListBuffer[Instruction]().empty
            instructions.prependAll(setupStack)
            instructions ++= moveInstr
            instructions ++= restoreStack
            instrs ++= instructions
          }
          case _ => throw new RuntimeException("Invalid return from attempt to find variable in stack")
        }
      }
    }
  }

  /**
    * Given a variable, find it in the stack, complete a set of instructions using the variable and push the variable back onto the stack
    *
    * @param ident The identifier of the variable
    * @param instrs The list of instructions to append to
    * @param extraOffset The extra offset to add to the variable in the variable's frame
    * @return The list of instructions to find the variable in the stack and complete the set of instructions using the variable
    */
  def findingVarOnStackIR(ident: String, instrs : ListBuffer[Instruction], extraOffset : Int, asgnType : AsgnType) : ListBuffer[Instruction] = {
    StackMachine.offset(ident) match {
      case (Some((offset, fpchange)), true, frame) => {
        processInstructions(
          asgnType,
          offset,
          extraOffset,
          fpchange,
          instrs
        )
      }
      case (Some((offset, fpchange)), false, frame) => {

        asgnType match {
          case (Declare) => {
            val generateVarIR : ListBuffer[Instruction] = processInstructions(asgnType, offset, extraOffset, fpchange, instrs)
            val label = s"lazy_${ident}${StackMachine.stackFrameCounter}"
            val lazyInstructions = generateVarIR
            lazyLabelToInstruction.addOne(label, lazyInstructions.toList)
            frame.addLazyIRLabel(ident, label, lazyInstructions)
            ListBuffer()
          }
          case (Retrieve) => {
            val label = frame.getLazyIRLabel(ident)._1
            // if we are in an inner scope we need to move the frame pointer and stack pointer back to the original 
            // scope before we can execute the lazy instructions
            frame.setDefined(ident)
            fpchange match {
              case 0 => {
                instrs ++= ListBuffer(CallInstr(label))
              }
              case change => {
                goToVarStackAddress(fpchange, MAX_REGSIZE) match {
                  case (setupStack, restoreStack) => {
                    val instructions = ListBuffer[Instruction]().empty
                    instructions.prependAll(setupStack)
                    instructions ++= ListBuffer(CallInstr(label))
                    instructions ++= restoreStack
                    instrs ++= instructions
                  }
                  case _ => throw new RuntimeException("Invalid return from attempt to find variable in stack")
                }
              }
            }
          }
        }
      }
      case _ => throw new RuntimeException(s"Variable ${ident} not found in stack")
    }
  }

  // ------- Statement IR Generation -----------------------------------------------//

  def statToIR(stat: Stat): Buffer[Instruction] = stat match {
    case tc@TryCatchStat(tryStmts, catches) => {
      val exceptionMap = new HashMap[String, Label]()
      val endTryLabelName = s"end_try_${labelCounter}"
      val catchBlocks: ListBuffer[ListBuffer[Instruction]] = ListBuffer()
              /*    trystmststj ..
          *   jmpcond catch1
          *   truyskc
          *   jmpcond catch2
          *   catch1
          *   jmp end
          *   catch2
          *   jmp end
          *   end
         */
      for (c <- catches) {
        val table = c.symbolTable
        val addFrame = StackMachine.addFrame(
          table,
          None
        )
        val label = Label(s"catch_${c.exception.exception}_${StackMachine.stackFrameCounter}")
        val catchStats : ListBuffer[Instruction] = ListBuffer(label) 
        catchStats ++= addFrame
        c.stats.foreach(s => catchStats ++= statToIR(s))
        catchStats ++= StackMachine.popFrame()
        catchStats += Jump(endTryLabelName)
        catchBlocks += catchStats
        val excName = lib.exceptionToErrType.get(c.exception.exception)
        if (excName.isDefined) {
          exceptionMap.addOne(excName.get -> label)
        }
      }
      val instructions : ListBuffer[Instruction] = StackMachine.addFrame(
        tc.symbolTable,
        None
      )
      StackMachine.setExceptionMap(exceptionMap)
      tryStmts.foreach(s => instructions ++= statToIR(s))
      val popFrame = StackMachine.popFrame()
      instructions ++= popFrame ++= ListBuffer(Jump(endTryLabelName))
      catchBlocks.foreach(c => instructions += c.headOption.getOrElse(throw new RuntimeException("problem")) ++= popFrame ++= c.tail)
      instructions += Label(endTryLabelName)
      instructions
    }
    case LazyStat(pos) => {
      val instructions = statToIR(pos)
      instructions
    }
    case be @ BeginEnd(statList) => {
      val body = {
        val table = be.symbolTable
        val addFrame = StackMachine.addFrame(
          table,
          None
        )
        val stats = for (s <- statList) yield statToIR(s)

        val removeFrame = StackMachine.popFrame()
        if (table.dictionary.size > 0) {
          addFrame ++ stats.flatten ++ removeFrame
        } else {
          stats.flatten
        }
      }
      ListBuffer() ++ body
    }

    case AsgnEq(lhs, rhs) => {
      val asgnType = AsgnType.Reassign
      lhs match {
        case Ident(ident) => {
          val instructions = exprToIR(rhs)
          findingVarOnStackIR(ident, instructions, asgnType)       
        }
        // Null dereference check
        // Get the address to write into
        // Get the expression to write
        // Move the expression to the address
        // Clean up
        case FstNode(identifier) => {
          lib.nullDerefOrFree.setFlag(true)
          val instructions = exprToIR(rhs)
          identifier match {
            case Ident(ident) => instructions ++= findingVarOnStackIR(ident, asgnType)
            case _ => throw new RuntimeException("Value (type: " + identifier.typeNode.toString() + ") should not reach this case")
          }
          instructions
        }

        case SndNode(identifier) => {
          lib.nullDerefOrFree.setFlag(true)
          val instructions = exprToIR(rhs)
          identifier match {
            case Ident(ident) => instructions ++= findingVarOnStackIR(ident, ListBuffer[Instruction](), MAX_REGSIZE, asgnType)
            case _ => throw new RuntimeException("Value (type: " + identifier.typeNode.toString() + ") should not reach this case")
          }
          instructions
        }
        
        case ArrayElem(ident, eList) => {
          lib.setArrStore8Flag(true)
          lib.outOfBounds.setFlag(true)
          lib.setArrLoad8Flag(true)
          val instructions = ListBuffer[Instruction]().empty
          val assignment = exprToIR(rhs)

          StackMachine.offset(ident.value) match {
            case (Some((offset, fpchange)), true, _) => fpchange match {
                case 0 => {
                  instructions ++= ListBuffer(
                    Mov(Dest, FPOffset(offset), InstrSize.fullReg)
                  )
                  for (e <- eList.init) {
                    instructions ++= ListBuffer(
                      Mov(Arg5, Dest, InstrSize.fullReg)
                    )
                    instructions ++= exprToIR(e)
                    instructions ++= ListBuffer(
                      Mov(G1, Dest, InstrSize.halfReg),
                      CallInstr("arrLoad8"),
                      Mov(Dest, Arg5, InstrSize.fullReg)
                    )
                  }

                  val lastExpr = exprToIR(eList.last)
                  instructions ++= ListBuffer(
                    Mov(Arg5, Dest, InstrSize.fullReg)
                  ) ++ lastExpr ++ ListBuffer(
                    Mov(G1, Dest, InstrSize.halfReg)
                  ) ++ assignment ++ ListBuffer(
                    CallInstr("arrStore8"),
                  ) 
                }
                case _ => {
                  val setup = ListBuffer(
                        AddInstr(SP, Immediate32(fpchange + 8), InstrSize.fullReg),
                        PopRegisters(List(FP), InstrSize.fullReg),
                        Mov(Dest, FPOffset(offset), InstrSize.fullReg),
                        PushRegisters(List(FP), InstrSize.fullReg),
                        SubInstr(SP, Immediate32(fpchange + 8), InstrSize.fullReg),
                        Mov(FP, SP, InstrSize.fullReg)
                      )

                  for (e <- eList.init) {
                    instructions ++= ListBuffer(
                      Mov(Arg5, Dest, InstrSize.fullReg)
                    )
                    instructions ++= exprToIR(e)
                    instructions ++= ListBuffer(
                      Mov(G1, Dest, InstrSize.halfReg),
                      CallInstr("arrLoad8"),
                      Mov(Dest, Arg5, InstrSize.fullReg)
                    )
                  }
                  val lastExpr = exprToIR(eList.last)
                  instructions ++= ListBuffer(
                    Mov(Arg5, Dest, InstrSize.fullReg)
                  ) ++ lastExpr ++ ListBuffer(
                    Mov(G1, Dest, InstrSize.halfReg)
                  ) ++ assignment ++ ListBuffer(
                    CallInstr("arrStore8"),
                  )

                  instructions.prependAll(setup)
                }
            }
            case (Some((offset, fpchange)), false, _) => {
              ???
            }
            case _ => {
              throw new RuntimeException(
                s"Variable ${ident} not found in stack"
              )
            }
          }

        }
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // TODO: need to have cases for pairs and array elements later
      }
    }

    case i@IdentAsgn(typeNode, ident, rhs) => {


      val asgnType = Declare

      // // Step 1: Allocate space on the stack based on the variable size
      val instructions = ListBuffer[Instruction]().empty

      // Step 2: Initialize the variable with the given expression
      val preInstr = typeNode match {
        case atn @ ArrayTypeNode(_) => {

          /** Use G2 for array pointers for time being when assigning arrays as
            * a special case of the calling convention.
            *
            * NOTE: This will need to change for a register tracker
            * implementation
            */
          rhs match {
            case ArrayLiter(arrayElements) => {
              val arrSize = arrayElements.size
              atn.setLength(arrSize)
              List()
            }
            case _ => ListBuffer[Instruction]().empty 
          }

        }
        // If lhs is a pairtype node of any types
        case PairTypeNode(_, _) => {
          rhs match {
            // Dynamically allocate memory for the pair (16 Bytes)
            case NewPair(_, _) => {
              List(
                // mov edi, 16
                Mov(
                  Arg0,
                  Immediate32(MAX_REGSIZE * 2),
                  InstrSize.halfReg
                ),
                // call _malloc
                CallInstr("malloc"),
                // mov r11, rax
                Mov(G2, Dest, InstrSize.fullReg) 
              )
            }
            case _ => ListBuffer.empty
          }
        }
        case _ => ListBuffer[Instruction]().empty
      }

      instructions ++= exprToIR(rhs)
      instructions.prependAll(preInstr)

        // Step 3: Update StackMachine context with the new variable
        StackMachine.putVarOnStack(ident.value)
        //Can abstract stackmachine.offset part of the function
        // can abstract Som
        findingVarOnStackIR(ident.value, instructions, asgnType)
    }
    case Print(expr) => {
      printToIR(expr, false)
    }
    case Println(expr) => {
      printToIR(expr, true)
    }
    case Exit(expr) => {
      lib.setExitFlag(true)
      exprToIR(expr) ++ ListBuffer(
        Mov(Arg0, Dest, InstrSize.fullReg),
        CallInstr("exit")
      )
    }
    case whileNode @ While(expr, stats) => {
      val cond = exprToIR(expr)
      val body = {
        val table = whileNode.symbolTable
        val setupStack = StackMachine.addFrame(
          table,
          None
        )
        val loopStats = for (s <- stats) yield statToIR(s)
        val popStack = StackMachine.popFrame()
        if (table.dictionary.size > 0) {
          setupStack ++ loopStats.flatten ++ popStack
        } else {
          loopStats.flatten
        }
      }
      labelCounter += 1

      ListBuffer(Jump(s"cond${labelCounter}")) ++ ListBuffer(
        Label(s"while${labelCounter}")
      ) ++ body ++ ListBuffer(
        Label(s"cond${labelCounter}")
      ) ++ cond ++ ListBuffer(
        Cmp(Dest, Immediate32(0), InstrSize.fullReg),
        JumpIfCond(s"while${labelCounter}", InstrCond.notEqual)
      )
    }
    case ifNode @ If(expr, trueCase, falseCase) => {
      val cond = exprToIR(expr)
      val thenCase = {
        val table = ifNode.symbolTableTrue
        val setupStack = StackMachine.addFrame(
          table,
          None
        )
        val trueStats = for (s <- trueCase) yield statToIR(s)
        val popStack = StackMachine.popFrame()
        if (table.dictionary.size > 0) {
          setupStack ++ trueStats.flatten ++ popStack
        } else {
          trueStats.flatten
        }
      }
      val elseCase = {
        val table = ifNode.symbolTableFalse
        val setupStack = StackMachine.addFrame(
          table,
          None
        )
        val falseStats = for (s <- falseCase) yield statToIR(s)
        val popStack = StackMachine.popFrame()
        if (table.dictionary.size > 0) {
          setupStack ++ falseStats.flatten ++ popStack
        } else {
          falseStats.flatten
        }
      }
      labelCounter += 1
      cond ++= ListBuffer(
        Cmp(Dest, Immediate32(0), InstrSize.fullReg),
        JumpIfCond(s"else${labelCounter}", InstrCond.equal)
      ) ++= thenCase ++= ListBuffer(
        Jump(s"end${labelCounter}"),
        Label(s"else${labelCounter}")
      ) ++= elseCase ++= ListBuffer(
        Label(s"end${labelCounter}")
      )
    }
    case Return(expr) => {
      exprToIR(expr) ++ ListBuffer(
        ReturnInstr()
      )
    }
    case Skip() => {
      ListBuffer(
      )
    }
    case Free(expr) => {
      val instructions = ListBuffer[Instruction]().empty
      var callInstr: ListBuffer[Instruction] = ListBuffer[Instruction]().empty
      expr.typeNode match {
        case ArrayTypeNode(_) => {
          lib.setFreeArrayFlag(true)
          // Decrement the pointer to the start of the array
          callInstr += SubInstr(Arg0, Immediate32(HALF_REGSIZE), InstrSize.fullReg)
          callInstr += CallInstr("free")          
        }
        case PairTypeNode(_, _) => {
          lib.setFreePairFlag(true)
          callInstr += CallInstr("freepair")
        }
      }

      expr match {
        case Ident(ident) => {
          StackMachine.offset(ident) match {
            case (Some((offset, fpchange)), true, _) => {
              fpchange match {
                case 0 => {
                  // Use offset as start of stack
                  instructions ++= ListBuffer(
                    Mov(Arg0, G2, InstrSize.fullReg)
                  )
                  instructions ++= callInstr
                }
                case _ => {
                  val stackSetup = ListBuffer(
                    // add rsp, fpchange
                    AddInstr(SP, Immediate32(fpchange), InstrSize.fullReg),
                    // pop rbp
                    PopRegisters(List(FP), InstrSize.fullReg)
                  )
                  val reverseStackSetup = ListBuffer(
                    // push rbp
                    PushRegisters(List(FP), InstrSize.fullReg),
                    // sub rsp, fpchange
                    SubInstr(SP, Immediate32(fpchange), InstrSize.fullReg),
                    // mov rbp, rsp
                    Mov(FP, SP, InstrSize.fullReg)
                  )

                  instructions ++= stackSetup
                  instructions ++= ListBuffer(
                    Mov(Arg0, G2, InstrSize.fullReg)
                  ) 
                  instructions ++= callInstr 
                  instructions ++= reverseStackSetup
                }
              }
            }
            case (Some((offset, fpchange)), false, _) => {
              ???
            }
            case _ => {
              throw new RuntimeException(
                s"Variable ${ident} not found in stack"
              )
            }
          }
        }
      }

      instructions
    }
    case Read(lhs) => {
      val instructions = ListBuffer[Instruction]().empty

      val callReadLabel = lhs.typeNode match {
        case IntTypeNode() => {
          lib.setReadIntFlag(true)
          CallInstr("readi")
        }
        case CharTypeNode() => {
          lib.setReadCharFlag(true)
          CallInstr("readc")
        }
      }

      // Check lhs is an identifier
      lhs match {
        case Ident(ident) => {
          // Find position in stack
          StackMachine.offset(ident) match {
            case (Some((offset, fpchange)), true, _ ) => {
              val readLogic = ListBuffer(
                // mov rax, qword ptr [rbp - offset]
                Mov(Dest, FPOffset(offset), InstrSize.fullReg),
                // mov rdi, rax
                Mov(Arg0, Dest, InstrSize.fullReg),
                // call _readi / _readc depending on lhs.typeNode
                callReadLabel,
                // mov qword ptr [rbp - offset], rax
                Mov(FPOffset(offset), Dest, InstrSize.fullReg)
              )

              fpchange match {
                case 0 => {
                  // Use offset as start of stack
                  instructions ++ readLogic
                }
                case _ => {
                  goToVarStackAddress(fpchange) match {
                    case (setup, restore) => {
                      instructions ++= setup ++ readLogic ++ restore
                    }
                    case _ => {
                      throw new RuntimeException(
                        s"Variable ${ident} not found in stack"
                      )
                    }
                  }
                }
              }
            }
            case (Some((offset, fpchange)), false, _) => {
              ???
            }
            case _ => {
              throw new RuntimeException(
                s"Variable ${ident} not found in stack"
              )
            }
          }
        }
        case _ => {
          // Message log for value should not reach this case
          throw new RuntimeException(
            "Value (type: " + lhs.typeNode.toString() +
              ") should not reach this case"
          )
        }
      }
    }

    /* EXTENSION - Void Types */
    case CallVoid(ident, paramList) => {
      val instructions = new ListBuffer[Instruction]().empty
      val functionNode = functionGenerator.getFunctionNode(ident.value)
      
      val handleParams = ListBuffer[Instruction]().empty
      for (param <- paramList) {
        handleParams ++= exprToIR(param)
        handleParams ++= ListBuffer(
          PushRegisters(List(Dest), InstrSize.fullReg)
        )
      }

      val incrementStackInstr = new ListBuffer[Instruction]().empty
      if (paramList.size > 0) {
        for (i <- 0 until paramList.size) {
          incrementStackInstr ++= ListBuffer(
            PopRegisters(List(G0), InstrSize.fullReg)
          )
        }
      }

      if (!functionGenerator.isDefined(functionNode.ident.value)) {
        functionGenerator.setDefined(functionNode.ident.value)

        val funcBody = generateFunctionIR(
          functionNode, 
          functionGenerator.getFunctionTable(ident.value), 
          functionNode.paramList
        )

        functionGenerator.addFunction(
          functionNode.ident.value,
          functionNode,
          funcBody
        )

        functionGenerator.addFunction(ident.value, functionNode, funcBody)
      }

      instructions ++= handleParams 
      instructions += CallInstr(ident.value)
      instructions ++= incrementStackInstr
    }

  }

  def matchPrintType(typeNode: TypeNode): ListBuffer[Instruction] = {
    typeNode match {
      case StringTypeNode() => {
        lib.setPrintStringFlag(true)
        ListBuffer(
          Mov(Arg0, Dest, InstrSize.fullReg),
          CallInstr("prints")
        )
      }
      case BoolTypeNode() => {
        lib.setPrintBoolFlag(true)
        ListBuffer(
          Mov(Arg0, Dest, InstrSize.fullReg),
          CallInstr("printb")
        )
      }
      case CharTypeNode() => {
        lib.setPrintCharFlag(true)
        ListBuffer(
          Mov(Arg0, Dest, InstrSize.fullReg),
          CallInstr("printc")
        )
      }
      case IntTypeNode() => {
        lib.setPrintIntFlag(true)
        ListBuffer(
          Mov(Arg0, Dest, InstrSize.fullReg),
          CallInstr("printi")
        )
      }
      // TODO: Fix once char arrays stored at 1 byte buffer
      case atn @ ArrayTypeNode(CharTypeNode()) => {
        lib.setPrintStringFlag(true)
        ListBuffer(
          Mov(Arg0, G2, InstrSize.fullReg),
          CallInstr("prints")
        )
      }
      case atn @ ArrayTypeNode(typeNode) => {
        // atn can be a nested array type node, need to unwrap and get the inner
        var node : TypeNode = atn
        var arrType = atn.elementType

        print("Arrays inner type is: " + arrType.toString())
        
        matchPrintType(arrType)
      }
      case _ => {
        // Must be Pair
        lib.setPrintPtrFlag(true)
        ListBuffer(
          Mov(Arg0, G2, InstrSize.fullReg),
          CallInstr("printp")
        )
      }
    }
  }
  
  def printToIR(expr: Expr, println: Boolean): Buffer[Instruction] = {

    val instructions = ListBuffer[Instruction]().empty
    val printCall = matchPrintType(expr.typeNode)

    instructions ++= exprToIR(expr) 
    instructions ++= ListBuffer(
      DecrementStackPointerNB(8),
      // Mov(Arg0, Dest, InstrSize.fullReg) --> might not need this
    ) 
    instructions ++= printCall
    if (println) {
      lib.setPrintLnFlag(true)
      instructions ++= ListBuffer(CallInstr("println"))
    }
    instructions ++= ListBuffer(
      IncrementStackPointerNB(8)
    )
  }

  // --------------- Expression IR Generation -------------------------------------//

  def pairExprToIR(e: Expr, ptype : String) : ListBuffer[Instruction] = {
    val asgnType = AsgnType.Retrieve
    e match {
      case Ident(name) => {
        val instrs = ListBuffer.empty[Instruction]
        instrs ++= (ptype match {
          case "Fst" => {
            findingVarOnStackIR(name, ListBuffer(), asgnType)
          }
          case "Snd" => {
            findingVarOnStackIR(name, ListBuffer(), MAX_REGSIZE, asgnType)
          }
          case _ => {
            throw new RuntimeException("Invalid pair type")
          }
        })
        instrs          
      }
      case _ => exprToIR(e)
    }
  }

  // Never lhs
  def exprToIR(expr: Position): ListBuffer[Instruction] = expr match {
    case Brackets(expr) => {
      exprToIR(expr)
    }
    case Not(expr) => {
      exprToIR(expr) ++ ListBuffer(
        Cmp(Dest, Immediate32(0), InstrSize.fullReg),
        SetByteIfCond(Dest, InstrCond.equal, InstrSize.eigthReg),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
      )
    }

    case Len(expr) => {
      lib.setPrintIntFlag(true)
      lib.setPrintLnFlag(true)
      val instructions : ListBuffer[Instruction] = exprToIR(expr)
      instructions ++ ListBuffer(
        MovWithSignExtend(Dest, RegisterPtr(Dest, InstrSize.halfReg, -1 * HALF_REGSIZE), InstrSize.fullReg, InstrSize.halfReg),
        Mov(Arg0, Dest, InstrSize.halfReg),
        CallInstr("printi"),
        CallInstr("println")
      )
    }

    case Neg(int) => {
      lib.overflow.setFlag(true)
      exprToIR(int) ++ ListBuffer(
        PushRegisters(List(Dest), InstrSize.fullReg),
        PopRegisters(List(G0), InstrSize.fullReg),
        Mov(Dest, Immediate32(0), InstrSize.halfReg),
        SubInstr(Dest, G0, InstrSize.halfReg),
        JumpIfCond(lib.overflow.getJumpLabel, InstrCond.overflow),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.halfReg)
      )
    }
    case Chr(int) => {
      lib.badChar.setFlag(true)
      exprToIR(int) ++ ListBuffer(
        TestInstr(Dest, Immediate32(-128), InstrSize.fullReg),
        CheckMoveNotEqual(Arg1, Dest, InstrSize.fullReg),
        JumpIfCond(lib.badChar.getJumpLabel, InstrCond.notEqual)
      )
    }
    case Ord(char) => {
      lib.overflow.setFlag(true)
      exprToIR(char) 
    }
    case IntLiter(value) => {

      val instructions = ListBuffer[Instruction]().empty
      // If the expression is an integer literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value), InstrSize.fullReg)

    }
    // Handle other types of expressions
    case BoolLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a boolean literal, we can simply move the value to the variable
      instructions += Mov(
        Dest,
        Immediate32(if (value) 1 else 0),
        InstrSize.fullReg
      )

    }
    case CharLiter(value) => {
      val instructions = ListBuffer[Instruction]().empty
      // If the expression is a character literal, we can simply move the value to the variable
      instructions += Mov(Dest, Immediate32(value.toInt), InstrSize.fullReg)
    }
    case StringLiter(value) => {
      val label = addStringLiteral(value)
      ListBuffer(
        LoadEffectiveAddress(
          Dest,
          RegisterLabelAddress(IP, LabelAddress(label)),
          InstrSize.fullReg
        )
      )
    }

    case Null() => {
      val instructions = ListBuffer[Instruction](
        // mov rax, 0
        Mov(Dest, Immediate32(0), InstrSize.fullReg),
        // mov r12, rax
        Mov(G2, Dest, InstrSize.fullReg),
      )
      instructions
    }

    case f @ FstNode(e) => pairExprToIR(e, "Fst")
    case s @ SndNode(e) => pairExprToIR(e, "Snd")

    case NewPair(fst, snd) => {
      lib.setMallocFlag(true)

      // int[] a = [1,2,3]
      // pair b = newpair(a, a)
      
      val firstIR : ListBuffer[Instruction] = exprToIR(FstNode(fst)(fst.pos))
      val secondIR : ListBuffer[Instruction] = exprToIR(SndNode(snd)(snd.pos))
      
      val instructions = firstIR 
      instructions ++= ListBuffer[Instruction](
        // mov qword ptr [r11], rax
        Mov(RegisterPtr(G2, InstrSize.fullReg, 0), Dest, InstrSize.fullReg)
      ) 
      instructions ++= secondIR 
      instructions ++= ListBuffer[Instruction](
        // mov qword ptr [r11 + 8], rax
        Mov(RegisterPtr(G2, InstrSize.fullReg, 8), Dest, InstrSize.fullReg),
        // mov rax, r11 --> store the address of the pair in rax
        Mov(Dest, G2, InstrSize.fullReg),
        // mov r12, rax --> store the address of the pair in r12
        Mov(G2, Dest, InstrSize.fullReg)
      )

      instructions      
    }

    case ArrayElem(ident, eList) => { // x[0][1]
      lib.setArrLoad8Flag(true)
      lib.outOfBounds.setFlag(true)

      val instructions: ListBuffer[Instruction] = ListBuffer()

      StackMachine.offset(ident.value) match {
        case (Some((offset, fpchange)), true, _) => {
          fpchange match {
            case 0 => {

              instructions ++= findVarInCurrFrame(offset)

              for (e <- eList) {
                instructions ++= ListBuffer(Mov(Arg5, Dest, InstrSize.fullReg))
                instructions ++= exprToIR(e)
                // G1 represents the index of the array
                // Arg5 represents the address of the array
                instructions ++= ListBuffer(
                  Mov(G1, Dest, InstrSize.halfReg),
                  CallInstr("arrLoad8"),
                  Mov(Dest, Arg5, InstrSize.fullReg)
                )
              }
            }
            case _ => {
              val setup = ListBuffer(
                AddInstr(
                  SP,
                  Immediate32(fpchange + MAX_REGSIZE),
                  InstrSize.fullReg
                ),
                PopRegisters(List(FP), InstrSize.fullReg)
              )
              instructions ++= setup ++ ListBuffer(
                Mov(Dest, FPOffset(offset), InstrSize.fullReg),
                Mov(Arg5, Dest, InstrSize.fullReg),
                PushRegisters(List(FP), InstrSize.fullReg),
                SubInstr(
                  SP,
                  Immediate32(fpchange + MAX_REGSIZE),
                  InstrSize.fullReg
                ),
                Mov(FP, SP, InstrSize.fullReg),
                CallInstr("arrLoad8"),
                Mov(Dest, Arg5, InstrSize.fullReg)
              )
            }
          }
        }
        case (Some((offset, fpchange)), false, _) => {
          ???
        }
        case _ => {
          throw new RuntimeException("Variable not found in stack")
        }
      }

      instructions

    }
    case ArrayLiter(entries) => {
      // Assume special calling convention for array stores - r11 is used to store the array pointer
      var offset: Int =
        0 // As we initially store the size of the array in the first 4 bytes

      lib.setMallocFlag(true)      
      val instructions: ListBuffer[Instruction] = ListBuffer(
        Mov(
          Arg0,
          Immediate32((entries.size) * MAX_REGSIZE + HALF_REGSIZE),
          InstrSize.halfReg
        ),
        CallInstr("malloc"),
        Mov(G2, Dest, InstrSize.fullReg),
        Mov(G2, G2, InstrSize.fullReg),
        AddInstr(G2, Immediate32(HALF_REGSIZE), InstrSize.fullReg),
        Mov(Dest, Immediate32(entries.size), InstrSize.fullReg),
        Mov(
          RegisterPtr(G2, InstrSize.halfReg, (-1) * HALF_REGSIZE),
          Dest,
          InstrSize.halfReg
        )
      )
      for (e <- entries) {
        instructions ++= exprToIR(e)
        instructions += Mov(
          RegisterPtr(G2, InstrSize.fullReg, offset),
          Dest,
          InstrSize.fullReg
        )
        e.typeNode match {
          case CharTypeNode() => {
            offset += BYTE
          }
          case _ => {
            offset += MAX_REGSIZE
          }
        }
      }
      instructions += Mov(Dest, G2, InstrSize.fullReg)
      instructions
    }
    case Ident(ident) => {
      findingVarOnStackIR(ident, AsgnType.Retrieve)
    }
    case Equals(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.equal)
    }
    case NotEquals(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.notEqual)
    }
    case And(expr1, expr2) => {
      // evaluate each expression before performing the and operation
      val setup = binOpSetup(expr1, expr2)
      labelCounter += 1
      setup ++= ListBuffer(
        Cmp(G1, Immediate32(1), InstrSize.fullReg),
        JumpIfCond(s"and_false${labelCounter}", InstrCond.notEqual),
        Cmp(G2, Immediate32(1), InstrSize.fullReg),
        Label(s"and_false${labelCounter}"),
        SetByteIfCond(Dest, InstrCond.equal, InstrSize.eigthReg),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
      )
    }
    case Or(expr1, expr2) => {
      val setup = binOpSetup(expr1, expr2)
      labelCounter += 1
      setup ++= ListBuffer(
        Cmp(G1, Immediate32(1), InstrSize.fullReg),
        JumpIfCond(s"or_true${labelCounter}", InstrCond.equal),
        Cmp(G2, Immediate32(1), InstrSize.fullReg),
        Label(s"or_true${labelCounter}"),
        SetByteIfCond(Dest, InstrCond.equal, InstrSize.eigthReg),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
      )
    }

    /* EXTENSION - Bitwise Operators */
    case BitAnd(expr1, expr2) => {
      val setup = binOpSetup(expr1, expr2)
      setup ++= ListBuffer(
        Mov(Dest, G1, InstrSize.fullReg),
        AndInstr(Dest, G2, InstrSize.fullReg)
      )
    }
    case BitOr(expr1, expr2) => {
      val setup = binOpSetup(expr1, expr2)
      setup ++= ListBuffer(
        Mov(Dest, G1, InstrSize.fullReg),
        OrInstr(Dest, G2, InstrSize.fullReg)
      )
    }
    case BitNot(expr) => {
      exprToIR(expr) += NotInstr(Dest, InstrSize.fullReg)
    }

    case GreaterThan(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.greaterThan)
    }
    case LessThan(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.lessThan)
    }
    case GreaterThanEq(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.greaterThanEqual)
    }
    case LessThanEq(expr1, expr2) => {
      compBinOp(expr1, expr2, InstrCond.lessThanEqual)
    }
    case Plus(expr1, expr2) => {
      intBinOp(expr1, expr2, ArithmOperations.add)
    }
    case Minus(expr1, expr2) => {
      intBinOp(expr1, expr2, ArithmOperations.sub)
    }
    case Mul(expr1, expr2) => {
      intBinOp(expr1, expr2, ArithmOperations.mul)
    }
    case Div(expr1, expr2) => {
      intBinOp(expr1, expr2, ArithmOperations.div)
    }
    case Mod(expr1, expr2) => {
      intBinOp(expr1, expr2, ArithmOperations.mod)
    }
    case Call(ident, paramList) => {
      val instructions = new ListBuffer[Instruction]().empty
      val functionNode = functionGenerator.getFunctionNode(ident.value)
      val handleParams = ListBuffer[Instruction]().empty

      for (param <- paramList) {
        handleParams ++= exprToIR(param)
        handleParams ++= ListBuffer(
          PushRegisters(List(Dest), InstrSize.fullReg)
        )
      }

      val incrementStackInstr = new ListBuffer[Instruction]().empty

      if (paramList.size > 0) {
        for (i <- 0 until paramList.size) {
          incrementStackInstr ++= ListBuffer(
            PopRegisters(List(G0), InstrSize.fullReg)
          )
        }
      }


      if (!functionGenerator.isDefined(functionNode.ident.value)) {

        functionGenerator.setDefined(functionNode.ident.value)

        val funcBody = generateFunctionIR(functionNode, functionGenerator.getFunctionTable(ident.value), functionNode.paramList)
        functionGenerator.addFunction(
          functionNode.ident.value,
          functionNode,
          funcBody
        )

        functionGenerator.addFunction(ident.value, functionNode, funcBody)

      }


      instructions ++= handleParams += CallInstr(ident.value) ++= incrementStackInstr

    }
  }

  def binOpSetup(expr1: Expr, expr2: Expr): ListBuffer[Instruction] = {
    val expr1IR = exprToIR(expr1)
    val expr2IR = exprToIR(expr2)

    val leftPrecedence = expr1IR ++ ListBuffer(
      PushRegisters(List(Dest), InstrSize.fullReg)
    ) ++ expr2IR ++ ListBuffer(
      Mov(G2, Dest, InstrSize.fullReg),
      PopRegisters(List(G1), InstrSize.fullReg),
    )

    val rightPrecedence = expr2IR ++ ListBuffer(
      PushRegisters(List(Dest), InstrSize.fullReg)
    ) ++ expr1IR ++ ListBuffer(
      Mov(G2, Dest, InstrSize.fullReg),
      PopRegisters(List(G1), InstrSize.fullReg),
    )

    expr2 match {
      case Brackets(_) => {
        expr1 match {
          case Brackets(_) => leftPrecedence
          case _           => rightPrecedence

        }
      }
      case _ => leftPrecedence
    }

  }

  def intBinOp(expr1: Expr, expr2: Expr, operation: ArithmOperations): ListBuffer[Instruction] = {
    val setup = binOpSetup(expr1, expr2)
    setup ++= ListBuffer(
      Mov(Dest, G1, InstrSize.halfReg)
    )

    def generalOp(operation: ArithmOperations, jumpLabel: String, cond: InstrCond) = {
      lib.overflow.setFlag(true)
      val instr = operation match {
        case ArithmOperations.add => AddInstr(Dest, G2, InstrSize.halfReg)
        case ArithmOperations.sub => SubInstr(Dest, G2, InstrSize.halfReg)
        case ArithmOperations.mul => MulInstr(Dest, G2, InstrSize.halfReg)
        case _ => throw new RuntimeException("Not a generalized operation")
      }
      setup ++= ListBuffer(
        instr,
        JumpIfCond(jumpLabel, cond),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.halfReg)
      )
    }

    operation match {
      case ArithmOperations.add =>
        generalOp(operation, lib.overflow.getJumpLabel, InstrCond.overflow)
      case ArithmOperations.sub =>
        generalOp(operation, lib.overflow.getJumpLabel, InstrCond.overflow)
      case ArithmOperations.mul =>
        generalOp(operation, lib.overflow.getJumpLabel, InstrCond.overflow)
      case ArithmOperations.div | ArithmOperations.mod => {
        lib.divideByZero.setFlag(true)
        setup ++= ListBuffer(
          Cmp(
            G2,
            Immediate32(0),
            InstrSize.fullReg
          ), // Check for divide by zero
          JumpIfCond(lib.divideByZero.getJumpLabel, InstrCond.equal),
          ConvertDoubleWordToQuadWord(),
          DivInstr(Dest, G2, InstrSize.halfReg)
        )

        if (operation == ArithmOperations.mod) {
          setup ++= ListBuffer(
            Mov(Dest, Arg2, InstrSize.halfReg),
            Mov(Dest, Dest, InstrSize.halfReg)
          )
        }

        setup ++= ListBuffer(
          MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.halfReg)
        )
      }
      case _ => throw new RuntimeException("Invalid operation")

    }

  }

  def compBinOp(expr1: Expr, expr2: Expr, comparison: InstrCond) : ListBuffer[Instruction] = {
    val setup = binOpSetup(expr1, expr2)
    setup ++= ListBuffer(
      Cmp(G1, G2, InstrSize.fullReg),
      SetByteIfCond(Dest, comparison, InstrSize.eigthReg),
      MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.eigthReg)
    )
  }

  // for testing
  def reset(): Unit = {
    stringLiterals.clear()
    rodataDirectives.clear()
    lib.reset()
    lazyLabelToInstruction.clear()
    functionGenerator.reset()
    labelCounter = 0
  }

}

case class VariableScope(scope: Int, name: String)
