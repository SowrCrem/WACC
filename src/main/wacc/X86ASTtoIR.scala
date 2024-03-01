package wacc

import scala.collection.mutable._
import InstrCond._
import ArithmOperations._
import Constants._
import parsley.internal.machine.instructions.Instr

object X86IRGenerator {

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
        val escapedLiteral = literal
          .replace("\u0000", "\\0")
          .replace("\b", "\\b")
          .replace("\t", "\\t")
          .replace("\n", "\\n")
          .replace("\f", "\\f")
          .replace("\r", "\\r")
          .replace("\"", "\\\"")
          .replace("\'", "\\\'")
          .replace("\\", "\\\\")
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
        if (table.dictionary.size > 0) {
          addFrame ++ stats.flatten ++ StackMachine.popFrame()
        } else {
          stats.flatten
        }
      }
      ListBuffer() ++ body
    }

    case AsgnEq(lhs, rhs) => {
      lhs match {
        case Ident(ident) => {
          val instructions = exprToIR(rhs)
          StackMachine.offset(ident) match {
            case Some((offset, fpchange)) => {
              fpchange match {
                case 0 => {
                  instructions += Mov(FPOffset(offset), Dest, InstrSize.fullReg)
                }
                case _ => {
                  val setup = ListBuffer(
                    AddInstr(SP, Immediate32(fpchange + 8), InstrSize.fullReg),
                    PopRegisters(List(FP), InstrSize.fullReg)
                  )
                  setup ++ instructions ++ ListBuffer(
                    Mov(FPOffset(offset), Dest, InstrSize.fullReg)
                  ) ++ ListBuffer(
                    PushRegisters(List(FP), InstrSize.fullReg),
                    SubInstr(SP, Immediate32(fpchange + 8), InstrSize.fullReg),
                    Mov(FP, SP, InstrSize.fullReg)
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
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // TODO: need to have cases for pairs and array elements later
      }
    }
    case IdentAsgn(typeNode, ident, expr) => {

      // Dynamically determine the size of the variable from the typeNode
      val varSize = typeNode.size / 8

      // // Step 1: Allocate space on the stack based on the variable size
      val instructions = ListBuffer[Instruction]().empty

      // Step 2: Initialize the variable with the given expression
      val arrInstructions = typeNode match {
        case arr @ ArrayTypeNode(elem) => {
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
              List(
                Mov(
                  Arg0,
                  Immediate32((arrSize) * MAX_REGSIZE + HALF_REGSIZE),
                  InstrSize.halfReg
                ),
                CallInstr("malloc"),
                Mov(G2, Dest, InstrSize.fullReg),
                AddInstr(G2, Immediate32(HALF_REGSIZE), InstrSize.fullReg)
              )

            }
            case _ => List()
          }

        }
        case _ => List()
      }

      instructions ++= exprToIR(expr)

      // Step 3: Update StackMachine context with the new variable
      StackMachine.putVarOnStack(ident.value)

      StackMachine.offset(ident.value) match {
        case Some((offset, fpchange)) => {
          // instructions += Mov(FPOffset(offset), Dest, InstrSize.fullReg)
          fpchange match {
            case 0 => {
              instructions.prependAll(arrInstructions)
              instructions += Mov(FPOffset(offset), Dest, InstrSize.fullReg)
            }
            case _ => {
              val setup = ListBuffer(
                AddInstr(SP, Immediate32(offset), InstrSize.fullReg),
                PopRegisters(List(FP), InstrSize.fullReg)
              )
              setup ++ arrInstructions ++ instructions ++ ListBuffer(
                PushRegisters(List(FP), InstrSize.fullReg),
                SubInstr(SP, Immediate32(offset), InstrSize.fullReg),
                Mov(FP, SP, InstrSize.fullReg)
              )
            }
          }
        }
        case None => {
          throw new RuntimeException(
            "Variable not found in stack: Identifier Assignment"
          )
        }
      }

      instructions
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
        val loopStats = for (s <- stats) yield statToIR(s)
        if (table.dictionary.size > 0) {
          StackMachine.addFrame(
            table,
            None
          ) ++ loopStats.flatten ++ StackMachine.popFrame()
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
        if (table.dictionary.size > 0) {
          StackMachine.addFrame(
            table,
            None
          ) ++ trueStats.flatten ++ StackMachine.popFrame()
        } else {
          trueStats.flatten
        }
      }
      val elseCase = {
        val table = ifNode.symbolTableFalse
        val falseStats = for (s <- falseCase) yield statToIR(s)
        if (table.dictionary.size > 0) {
          StackMachine.addFrame(
            table,
            None
          ) ++ falseStats.flatten ++ StackMachine.popFrame()
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

    // TODO: Implement
    case Call(ident, args) => ???
    case Free(expr)        => ???
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

                  instructions ++ stackSetup ++ readLogic ++ reverseStackSetup
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

    val func = expr.typeNode match {
      case StringTypeNode() => {
        lib.setPrintStringFlag(true)
        CallInstr("prints")
      }
      case BoolTypeNode() => {
        lib.setPrintBoolFlag(true)
        CallInstr("printb")
      }
      case CharTypeNode() => {
        lib.setPrintCharFlag(true)
        CallInstr("printc")
      }
      case IntTypeNode() => {
        lib.setPrintIntFlag(true)
        CallInstr("printi")
      }
      case _ => {
        // Must be Array || Error? || Pair
        // printf("Type printing: :" + expr.typeNode.toString())
        if (expr.typeNode == null) {
          printf("Type printing: :" + expr.toString())
        }
        lib.setPrintPtrFlag(true)
        CallInstr("printp")
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
        ListBuffer(func, IncrementStackPointerNB(8))
      } else {
        ListBuffer(func, CallInstr("println"), IncrementStackPointerNB(8))
      }
    }
  }

  // --------------- Expression IR Generation -------------------------------------//

  def exprToIR(expr: Position): Buffer[Instruction] = expr match {

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
    case ArrayElem(ident, eList) => {
      lib.setArrLoad8Flag(true)
      lib.outOfBounds.setFlag(true)
      if (eList.size == 1) {
        val instrs = exprToIR(eList.head)
        instrs ++= ListBuffer(
          Mov(G1, Dest, InstrSize.halfReg)
        )


        StackMachine.offset(ident.value) match {
        
          case Some((offset, fpchange)) => {
            fpchange match {
              case 0 => {
                instrs ++= ListBuffer(
                  Mov(Dest, FPOffset(offset), InstrSize.fullReg),
                  Mov(Arg5, Dest, InstrSize.fullReg),
                  CallInstr("arrLoad8"), Mov(Dest, Arg5, InstrSize.fullReg)
                )
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
                instrs ++= setup ++ ListBuffer(
                  Mov(Dest, FPOffset(offset), InstrSize.fullReg),
                  Mov(Arg5, Dest, InstrSize.fullReg),
                  PushRegisters(List(FP), InstrSize.fullReg),
                  SubInstr(
                    SP,
                    Immediate32(fpchange + MAX_REGSIZE),
                    InstrSize.fullReg
                  ),
                  Mov(FP, SP, InstrSize.fullReg)
                )
              }


              instrs ++ ListBuffer(CallInstr("arrLoad8"), Mov(Dest, Arg5, InstrSize.fullReg))

            }
          }
          case None => {
            throw new RuntimeException(
              "Variable not found in stack: Array Element"
            )
          }
        }
      } else {
        ListBuffer()
      }
    }
    case ArrayLiter(entries) => {
      // Assume special calling convention for array stores - r11 is used to store the array pointer
      var offset: Int =
        0 // As we initially store the size of the array in the first 4 bytes
      val instructions: ListBuffer[Instruction] = ListBuffer(
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
          RegisterPtr(G2, InstrSize.halfReg, offset),
          Dest,
          InstrSize.halfReg
        )
        offset += MAX_REGSIZE
      }
      instructions += Mov(Dest, G2, InstrSize.fullReg)
      instructions
    }
    case Ident(ident) => {
      StackMachine.offset(ident) match {
        case Some((offset, fpchange)) => {
          fpchange match {
            case 0 => {
              ListBuffer(Mov(Dest, FPOffset(offset), InstrSize.fullReg))
            }
            case _ => {
              val setup = ListBuffer(
                AddInstr(SP, Immediate32(fpchange), InstrSize.fullReg),
                PopRegisters(List(FP), InstrSize.fullReg)
              )
              setup ++ ListBuffer(
                Mov(Dest, FPOffset(offset), InstrSize.fullReg)
              ) ++ ListBuffer(
                PushRegisters(List(FP), InstrSize.fullReg),
                SubInstr(SP, Immediate32(fpchange), InstrSize.fullReg),
                Mov(FP, SP, InstrSize.fullReg)
              )

            }
          }
        }
        case None => {
          throw new RuntimeException("Variable not found in stack")
        }
      }
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

  def binOpSetup(expr1: Expr, expr2: Expr): Buffer[Instruction] = {
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

  def intBinOp(
      expr1: Expr,
      expr2: Expr,
      operation: ArithmOperations
  ): Buffer[Instruction] = {
    val setup = binOpSetup(expr1, expr2)
    setup ++= ListBuffer(
      Mov(Dest, G1, InstrSize.halfReg)
    )

    def generalOp(
        operation: ArithmOperations,
        jumpLabel: String,
        cond: InstrCond
    ) = {
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

  def compBinOp(expr1: Expr, expr2: Expr, comparison: InstrCond) = {
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
