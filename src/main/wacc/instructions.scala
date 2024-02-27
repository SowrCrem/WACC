package wacc

import parsley.internal.machine.instructions.Instr

sealed trait Instruction

object InstrSize extends Enumeration {

  type InstrSize = Value

  val fullReg = Value(64)
  val halfReg = Value(32)
  val quarterReg = Value(16)
  val eigthReg = Value(8)
}

object InstrCond extends Enumeration {
  type InstrCond = Value
  val equal = Value("e")
  val notEqual = Value("ne")
  val lessThan = Value("l")
  val lessThanEqual = Value("le")
  val greaterThan = Value("g")
  val greaterThanEqual = Value("ge")
}


import InstrCond._
import InstrSize._

case class AddInstr(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction
case class SubInstr(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction

case class MulInstr(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction
case class DivInstr(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction

case class AndInstr(dest: Operand, src: Operand, instrSize: InstrSize)
    extends Instruction
case class Eor(dest: Operand, src: Operand, operand: Operand, instrSize: InstrSize)
    extends Instruction
case class Orr(dest: Operand, src: Operand, operand: Operand, instrSize: InstrSize)
    extends Instruction

case class Mov(dest: Operand, operand: Operand, size: InstrSize) extends Instruction
case class MovWithSignExtend(dest: Operand, operand: Operand, size1: InstrSize, size2: InstrSize)
    extends Instruction
case class Cmp(src: Operand, operand: Operand, size: InstrSize) extends Instruction
case class Lea(dest: Operand, src: Operand, size: InstrSize) extends Instruction

case class PushRegisters(registers: List[Register], size: InstrSize) extends Instruction
case class PopRegisters(registers: List[Register], size: InstrSize) extends Instruction
case class Directive(val name: String) extends Instruction
case class Label(val name: String) extends Instruction
case class CallInstr(val name: String) extends Instruction
case class CallPLT(val name: String) extends Instruction
case class ReturnInstr() extends Instruction

case class Jump(val label: String) extends Instruction

case class JumpIfCond(val label: String, cond: InstrCond) extends Instruction

case class SetByteIfCond(val dest: Operand, cond: InstrCond, size: InstrSize) extends Instruction

case class IncrementStackPointer4B() extends Instruction
case class IncrementStackPointer8B() extends Instruction
case class IncrementStackPointerNB(val value: Int) extends Instruction
case class DecrementStackPointer4B() extends Instruction
case class DecrementStackPointer8B() extends Instruction
case class DecrementStackPointerNB(val value: Int) extends Instruction
case class LoadEffectiveAddress(val dest: Operand, val src: Operand, size: InstrSize)
    extends Instruction

sealed trait Address
case class StackAddress(val index: Int) extends Address {
  // def toIntelString: String = s"qword [rbp - $offset]"
  // TODO (Maybe store as index rather than offset)
}

sealed trait Operand {
  def toIntelString(size: InstrSize): String
}

sealed abstract trait Register extends Operand with Address {
  override def toIntelString(size: InstrSize): String
}

sealed trait SpecialRegister extends Register

case object IP extends SpecialRegister {
  def toIntelString(size: InstrSize): String = "rip"
} // Instruction pointer

case object FP extends SpecialRegister {
  def toIntelString(size: InstrSize): String = "rbp"
} // Frame pointer
case object SP extends SpecialRegister {
  def toIntelString(size: InstrSize): String = "rsp"
} // Stack pointer

// Destination register
case object Dest extends SpecialRegister {
  def toIntelString(size: InstrSize): String = { // rax
    size match {
      case InstrSize.fullReg    => "rax"
      case InstrSize.halfReg    => "eax"
      case InstrSize.quarterReg => "ax"
      case InstrSize.eigthReg   => "al"
    }
  }

}

sealed trait GeneralRegister extends Register
case object G0 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = { // rbx
    size match {
      case InstrSize.fullReg    => "rbx"
      case InstrSize.halfReg    => "ebx"
      case InstrSize.quarterReg => "bx"
      case InstrSize.eigthReg   => "bl"
    }
  }
}
case object G1 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r10"
    case InstrSize.halfReg    => "r10d"
    case InstrSize.quarterReg => "r10w"
    case InstrSize.eigthReg   => "r10b"
  }
}
case object G2 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r11"
    case InstrSize.halfReg    => "r11d"
    case InstrSize.quarterReg => "r11w"
    case InstrSize.eigthReg   => "r11b"
  }
}
case object G3 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r12"
    case InstrSize.halfReg    => "r12d"
    case InstrSize.quarterReg => "r12w"
    case InstrSize.eigthReg   => "r12b"
  }
}
case object G4 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r13"
    case InstrSize.halfReg    => "r13d"
    case InstrSize.quarterReg => "r13w"
    case InstrSize.eigthReg   => "r13b"
  }
}
case object G5 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r14"
    case InstrSize.halfReg    => "r14d"
    case InstrSize.quarterReg => "r14w"
    case InstrSize.eigthReg   => "r14b"
  }
}
case object G6 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r15"
    case InstrSize.halfReg    => "r15d"
    case InstrSize.quarterReg => "r15w"
    case InstrSize.eigthReg   => "r15b"
  }
}
sealed trait ArgRegister extends Register
case object Arg0 extends ArgRegister {
  def toIntelString(size: InstrSize): String = { //rdi
    size match {
      case InstrSize.fullReg    => "rdi"
      case InstrSize.halfReg    => "edi"
      case InstrSize.quarterReg => "di"
      case InstrSize.eigthReg   => "dil"
    }
  }
}
case object Arg1 extends ArgRegister {
  def toIntelString(size: InstrSize): String = { //rsi
    size match {
      case InstrSize.fullReg    => "rsi"
      case InstrSize.halfReg    => "esi"
      case InstrSize.quarterReg => "si"
      case InstrSize.eigthReg   => "sil"
    }
  }
}
case object Arg2 extends ArgRegister {
  def toIntelString(size: InstrSize): String = { //rdx
    size match {
      case InstrSize.fullReg    => "rdx"
      case InstrSize.halfReg    => "edx"
      case InstrSize.quarterReg => "dx"
      case InstrSize.eigthReg   => "dl"
    }
  }
}
case object Arg3 extends ArgRegister {
  def toIntelString(size: InstrSize): String = { //rcx
    size match {
      case InstrSize.fullReg    => "rcx"
      case InstrSize.halfReg    => "ecx"
      case InstrSize.quarterReg => "cx"
      case InstrSize.eigthReg   => "cl"
    }
  }
}
case object Arg4 extends ArgRegister {
  def toIntelString(size: InstrSize): String = "r8"
}
case object Arg5 extends ArgRegister {
  def toIntelString(size: InstrSize): String = "r9"
}

