import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Position,
  IntLiter,
  CharLiter,
  StringLiter,
  BoolLiter,
  Ident,
  Brackets,
  Null,
  Errors,
  ArrayElem,
  Plus,
  Mul,
  Div,
  Not,
  Len,
  Skip,
  Read,
  If,
  While,
  Free,
  Return,
  Exit,
  Print,
  Println,
  IdentAsgn,
  IntTypeNode,
  PairTypeNode,
  PairElemTypeNode,
  ArrayTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  Func,
  ParamList,
  Param,
  Program,
  TypeNode,
  Call,
  Expr,
  NewPair,
  ArrayLiter,
  Neg,
  Ord,
  Chr,
  Mod,
  Minus,
  GreaterThan,
  GreaterThanEq,
  LessThan,
  LessThanEq,
  Equals,
  NotEquals,
  And,
  Or
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.SymbolTable
import org.scalatest.BeforeAndAfterEach
import wacc.TypeChecker
import wacc.Instruction
import wacc.Mov
import wacc.Immediate
import wacc.PopRegisters
import wacc.Directive
import wacc.PushRegisters
import wacc.Label
import scala.collection.mutable.ListBuffer
import wacc.{REG, FP, SP}
import wacc._
import wacc.{CallInstr, ReturnInstr, AndInstr}


class quickTEst extends AnyFlatSpec with BeforeAndAfterEach {

  val pos = (0, 0)

  //This is based on the reference compiler output for valid/exit-1.wacc

  "compiler" should "create IR for basic exit program" in {
    val node = Program(List(), List(Exit(IntLiter(-1)(pos))(pos)))(pos)

    val instrs: ListBuffer[Instruction] = ListBuffer(
      Directive("intel_syntax noprefix"),
      Directive("globl main"),
      Directive("section .rodata"),
      Directive("text"),
      Label("main"),
      PushRegisters(List(FP, REG(1))),
      Mov(FP, SP),
      Mov(REG(0), Immediate(-1)),
      Mov(REG(5), REG(0)),
      CallInstr("exit"),
      Mov(REG(0), Immediate(0)),
      PopRegisters(List(REG(1), FP)),
      ReturnInstr(),
      Label("_exit"),
      Push(REG(1)),
      Mov(FP, SP),
      AndInstr(SP, Immediate(-16)),
      CallInstr("exit@plt"),
      Mov(SP, FP),
      Pop(REG(1)),
      ReturnInstr()
    )

    // IRGenerator.generateIR(node) should be(
    //   instrs
    // )

    // Arm32CodeGenerator.transInstr(
    //   Ldr(Register("r0"), Immediate(-1), AL())
    // ) should be(
    //   "ldr r0, =-1"
    // )

    // instrs.map(Arm32CodeGenerator.transInstr) should be(
    //   ListBuffer(
    //     ".data",
    //     ".align 4",
    //     ".text",
    //     ".global main",
    //     "main:",
    //     "push {fp, lr}",
    //     "push {r8, r10, r12}",
    //     "mov fp, sp",
    //     "ldr r0, =-1",
    //     "bl _exit",
    //     "_exit:",
    //     "push {fp, r1}",
    //     "mov fp, sp",
    //     "bic sp, sp, 7",
    //     "bl exit",
    //     "mov sp, fp",
    //     "pop {fp, pc}",
    //     "mov r0, =0",
    //     "pop {r8, r10, r12}",
    //     "pop {fp, pc}"
    //   )
    // )

  }
}
