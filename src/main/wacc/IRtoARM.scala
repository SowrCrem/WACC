// package wacc



// object Arm32CodeGenerator {
//     def transInstrARM32(instruction: Instruction) : String = instruction match {
//         case Mov(dest, operand, cond) => s"mov ${translateOperandARM32(dest)}, ${translateOperandARM32(operand)}"
//         case Add(dest, src, operand, cond) => s"add ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Sub(dest, src, operand, cond) => s"sub ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Mul(dest, src, operand, cond) => s"mul ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Div(dest, src, operand, cond) => s"div ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case And(dest, src, operand, cond) => s"and ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Eor(dest, src, operand, cond) => s"eor ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Orr(dest, src, operand, cond) => s"orr ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Cmp(src, operand, cond) => s"cmp ${translateOperandARM32(src)}, ${translateOperandARM32(operand)}"
//         case Ldr(dest, src, cond) => s"ldr ${translateOperandARM32(dest)}, ${translateOperandARM32(src)}"
//         case Push(src) => s"push {${translateOperandARM32(src)}}"
//         case Pop(dest) => s"pop {${translateOperandARM32(dest)}}"
//         case PushRegisters(registers) => s"push {${registers.map(translateOperandARM32).mkString(", ")}}"
//         case PopRegisters(registers) => s"pop {${registers.map(translateOperandARM32).mkString(", ")}}"
//         case LoadRegisterImmediate(register, value) => s"ldr ${translateOperandARM32(register)}, =${value}"
//         case BranchLink(label) => s"bl ${label}"
//         case BitClear(destination, source, mask) => s"bic ${translateOperandARM32(destination)}, ${translateOperandARM32(source)}, ${mask}"
//         case Directive(name) => s".$name"
//         case Label(name) => s"$name:"
//     }

//     def translateOperandARM32(operand: Operand): String = operand match {
//         case Register(name) => s"${name}"
//         case Immediate(value) => s"=${value}"
//     }
// }