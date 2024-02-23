package wacc

import scala.collection.mutable.Buffer
import java.io.File
import java.io.PrintWriter

object X86CodeGenerator {

  def generate(program: Program): String = makeAssemblyIntel(X86IRGenerator.generateIR(program))

  def makeAssemblyIntel(instrs: Buffer[Instruction]): String = instrs.map(transInstr).flatten.mkString("\n")
  
  def transInstr(instruction: Instruction) : List[String] = instruction match {
    case Mov(dest, operand) => List(s"  mov ${dest.toIntelString}, ${operand.toIntelString}")
    case AddInstr(dest, src, operand) => List(s"  add ${dest.toIntelString}, ${src.toIntelString}, ${operand.toIntelString}")
    case SubInstr(dest, src, operand) => List(s"  sub ${dest.toIntelString}, ${src.toIntelString}")
    case MulInstr(dest, src, operand) => List(s"  mul ${dest.toIntelString}, ${src.toIntelString}, ${operand.toIntelString}")
    case DivInstr(dest, src, operand) => List(s"  div ${dest.toIntelString}, ${src.toIntelString}, ${operand.toIntelString}")
    case AndInstr(dest, src) => List(s"  and ${dest.toIntelString}, ${src.toIntelString}")
    case Eor(dest, src, operand) => List(s"  eor ${dest.toIntelString}, ${src.toIntelString}, ${operand.toIntelString}")
    case Orr(dest, src, operand) => List(s"  orr ${dest.toIntelString}, ${src.toIntelString}, ${operand.toIntelString}")
    case Cmp(src, operand) => List(s"  cmp ${src.toIntelString}, ${operand.toIntelString}")
    case Lea(dest, src) => List(s"  lea ${dest.toIntelString}, ${src.toIntelString}")
    case PushRegisters(registers) => for (register <- registers) yield s"  push ${register.toIntelString}"
    case PopRegisters(registers) => for (register <- registers) yield s"  pop ${register.toIntelString}"
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
    case LoadEffectiveAddress(dest, src) => List(s"  lea ${dest.toIntelString}, ${src.toIntelString}")
    case x => throw new IllegalArgumentException("Invalid instruction type: " + x)
  }
}
