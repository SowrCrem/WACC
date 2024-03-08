package wacc

import parsley.internal.machine.instructions.Instr

/** Constants used in the stack machine
  * REGSIZEs defined in bytes
  */
object Constants {
  val MAX_REGSIZE: Int = 8
  val HALF_REGSIZE: Int = 4
  val BYTE: Int = 1
}

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
  val overflow = Value("o")
}

object ArithmOperations extends Enumeration {
  type ArithmOperations = Value
  val add = Value("ADD")
  val sub = Value("SUB")
  val mul = Value("MUL")
  val div = Value("DIV")
  val mod = Value("MOD")
  
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


case class TestInstr(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction {
  def toIntelString: String = s"test ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}"

}

case class CheckMoveNotEqual(
    dest: Operand,
    src: Operand,
    instrSize: InstrSize
) extends Instruction {
  def toIntelString: String = s"cmovne ${dest.toIntelString(instrSize)}, ${src.toIntelString(instrSize)}"

}

case class ConvertDoubleWordToQuadWord() extends Instruction

case class AndInstr(dest: Operand, src: Operand, instrSize: InstrSize)
    extends Instruction
case class OrInstr(dest: Operand, src: Operand, instrSize: InstrSize)
    extends Instruction
case class NotInstr(dest: Operand, instrSize: InstrSize) extends Instruction
case class Eor(dest: Operand, src: Operand, operand: Operand, instrSize: InstrSize)
    extends Instruction
case class Orr(dest: Operand, src: Operand, operand: Operand, instrSize: InstrSize)
    extends Instruction

case class Mov(dest: Operand, operand: Operand, size: InstrSize) extends Instruction
// TODO: 64-bit can't be sign extended, so account for this
case class MovWithSignExtend(dest: Operand, operand: Operand, size1: InstrSize, size2: InstrSize)
    extends Instruction
case class ConditionalMov(dest: Operand, src: Operand, cond: InstrCond, size: InstrSize) extends Instruction
case class MovToPtr()
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
case object R12 extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r12"
    case InstrSize.halfReg    => "r12d"
    case InstrSize.quarterReg => "r12w"
    case InstrSize.eigthReg   => "r12b"
  }
}
case object IntReg extends GeneralRegister {
  def toIntelString(size: InstrSize): String = size match {
    case InstrSize.fullReg    => "r13"
    case InstrSize.halfReg    => "r13d"
    case InstrSize.quarterReg => "r13w"
    case InstrSize.eigthReg   => "r13b"
  }
}
case object CharReg extends GeneralRegister {
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
  def toIntelString(size: InstrSize): String = RegisterPtr(FP, InstrSize.fullReg, -1 * offset).toIntelString(size)
}

/**
  * RegisterPtr represents a pointer to the address within a register with an offset
  * 
  * @param register denotes the register representing a pointer to a memory address
  * @param size denotes the size of the memory address (qword, dword, word, byte)
  * @param offset denotes the offset from the base address
  * 
  * Example use:
  * Mov(RegisterPtr(FP, InstrSize.fullReg, MAX_REGSIZE), Dest, InstrSize.fullReg) translates to
  * "mov qword ptr [rbp + 8], rax"
  */
case class RegisterPtr(val register: Register, val size : InstrSize, var offset : Int) extends Operand {
  def toIntelString(unused: InstrSize): String = {
    val prefix = size match {
      case InstrSize.fullReg => "qword"
      case InstrSize.halfReg => "dword"
      case InstrSize.quarterReg => "word"
      case InstrSize.eigthReg => "byte"
    }
    val operator = if (offset < 0) {
      offset *= -1
      "-"
    } else {
      "+"
    }
    s"$prefix ptr [${register.toIntelString(InstrSize.fullReg)} $operator $offset]"
  } 
}

case class ArrayAccessPtr(val reg1 : Register, val reg2: Register, var multiplier : Int, val ptrSize : InstrSize) extends Operand {
  def toIntelString(unused: InstrSize.InstrSize): String = {
    val prefix = ptrSize match {
      case InstrSize.fullReg => "qword"
      case InstrSize.halfReg => "dword"
      case InstrSize.quarterReg => "word"
      case InstrSize.eigthReg => "byte"
    }
    val operator = if (multiplier < 0) {
      multiplier *= -1
      "-"
    } else {
      "+"
    }
    s"$prefix ptr [${reg1.toIntelString(InstrSize.fullReg)} $operator ${multiplier}*${reg2.toIntelString(InstrSize.fullReg)}]"
  }
}

case class FPOffsetPlusReg(val offset: Int) extends Operand {
  def toIntelString(size: InstrSize): String = s"qword ptr [rbp + $offset]"
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

case class Reg32Offset(reg: Register, offset: Int) extends Operand {
  def toIntelString(size: InstrSize): String = s"dword ptr [${reg.toIntelString(InstrSize.fullReg)} + $offset]"
}

case class Reg32(reg: Register) extends Operand {
  def toIntelString(size: InstrSize): String = s"dword ptr [${reg.toIntelString(InstrSize.fullReg)}]"
}

case class Reg16(reg: Register) extends Operand {
  def toIntelString(size: InstrSize): String = s"word ptr [${reg.toIntelString(size)}]"
}

case class Reg8(reg: Register) extends Operand {
  def toIntelString(size: InstrSize): String = s"byte ptr [${reg.toIntelString(size)}]"
}
