package wacc

object X86RegisterMapper {

    val MAX_REG_NUM = 14

    val registerMap32Bit: Map[Int, String] = Map(
        0 -> "rax", // Return register
        1 -> "rbx", // Callee saved
        2 -> "rcx", // Argument 4
        3 -> "rdx", // Argument 3
        4 -> "rsi", // Argument 2
        5 -> "rdi", // Argument 1
        6 -> "rsp", // Stack pointer
        7 -> "rbp", // Callee saved
        8 -> "r8",  // Argument 5
        9 -> "r9",  // Argument 6
        10 -> "r10",// Caller Saved
        11 -> "r11",// Caller Saved
        12 -> "r12",// Callee Saved
        13 -> "r13",// Callee Saved
        14 -> "r14",// Callee Saved
        15 -> "r15" // Callee Saved
    )
    

    def transInstr(instruction: Instruction) : List[String] = instruction match {
        case Mov(dest, operand) => List(s"mov ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(operand)}")
        case AddInstr(dest, src, operand) => List(s"add ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case SubInstr(dest, src, operand) => List(s"sub ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case MulInstr(dest, src, operand) => List(s"mul ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case DivInstr(dest, src, operand) => List(s"div ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case AndInstr(dest, src) => List(s"and ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}")
        case Eor(dest, src, operand) => List(s"eor ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Orr(dest, src, operand) => List(s"orr ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Cmp(src, operand) => List(s"cmp ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Lea(dest, src) => List(s"lea ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}")
        case Push(src) => List(s"push ${translateOperand_Intel_X86(src)}")
        case Pop(dest) => List(s"pop {${translateOperand_Intel_X86(dest)}}")
        case PushRegisters(registers) => for (register <- registers) yield s"push ${translateOperand_Intel_X86(register)}"
        case PopRegisters(registers) => for (register <- registers) yield s"pop ${translateOperand_Intel_X86(register)}"
        case Directive(name) => List(s".$name")
        case Label(name) => List(s"$name:")
        case CallInstr(name) => List(s"call _$name")
        case ReturnInstr() => List(s"ret\n")
        case _ => throw new IllegalArgumentException("Invalid instruction")
    }

    /** 
        * Translates an operand in the intermediate representation to an operand in ARM32 assembly
        * Input: Any operand in the intermediate representation (registers or immediate values)
        * Output: The corresponding operand in ARM32 assembly as a String
    **/
    def translateOperand_Intel_X86(operand: Operand): String = operand match {
        /**
          * TODO: If rNum is invalid, instead of throwing an error,
          * we should implement a mechanism to save registers on the stack 
          * or remove redundant register values
        **/
        case REG(rNum) => {
            registerMap32Bit.getOrElse(
                rNum, 
                throw new IllegalArgumentException(s"Unknown register: $rNum")
                )
        }

        case FP => "rbp"
        case SP => "rsp"        
        /**
          * TODO: Check the immediate case is not erroneous
          * and doesn't allow invalid immediates to appear 
          * in the assembly code E.g. integer overflowing immediates
          * (although this may already have been handled in the parser/lexer)
        **/

        case Immediate(value) => s"#${value}" 
        case _ => throw new IllegalArgumentException("Invalid operand: " + operand)
    }
}