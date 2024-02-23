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
case class CallInstr(val name: String) extends Instruction
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
    // def toIntelString: String = s"qword [rbp - $offset]"
    // TODO (Maybe store as index rather than offset)
}

sealed trait Operand {
    def toIntelString: String
}

sealed abstract trait Register extends Operand with Address {
    def toIntelString: String
}

sealed trait SpecialRegister extends Register


case object IP extends SpecialRegister { def toIntelString: String = "rip" } // Instruction pointer

case object FP extends SpecialRegister { def toIntelString: String = "rbp" } // Frame pointer
case object SP extends SpecialRegister { def toIntelString: String = "rsp" } // Stack pointer
case object Dest extends SpecialRegister { def toIntelString: String = "rax" } // Destination register

sealed trait GeneralRegister extends Register
case object G0 extends GeneralRegister { def toIntelString: String = "rbx" }
case object G1 extends GeneralRegister { def toIntelString: String = "r10" }
case object G2 extends GeneralRegister { def toIntelString: String = "r11" }
case object G3 extends GeneralRegister { def toIntelString: String = "r12" }
case object G4 extends GeneralRegister { def toIntelString: String = "r13" }
case object G5 extends GeneralRegister { def toIntelString: String = "r14" }
case object G6 extends GeneralRegister { def toIntelString: String = "r15" }
sealed trait ArgRegister extends Register
case object Arg0 extends ArgRegister { def toIntelString: String = "rdi" }
case object Arg1 extends ArgRegister { def toIntelString: String = "rsi" }
case object Arg2 extends ArgRegister { def toIntelString: String = "rdx" }
case object Arg3 extends ArgRegister { def toIntelString: String = "rcx" }
case object Arg4 extends ArgRegister { def toIntelString: String = "r8" }
case object Arg5 extends ArgRegister { def toIntelString: String = "r9" }

case class FPOffset(val offset: Int) extends Operand {
    def toIntelString: String = s"qword ptr [rbp - $offset]"
}

case class LabelAddress(val label: String) extends Operand {
    def toIntelString: String = label
}

case class RegisterLabelAddress(val register: Register, val label: LabelAddress) extends Operand {
    def toIntelString: String = s"[${register.toIntelString} + .${label.toIntelString}]"
}

case class Immediate32(val value: Int) extends Operand {
    def verify: Boolean = value >= Int.MinValue && value <= Int.MaxValue
    def toIntelString: String = {
        if (this.verify)
            value.toString
        else
            throw new IllegalArgumentException("Immediate value out of range")
    }
}
case object EMPTY extends Operand {
    def toIntelString: String = ""
}

case class OffsetReg(reg: Register, offset: Int) extends Operand with Register {
    def toIntelString: String = s"${reg.toIntelString} + $offset"
}

case class Reg64(reg: Register) extends Operand {
    def toIntelString: String = s"qword ptr [${reg.toIntelString}]"
}

case class Reg32(reg: Register) extends Operand {
    def toIntelString: String = s"dword ptr [${reg.toIntelString}]"
}