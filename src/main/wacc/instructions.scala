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

case class Push(src: Operand) extends Instruction
case class Pop(dest: Operand) extends Instruction
case class PushRegisters(registers: List[Register]) extends Instruction
case class PopRegisters(registers: List[Register]) extends Instruction
case class Directive(val name: String) extends Instruction
case class Label(val name: String) extends Instruction
case class CallInstr(val name: String) extends Instruction
case class CallPLT(val name: String) extends Instruction
case class ReturnInstr() extends Instruction


sealed trait Operand {
    def toIntelString: String
}

sealed trait Register extends Operand

sealed trait SpecialRegister extends Register
case object FP extends SpecialRegister {
    def toIntelString: String = "rbp"
} // Frame pointer
case object SP extends SpecialRegister {
    def toIntelString: String = "rsp"
} // Stack pointer
case object Dest extends SpecialRegister {
    def toIntelString: String = "rax"
} // Destination register

sealed trait GeneralRegister extends Register
case object G0 extends GeneralRegister {
    def toIntelString: String = "rbx"
}
case object G1 extends GeneralRegister {
    def toIntelString: String = "r10"
}
case object G2 extends GeneralRegister {
    def toIntelString: String = "r11"
}
case object G3 extends GeneralRegister {
    def toIntelString: String = "r12"
}
case object G4 extends GeneralRegister {
    def toIntelString: String = "r13"
}
case object G5 extends GeneralRegister {
    def toIntelString: String = "r14"
}
case object G6 extends GeneralRegister {
    def toIntelString: String = "r15"
}
sealed trait ArgRegister extends Register
case object Arg0 extends ArgRegister {
    def toIntelString: String = "rdi"
}
case object Arg1 extends ArgRegister {
    def toIntelString: String = "rsi"
}
case object Arg2 extends ArgRegister {
    def toIntelString: String = "rdx"
}
case object Arg3 extends ArgRegister {
    def toIntelString: String = "rcx"
}
case object Arg4 extends ArgRegister {
    def toIntelString: String = "r8"
}
case object Arg5 extends ArgRegister {
    def toIntelString: String = "r9"
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