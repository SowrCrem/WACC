package wacc

sealed trait Instruction

case class AddInstr(dest: Operand, src: Operand, operand: Operand) extends Instruction
case class SubInstr(dest: Operand, src: Operand, operand: Operand) extends Instruction
case class MulInstr(dest: Operand, src: Operand, operand: Operand) extends Instruction
case class DivInstr(dest: Operand, src: Operand, operand: Operand) extends Instruction

case class AndInstr(dest: Operand, src: Operand) extends Instruction
case class Eor(dest: Operand, src: Operand, operand: Operand) extends Instruction
case class Orr(dest: Operand, src: Operand, operand: Operand) extends Instruction

case class Mov(dest: Operand, operand: Operand) extends Instruction
case class Cmp(src: Operand, operand: Operand) extends Instruction
case class Lea(dest: Operand, src: Operand) extends Instruction

case class PushRegisters(registers: List[Register]) extends Instruction
case class PopRegisters(registers: List[Register]) extends Instruction
case class Directive(val name: String) extends Instruction
case class Label(val name: String) extends Instruction
case class CallLabel(val name: String) extends Instruction
case class CallPLT(val name: String) extends Instruction
case class ReturnInstr() extends Instruction

case class IncrementStackPointer4B() extends Instruction
case class IncrementStackPointer8B() extends Instruction
case class IncrementStackPointerNB(val value: Int) extends Instruction
case class DecrementStackPointer4B() extends Instruction
case class DecrementStackPointer8B() extends Instruction
case class DecrementStackPointerNB(val value: Int) extends Instruction
case class LoadEffectiveAddress(val dest: Operand, val src: Operand) extends Instruction

sealed trait Address
case class StackAddress(val index: Int) extends Address {
  // def toString: String = s"qword [rbp - $offset]"
  // TODO (Maybe store as index rather than offset)
}

sealed trait Operand

sealed trait Register extends Operand with Address

sealed trait SpecialRegister extends Register
case object IP extends SpecialRegister {
  override def toString: String = "rip" // Instruction pointer
}
case object FP extends SpecialRegister {
  override def toString: String = "rbp" // Frame pointer
}
case object SP extends SpecialRegister 
{
  override def toString: String = "rsp" // Stack pointer
}
case object RAX extends SpecialRegister {
  override def toString: String = "rax" // Return value
}

sealed trait GeneralRegister extends Register
case object G0 extends GeneralRegister {
  override def toString: String = "rbx"
}
case object G1 extends GeneralRegister {
  override def toString: String = "r10"
}
case object G2 extends GeneralRegister {
  override def toString: String = "r11"
}
case object G3 extends GeneralRegister {
  override def toString: String = "r12"
}
case object G4 extends GeneralRegister {
  override def toString: String = "r13"
}
case object G5 extends GeneralRegister {
  override def toString: String = "r14"
}
case object G6 extends GeneralRegister {
  override def toString: String = "r15"
}

sealed trait ArgRegister extends Register
case object Arg0 extends ArgRegister {
  override def toString: String = "rdi"
}
case object Arg1 extends ArgRegister {
  override def toString: String = "rsi"
}
case object Arg2 extends ArgRegister {
  override def toString: String = "rdx"
}
case object Arg3 extends ArgRegister {
  override def toString: String = "rcx"
}
case object Arg4 extends ArgRegister {
  override def toString: String = "r8"
}
case object Arg5 extends ArgRegister {
  override def toString: String = "r9"
}

// TODO: Abstract the registers and their modes
case object ESI extends Register { override def toString: String = "esi"}
case object AL extends Register { override def toString: String = "al"}

// TODO: why is the toInt needed?
sealed trait Size {
  // def toInt: Int = this match {
  //   case Byte => 1
  //   case Word => 2
  //   case DWord => 4
  //   case QWord => 8
  // }
}

// 8-bit value
case object Byte extends Size {
  override def toString: String = "byte"
}
// 16-bit value
case object Word extends Size {
  override def toString: String = "word"
}
// 32-bit value
case object DWord extends Size {
  override def toString: String = "dword"
}
// 64-bit value 
case object QWord extends Size {
  override def toString: String = "qword"
}

// Offset of two operands (op1 - op2)
case class Offset(operand1: Operand, operand2: Operand) extends Operand {
  override def toString: String = s"${operand1.toString} - ${operand2.toString}"
}

// Positive offset of two operands (op1 + op2)
case class PosOffset(operand1: Operand, operand2: Operand) extends Operand {
  override def toString: String = s"${operand1.toString} + ${operand2.toString}"
}

// Dereference the memory at the operand
case class MemAt(val operand: Operand) extends Operand {
  override def toString: String = s"[${operand.toString}]"
}

case class PtrSizeOffset(size: Size, operand: Operand) extends Operand {
  override def toString: String = 
    s"${size.toString} ptr [${operand.toString}]"
}

// TODO: Refactor to the abstracted layout
case class FPOffset(val offset: Int) extends Operand {
  override def toString: String = s"qword ptr [rbp - $offset]"
}

case class LabelAddress(val label: String) extends Operand {
  override def toString: String = label
}

case class RegisterLabelAddress(val register: Register, val label: LabelAddress) extends Operand {
  override def toString: String = s"[${register.toString} + .${label.toString}]"
}

case class Imm(val value: Int) extends Operand {
  def verify: Boolean = value >= Int.MinValue && value <= Int.MaxValue
  override def toString: String = {
    if (this.verify)
      value.toString
    else
      throw new IllegalArgumentException("Immediate value out of range")
  }
}

case object EMPTY extends Operand {
  override def toString: String = ""
}

case class OffsetReg(reg: Register, offset: Int) extends Operand with Register {
    def toIntelString: String = s"${reg.toString()} + $offset"
}

case class Reg64(reg: Register) extends Operand {
    def toIntelString: String = s"qword ptr [${reg.toString}]"
}

case class Reg32(reg: Register) extends Operand {
    def toIntelString: String = s"dword ptr [${reg.toString}]"
}

case class Reg16(reg: Register) extends Operand {
    def toIntelString: String = s"word ptr [${reg.toString}]"
}

case class Reg8(reg: Register) extends Operand {
    def toIntelString: String = s"byte ptr [${reg.toString}]"
}