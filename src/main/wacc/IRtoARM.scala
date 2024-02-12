package wacc


object Arm32CodeGenerator {
    def transInstr(instruction: Instruction) : String = instruction match {
        case Mov(dest, operand, cond) => s"mov ${translateOperand(dest)}, ${translateOperand(operand)}"
        case Add(dest, src, operand, cond) => s"add ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Sub(dest, src, operand, cond) => s"sub ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Mul(dest, src, operand, cond) => s"mul ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Div(dest, src, operand, cond) => s"div ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case And(dest, src, operand, cond) => s"and ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Eor(dest, src, operand, cond) => s"eor ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Orr(dest, src, operand, cond) => s"orr ${translateOperand(dest)}, ${translateOperand(src)}, ${translateOperand(operand)}"
        case Cmp(src, operand, cond) => s"cmp ${translateOperand(src)}, ${translateOperand(operand)}"
        case Ldr(dest, src, cond) => s"ldr ${translateOperand(dest)}, ${translateOperand(src)}"
        case Push(src) => s"push {${translateOperand(src)}}"
        case Pop(dest) => s"pop {${translateOperand(dest)}}"
        case PushRegisters(registers) => s"push {${registers.map(translateOperand).mkString(", ")}}"
        case PopRegisters(registers) => s"pop {${registers.map(translateOperand).mkString(", ")}}"
        case LoadRegisterImmediate(register, value) => s"ldr ${translateOperand(register)}, =${value}"
        case BranchLink(label) => s"bl ${label}"
        case BitClear(destination, source, mask) => s"bic ${translateOperand(destination)}, ${translateOperand(source)}, ${mask}"
        case Directive(name) => s".$name"
        case Label(name) => s"$name:"

    }

    def translateOperand(operand: Operand): String = operand match {
        case Register(name) => s"${name}"
        case Immediate(value) => s"=${value}"
    }
}