case class FPOffset(val offset: Int) extends Operand {
  def toIntelString(size: InstrSize): String = s"qword ptr [rbp - $offset]"
}

case class LabelAddress(val label: String) extends Operand {
  def toIntelString(size: InstrSize): String = label
}

case class RegisterLabelAddress(val register: Register, val label: LabelAddress)
    extends Operand {
  def toIntelString(size: InstrSize): String =
    s"[${register.toIntelString(size)} + .${label.toIntelString(size)}]"
}

case class Immediate32(val value: Int) extends Operand {
  def verify: Boolean = value >= Int.MinValue && value <= Int.MaxValue
  def toIntelString(size: InstrSize): String = {
    if (this.verify)
      value.toString
    else
      throw new IllegalArgumentException("Immediate value out of range")
  }
}
case object EMPTY extends Operand {
  def toIntelString(size: InstrSize): String = ""
}

case class OffsetRegLabel(reg: Register, label: LabelAddress) extends Operand with Register {
  def toIntelString(size: InstrSize): String = s"[${reg.toIntelString(size)} + .${label.toIntelString(size)}]"
}

case class OffsetRegPos(reg: Register, offset: Int) extends Operand with Register {
  def toIntelString(size: InstrSize): String = s"[${reg.toIntelString(size)} + $offset]"
}

case class OffsetRegNeg(reg: Register, offset: Int) extends Operand with Register {
  def toIntelString(size: InstrSize): String = s"${reg.toIntelString(size)} - $offset"
}

case class Reg64(reg: Register) extends Operand {
  def toIntelString(size: InstrSize): String = s"qword ptr [${reg.toIntelString(size)}]"
}

case class Reg32(reg: Register, offset: Int) extends Operand {
  def toIntelString(size: InstrSize): String = s"dword ptr [${reg.toIntelString(InstrSize.fullReg)} + $offset]"
}
