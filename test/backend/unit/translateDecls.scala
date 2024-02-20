package test.backend.unit

import wacc._
import test.Utils._
import parsley.{Failure, Result, Success}
import org.scalactic.Bool
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._
import sys.process._

class translateDecls extends AnyFlatSpec with BeforeAndAfterEach {

  val pos = (0, 0)
  val node = Program(
    List(),
    List(
      IdentAsgn(BoolTypeNode()(pos), Ident("b")(pos), BoolLiter(false)(pos))(
        pos
      )
    )
  )(pos)
  val filename = ".." + java.io.File.separator + "X86Code.s"

  // This is based on the reference compiler output for valid/exit-1.wacc
  val instrs: ListBuffer[Instruction] = ListBuffer(
    Directive("intel_syntax noprefix"),
    Directive("globl main"),
    Directive("section .rodata"),
    Directive("text"),
    Label("main"),
    PushRegisters(List(FP, G0)),
    Mov(FP, SP),
    DecrementStackPointerNB(1),
    Mov(Dest, Immediate32(0)),
    SubInstr(SP, Immediate32(4), null),
    Mov(SP, Dest),
    Mov(Dest, Immediate32(0)),
    PopRegisters(List(G0, FP)),
    ReturnInstr()
  )
  val instrsTranslated = """.intel_syntax noprefix
                            .globl main
                            .section .rodata
                            .text
                            main:
                              push rbp
                              push rbx
                              mov rbp, rsp
                              sub rsp, 8
                              mov rax, 0
                              sub rsp, 4
                              mov rsp, rax
                              mov rax, 0
                              pop rbx
                              pop rbp
                              ret"""

  "compiler" should "create IR for basic boolean decl program" ignore {

    semanticChecker.check(node) should be {
      Right(0)
    }

    X86IRGenerator.generateIR(node) should be(
      instrs
    )
  }

  // I DO NOT THINK THIS IS RIGHT WE NEED TO CHECK THIS
  it should "create X86 assembly for basic boolean decl program" ignore {

    println(X86CodeGenerator.makeAssemblyIntel(instrs))
    val x = X86CodeGenerator.makeAssemblyIntel(instrs)
    print(x)
  }

  it should "save the X86 assembly for basic bool decl program" ignore {
    Main.saveGeneratedCode(node)
    val fileContent = scala.io.Source.fromFile(filename).mkString
    fileContent should be {
      instrsTranslated
    }
  }
}
