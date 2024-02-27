package wacc

import scala.collection.mutable.Buffer
import java.io.File
import java.io.PrintWriter



object X86CodeGenerator {

  def generate(program: Program): String = makeAssemblyIntel(X86IRGenerator.generateIR(program))

  def makeAssemblyIntel(instrs: Buffer[Instruction]): String = instrs.map(transInstr).flatten.mkString("\n")
  
  def transInstr(instruction: Instruction) : List[String] = instruction match {
    case Mov(dest, operand, instrSize) => List(s"  mov ${dest.toIntelString(instrSize)}, ${operand.toIntelString(instrSize)}")
    case MovWithSignExtend(dest, operand, size1, size2) => List(s"  movsx ${dest.toIntelString(size1)}, ${operand.toIntelString(size2)}")
    case AddInstr(dest, src, instrSize) => List(s"  add ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case SubInstr(dest, src, instrSize) => List(s"  sub ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case MulInstr(dest, src, instrSize) => List(s"  imul ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case DivInstr(dest, src, instrSize) => List(s"  idiv ${src.toIntelString(instrSize)}")
    case AndInstr(dest, src, instrSize) => List(s"  and ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case Eor(dest, src, operand, instrSize) => List(s"  eor ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case Orr(dest, src, operand, instrSize) => List(s"  orr ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case Cmp(src, operand, instrSize) => List(s"  cmp ${src.toIntelString(instrSize)}, ${operand.toIntelString(instrSize)}")
    case Lea(dest, src, instrSize) => List(s"  lea ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case PushRegisters(registers, instrSize) => for (register <- registers) yield s"  push ${register.toIntelString(instrSize)}"
    case PopRegisters(registers, instrSize) => for (register <- registers) yield s"  pop ${register.toIntelString(instrSize)}"
    case Directive(name) => List(s".$name")
    case Label(name) => List(s"$name:")
    case CallInstr(name) => List(s"  call _${name}")
    case CallPLT(name) => List(s"  call ${name}@plt")
    case ReturnInstr() => List(s"  ret\n")
    case DecrementStackPointerNB(value) => List(s"  sub rsp, ${8*value}")
    case IncrementStackPointerNB(value) => List(s"  add rsp, ${8*value}")
    case DecrementStackPointer4B() => List(s"  sub rsp, 4")
    case IncrementStackPointer4B() => List(s"  add rsp, 4")
    case DecrementStackPointer8B() => List(s"  sub rsp, 8")
    case IncrementStackPointer8B() => List(s"  add rsp, 8")
    case LoadEffectiveAddress(dest, src, instrSize) => List(s"  lea ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}")
    case JumpIfCond(label, cond) => List(s"  j${cond} $label")
    case Jump(label) => List(s"  jmp $label")
    case SetByteIfCond(dest, cond, size) => List(s"  set${cond} ${dest.toIntelString(size)}")
    case ConvertDoubleWordToQuadWord() => List(s"  cdq")
    case x => throw new IllegalArgumentException("Invalid instruction type: " + x)
  }
}
