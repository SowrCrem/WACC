package wacc

object X86RegisterMapper {

    val MAX_REG_NUM = 14

    val registerMap32Bit: Map[Register, String] = Map(
        REG(0) -> "eax", // Return register
        REG(1) -> "ebx", // Callee saved
        REG(2) -> "ecx", // Argument 4
        REG(3) -> "edx", // Argument 3
        REG(4) -> "esi", // Argument 2
        REG(5) -> "edi", // Argument 1
        REG(6) -> "ebp", // Callee saved
        REG(7) -> "r8",  // Argument 5
        REG(8) -> "r9",  // Argument 6
        REG(9) -> "r10",// Caller Saved
        REG(10) -> "r11",// Caller Saved
        REG(11) -> "r12",// Callee Saved
        REG(12) -> "r13",// Callee Saved
        REG(13) -> "r14",// Callee Saved
        REG(14) -> "r15" // Callee Saved
    )

    def transInstr(instruction: Instruction) : List[String] = instruction match {
        case Mov(dest, operand) => List(s"mov ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(operand)}")
        case Add(dest, src, operand) => List(s"add ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Sub(dest, src, operand) => List(s"sub ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Mul(dest, src, operand) => List(s"mul ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Div(dest, src, operand) => List(s"div ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case And(dest, src, operand) => List(s"and ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Eor(dest, src, operand) => List(s"eor ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Orr(dest, src, operand) => List(s"orr ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Cmp(src, operand) => List(s"cmp ${translateOperand_Intel_X86(src)}, ${translateOperand_Intel_X86(operand)}")
        case Lea(dest, src) => List(s"lea ${translateOperand_Intel_X86(dest)}, ${translateOperand_Intel_X86(src)}")
        case Push(src) => List(s"push ${translateOperand_Intel_X86(src)}")
        case Pop(dest) => List(s"pop {${translateOperand_Intel_X86(dest)}}")
        case PushRegisters(registers) => registers.foreach(register => s"push ${translateOperand_Intel_X86(register)}")
        case PopRegisters(registers) => registers.foreach(register => s"pop ${translateOperand_Intel_X86(register)}")
        case Directive(name) => List(s".$name")
        case Label(name) => List(s"$name:")
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
            if (rNum > MAX_REG_NUM) {
                /**
                  * TODO: If rNum is invalid, instead of throwing an error,
                  * we should implement a mechanism to save registers on the stack 
                  * or remove redundant register values
                **/
                throw new IllegalArgumentException(s"Unknown register: $rNum")
            }
        }

        case FP => "ebp"
        case SP => "esp"        
        /**
          * TODO: Check the immediate case is not erroneous
          * and doesn't allow invalid immediates to appear 
          * in the assembly code E.g. integer overflowing immediates
          * (although this may already have been handled in the parser/lexer)
        **/

        case Immediate(value) => s"#${value}" 
    }
}