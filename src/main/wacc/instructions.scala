package wacc

sealed trait Instruction

sealed trait Operand

sealed trait Register extends Operand

case object FP extends Register
case object SP extends Register
object PC extends Register
case class REG(n : Int) extends Register


case class Immediate(val value: Int) extends Operand

sealed trait Condition
case class AL() extends Condition


case class Add(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Sub(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Mul(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Div(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction

case class And(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Eor(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Orr(dest: Operand, src: Operand, operand: Operand, cond: Condition)
    extends Instruction
case class Mov(dest: Operand, operand: Operand, cond: Condition) extends Instruction
case class Cmp(src: Operand, operand: Operand, cond: Condition) extends Instruction

case class Ldr(dest: Register, src: Operand, cond: Condition)
    extends Instruction


case class Push(src: Operand) extends Instruction
case class Pop(dest: Operand) extends Instruction
case class PushRegisters(registers: List[Register]) extends Instruction
case class PopRegisters(registers: List[Register]) extends Instruction
case class LoadRegisterImmediate(register: Register, value: Int) extends Instruction // LDR equivalent
case class BranchLink(label: String) extends Instruction
case class BitClear(destination: Register, source: Register, mask: Int) extends Instruction // BIC

case class Directive(val name: String) extends Instruction
case class Label(val name: String) extends Instruction