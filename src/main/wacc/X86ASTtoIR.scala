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

    instructions ++= lib.addLibFuns()

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

  def generateFunctionIR(func: Func): ListBuffer[Instruction] = {

    val body = {
      val table = func.symbolTable
      val stats = for (s <- func.statList) yield statToIR(s)
      stats.flatten
    }

    ListBuffer() ++ body

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
      case Some((offset, fpchange)) => {
        val moveInstr = asgnType match {
          case Reassign => moveExprToAddress(offset - extraOffset)
          case (Declare | Retrieve) => findVarInCurrFrame(offset - extraOffset)
        }
        fpchange match {
          case 0 => instrs ++= moveInstr
          case _ => {
            goToVarStackAddress(fpchange, MAX_REGSIZE) match {
              case (setupStack, restoreStack) => {
                instrs.prependAll(setupStack) 
                instrs ++= moveInstr
                instrs ++= restoreStack
              }
              case _ => throw new RuntimeException("Invalid return from attempt to find variable in stack")
            }
          }
        }
      }
      case None => throw new RuntimeException(s"Variable ${ident} not found in stack")
    }
  }

  // ------- Statement IR Generation -----------------------------------------------//

  def statToIR(stat: Stat): Buffer[Instruction] = stat match {
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
            case Some((offset, fpchange)) => {
              fpchange match {
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
              }
            }
            case None => {
              throw new RuntimeException(
                s"Variable ${ident} not found in stack"
              )
            }
          }

          instructions
        }
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // TODO: need to have cases for pairs and array elements later
      }
    }

    case IdentAsgn(typeNode, ident, expr) => {
      val asgnType = AsgnType.Declare

      // // Step 1: Allocate space on the stack based on the variable size
      val instructions = ListBuffer[Instruction]().empty

      // Step 2: Initialize the variable with the given expression
      val preInstr = typeNode match {
        case atn @ ArrayTypeNode(_) => {
          lib.setMallocFlag(true)
          lib.outOfMemory.setFlag(true)

          /** Use G2 for array pointers for time being when assigning arrays as
            * a special case of the calling convention.
            *
            * NOTE: This will need to change for a register tracker
            * implementation
            */
          expr match {
            case ArrayLiter(arrayElements) => {
              val arrSize = arrayElements.size
              atn.setLength(arrSize)
              List(
                Mov(
                  Arg0,
                  Immediate32((arrSize) * MAX_REGSIZE + HALF_REGSIZE),
                  InstrSize.halfReg
                ),
                CallInstr("malloc"),
                // I believe we use stack frame allocation for this
                Mov(G2, Dest, InstrSize.fullReg),
                AddInstr(G2, Immediate32(HALF_REGSIZE), InstrSize.fullReg)
              )

            }
            case _ => ListBuffer[Instruction]().empty 
          }

        }
        case PairTypeNode(_, _) => {
          expr match {
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
            case _ => ListBuffer[Instruction]().empty
          }
        }
        case _ => ListBuffer[Instruction]().empty
      }

      instructions ++= exprToIR(expr)
      instructions.prependAll(preInstr)

      // Step 3: Update StackMachine context with the new variable
      StackMachine.putVarOnStack(ident.value)
      //Can abstract stackmachine.offset part of the function
      // can abstract Som
      findingVarOnStackIR(ident.value, instructions, Reassign)
    }
    case Print(expr) => {
      printToIR(expr, false)
    }
    case Println(expr) => {
      lib.setPrintLnFlag(true)
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
        val trueStats = for (s <- trueCase) yield statToIR(s)
        val setupStack = StackMachine.addFrame(
          table,
          None
        )
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
      cond ++ ListBuffer(
        Cmp(Dest, Immediate32(0), InstrSize.fullReg),
        JumpIfCond(s"else${labelCounter}", InstrCond.equal)
      ) ++ thenCase ++ ListBuffer(
        Jump(s"end${labelCounter}"),
        Label(s"else${labelCounter}")
      ) ++ elseCase ++ ListBuffer(
        Label(s"end${labelCounter}")
      )
    }
    case Return(expr) => {
      exprToIR(expr) ++ ListBuffer(
        ReturnInstr()
      )
    }
    case Skip() => {
      ListBuffer()
    }
    case Free(expr) => {
      val instructions = ListBuffer[Instruction]().empty
      var callInstr: ListBuffer[Instruction] = ListBuffer[Instruction]().empty
      expr.typeNode match {
        case ArrayTypeNode(_) => {
          lib.setFreeArrayFlag(true)
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
            case Some((offset, fpchange)) => {
              fpchange match {
                case 0 => {
                  // Use offset as start of stack
                  instructions ++= ListBuffer(
                    Mov(Arg0, G2, InstrSize.fullReg)
                  )
                
                }
              }
            }
            case None => {
              throw new RuntimeException(
                s"Variable ${ident} not found in stack"
              )
            }
          }
        }
      }

      instructions ++ callInstr
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
            case Some((offset, fpchange)) => {
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
            case None => {
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
  }

  def printToIR(expr: Expr, println: Boolean): Buffer[Instruction] = {

    val instr = expr.typeNode match {
      case StringTypeNode() => {
        lib.setPrintStringFlag(true)
        List(CallInstr("prints"))
      }
      case BoolTypeNode() => {
        lib.setPrintBoolFlag(true)
        List(CallInstr("printb"))
      }
      case CharTypeNode() => {
        lib.setPrintCharFlag(true)
        List(CallInstr("printc"))
      }
      case IntTypeNode() => {
        lib.setPrintIntFlag(true)
        List(CallInstr("printi"))
      }
      case atn @ ArrayTypeNode(CharTypeNode()) => {
        lib.setPrintCharFlag(true)
        lib.setPrintStringFlag(true)

        val instructions = ListBuffer[Instruction]().empty
          // Iterate through +8 x n times or some other way to printc each character maybe
          
          // Would base pointer be set
          // mov rdi, qword ptr [rbp - offset]
        instructions ++= ListBuffer(
          // mov r9, rdi
          Mov(Arg5, Arg0, InstrSize.fullReg)
        )
        for (i <- 0 to atn.length - 1) {
          instructions ++= ListBuffer(
            SubInstr(SP, Immediate32(MAX_REGSIZE), InstrSize.fullReg),
            // push r9
            PushRegisters(List(Arg5), InstrSize.fullReg),
            // mov rdi, qword ptr [r9 + 8]
            Mov(Arg0, RegisterPtr(Arg5, InstrSize.fullReg, i * 8), InstrSize.fullReg),
            CallInstr("printc"),
            // pop r9
            PopRegisters(List(Arg5), InstrSize.fullReg),
            AddInstr(SP, Immediate32(MAX_REGSIZE), InstrSize.fullReg),

          )

        }

        instructions
      }
      case _ => {
        // Must be Array (non char) || Error? || Pair
        lib.setPrintPtrFlag(true)
        List(CallInstr("printp"))
      }
    }

    exprToIR(expr) ++= ListBuffer(
      DecrementStackPointerNB(8),
      PushRegisters(List(Dest), InstrSize.fullReg),
      PopRegisters(List(Dest), InstrSize.fullReg),
      Mov(Dest, Dest, InstrSize.fullReg),
      Mov(Arg0, Dest, InstrSize.fullReg)
    ) ++= {
      if (!println) {
        instr ++ ListBuffer(IncrementStackPointerNB(8))
      } else {
        instr ++ ListBuffer(CallInstr("println"), IncrementStackPointerNB(8))
      }
    }
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
        JumpIfCond("_errOverflow", InstrCond.overflow),
        MovWithSignExtend(Dest, Dest, InstrSize.fullReg, InstrSize.halfReg)
      )
    }
    case Chr(int) => {
      lib.badChar.setFlag(true)
      exprToIR(int) ++ ListBuffer(
        TestInstr(Dest, Immediate32(-128), InstrSize.fullReg),
        CheckMoveNotEqual(Arg1, Dest, InstrSize.fullReg),
        JumpIfCond("_errBadChar", InstrCond.notEqual)
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
        Mov(G3, Dest, InstrSize.fullReg),
      )
      instructions
    }

    case f @ FstNode(e) => pairExprToIR(e, "Fst")
    case s @ SndNode(e) => pairExprToIR(e, "Snd")

    case NewPair(fst, snd) => {
      lib.setMallocFlag(true)
      lib.outOfMemory.setFlag(true)

      // int[] a = [1,2,3]
      // pair b = newpair(a, a)
      
      val firstIR : ListBuffer[Instruction] = exprToIR(FstNode(fst)(fst.pos))
      val secondIR : ListBuffer[Instruction] = exprToIR(SndNode(snd)(snd.pos))
      
      val instructions = firstIR ++ ListBuffer[Instruction](
        // mov qword ptr [r11], rax
        Mov(RegisterPtr(G2, InstrSize.fullReg, 0), Dest, InstrSize.fullReg)
      ) ++ secondIR ++ ListBuffer[Instruction](
        // mov qword ptr [r11 + 8], rax
        Mov(RegisterPtr(G2, InstrSize.fullReg, 8), Dest, InstrSize.fullReg)
      )

      instructions      
    }

    case ArrayElem(ident, eList) => { // x[0][1]
      lib.setArrLoad8Flag(true)
      lib.outOfBounds.setFlag(true)

      val instructions: ListBuffer[Instruction] = ListBuffer()

      StackMachine.offset(ident.value) match {
        case Some((offset, fpchange)) => {
          fpchange match {
            case 0 => {

              instructions ++= findVarInCurrFrame(offset)

              for (e <- eList) {
                instructions ++= ListBuffer(Mov(Arg5, Dest, InstrSize.fullReg))
                instructions ++= exprToIR(e)
                instructions ++= ListBuffer(
                  Mov(G1, Dest, InstrSize.halfReg),
                  CallInstr("arrLoad8"),
                  Mov(Dest, Arg5, InstrSize.fullReg)
                )
              }
            }
          }
        }
        case None => {
          throw new RuntimeException("Variable not found in stack")
        }
      }

      instructions

    }
    case ArrayLiter(entries) => {
      // Assume special calling convention for array stores - r11 is used to store the array pointer
      var offset: Int =
        0 // As we initially store the size of the array in the first 4 bytes
      val instructions: ListBuffer[Instruction] = ListBuffer(
        // mov rax, entry.size
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
        offset += MAX_REGSIZE
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

      val setupStack = StackMachine.addFrame(
        functionGenerator.getFunctionTable(ident.value),
        Some(functionNode.paramList)
      )

      val funcBody = generateFunctionIR(functionNode)
      functionGenerator.addFunction(
        functionNode.ident.value,
        functionNode,
        funcBody
      )
      val popFrame = StackMachine.popFrame()

      val body = functionGenerator.getFunctionBody(ident.value)

      functionGenerator.addFunction(ident.value, functionNode, body)

      instructions ++= handleParams ++ setupStack ++ ListBuffer(
        CallInstr(ident.value)
      ) ++ popFrame ++ incrementStackInstr

    }
  }

  def binOpSetup(expr1: Expr, expr2: Expr): ListBuffer[Instruction] = {
    val expr1IR = exprToIR(expr1)
    val expr2IR = exprToIR(expr2)

    val leftPrecedence = expr1IR ++ ListBuffer(
      DecrementStackPointerNB(2),
      PushRegisters(List(Dest), InstrSize.fullReg)
    ) ++ expr2IR ++ ListBuffer(
      Mov(G2, Dest, InstrSize.fullReg),
      PopRegisters(List(G1), InstrSize.fullReg),
      IncrementStackPointerNB(2)
    )

    val rightPrecedence = expr2IR ++ ListBuffer(
      DecrementStackPointerNB(2),
      PushRegisters(List(Dest), InstrSize.fullReg)
    ) ++ expr1IR ++ ListBuffer(
      Mov(G2, Dest, InstrSize.fullReg),
      PopRegisters(List(G1), InstrSize.fullReg),
      IncrementStackPointerNB(2)
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
        generalOp(operation, s"_errOverflow", InstrCond.overflow)
      case ArithmOperations.sub =>
        generalOp(operation, s"_errOverflow", InstrCond.overflow)
      case ArithmOperations.mul =>
        generalOp(operation, s"_errOverflow", InstrCond.overflow)
      case ArithmOperations.div | ArithmOperations.mod => {
        lib.divideByZero.setFlag(true)
        setup ++= ListBuffer(
          Cmp(
            G2,
            Immediate32(0),
            InstrSize.fullReg
          ), // Check for divide by zero
          JumpIfCond(s"_errDivByZero", InstrCond.equal),
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
    functionGenerator.reset()
    labelCounter = 0
  }

}

case class VariableScope(scope: Int, name: String)
