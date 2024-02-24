package wacc

import scala.collection.mutable.Buffer
import java.io.File
import java.io.PrintWriter

object X86CodeGenerator {

  def generate(program: Program): String = toAssemblyIntel(X86IRGenerator.generateIR(program))
    // generateIR should return instructions, but predefined assembly should be added here

  def toAssemblyIntel(instrs: Buffer[Instruction]): String = instrs.map(transInstr).flatten.mkString("\n")
  
  def transInstr(instruction: Instruction) : List[String] = instruction match {
    case Mov(dest, operand) => List(s"  mov ${dest.toString}, ${operand.toString}")
    case AddInstr(dest, src, operand) => List(s"  add ${dest.toString}, ${src.toString}, ${operand.toString}")
    case SubInstr(dest, src, operand) => List(s"  sub ${dest.toString}, ${src.toString}")
    case MulInstr(dest, src, operand) => List(s"  mul ${dest.toString}, ${src.toString}, ${operand.toString}")
    case DivInstr(dest, src, operand) => List(s"  div ${dest.toString}, ${src.toString}, ${operand.toString}")
    case AndInstr(dest, src) => List(s"  and ${dest.toString}, ${src.toString}")
    case Eor(dest, src, operand) => List(s"  eor ${dest.toString}, ${src.toString}, ${operand.toString}")
    case Orr(dest, src, operand) => List(s"  orr ${dest.toString}, ${src.toString}, ${operand.toString}")
    case Cmp(src, operand) => List(s"  cmp ${src.toString}, ${operand.toString}")
    case Lea(dest, src) => List(s"  lea ${dest.toString}, ${src.toString}")
    case PushRegisters(registers) => for (register <- registers) yield s"  push ${register.toString}"
    case PopRegisters(registers) => for (register <- registers) yield s"  pop ${register.toString}"
    case Directive(name) => List(s".$name")
    case Label(name) => List(s"$name:") // Procedure label
    case CallLabel(name) => List(s"  call ${name}")
    case CallPLT(name) => List(s"  call ${name}@plt")
    case ReturnInstr() => List(s"  ret\n")
    case DecrementStackPointerNB(value) => List(s"  sub rsp, ${8*value}")
    case IncrementStackPointerNB(value) => List(s"  add rsp, ${8*value}")
    case DecrementStackPointer4B() => List(s"  sub rsp, 4")
    case IncrementStackPointer4B() => List(s"  add rsp, 4")
    case DecrementStackPointer8B() => List(s"  sub rsp, 8")
    case IncrementStackPointer8B() => List(s"  add rsp, 8")
    case LoadEffectiveAddress(dest, src) => List(s"  lea ${dest.toString}, ${src.toString}")
    case x => throw new IllegalArgumentException("Invalid instruction type: " + x)
  }
}
