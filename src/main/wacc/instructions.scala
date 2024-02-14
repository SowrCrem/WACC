package wacc

sealed trait Instruction

sealed trait Operand

sealed trait Register extends Operand

case object FP extends Register // Frame pointer
case object SP extends Register // Stack pointer
case object Dest extends Register // Destination register
case class REG(n : Int) extends Register // All other registers

case class Immediate(val value: Int) extends Operand

sealed trait Condition
case class AL() extends Condition


case class AddInstr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction
case class SubInstr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction
case class MulInstr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction
case class DivInstr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction

case class AndInstr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction
case class Eor(dest: Operand, src: Operand, operand: Operand)
    extends Instruction
case class Orr(dest: Operand, src: Operand, operand: Operand)
    extends Instruction

case class Mov(dest: Operand, operand: Operand) extends Instruction
case class Cmp(src: Operand, operand: Operand) extends Instruction
case class Lea(dest: Operand, src: Operand) extends Instruction

case class Push(src: Operand) extends Instruction
case class Pop(dest: Operand) extends Instruction
case class PushRegisters(registers: List[Register]) extends Instruction
case class PopRegisters(registers: List[Register]) extends Instruction
case class Directive(val name: String) extends Instruction
case class Label(val name: String) extends Instruction