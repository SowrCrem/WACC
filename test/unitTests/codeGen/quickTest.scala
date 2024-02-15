package test.backend.codeGen

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
import wacc.{CallInstr, ReturnInstr, AndInstr, CallPLT, Push, Pop, PushRegisters, PopRegisters}


class QuickTest extends AnyFlatSpec with BeforeAndAfterEach {

  val pos = (0, 0)

  //This is based on the reference compiler output for valid/exit-1.wacc
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
    Push(FP),
    Mov(FP, SP),
    AndInstr(SP, Immediate(-16)),
    CallPLT("exit"),
    Mov(SP, FP),
    Pop(FP),
    ReturnInstr()
  )

  val instrsTranslated = List(
        ".intel_syntax noprefix",
        ".globl main",
        ".section .rodata",
        ".text",
        "main:",
        "  push rbp",
        "  push rbx",
        "  mov rbp, rsp",
        "  mov rax, -1",
        "  mov rdi, rax",
        "  call _exit",
        "  mov rax, 0",
        "  pop rbx",
        "  pop rbp",
        "  ret",
        "",
        "_exit:",
        "  push rbp",
        "  mov rbp, rsp",
        "  and rsp, -16",
        "  call exit@plt",
        "  mov rsp, rbp",
        "  pop rbp",
        "  ret",
        ""
      ).mkString("\n")

  "compiler" should "create IR for basic exit program" in {
    val node = Program(List(), List(Exit(IntLiter(-1)(pos))(pos)))(pos)

    X86IRGenerator.generateIR(node) should be(
      instrs
    )
  }

  it should "create X86 assembly for basic exit program" in {

    println(X86CodeGenerator.makeAssemblyIntel(instrs))
    X86CodeGenerator.makeAssemblyIntel(instrs) should be {
      instrsTranslated
    }
  }
}
