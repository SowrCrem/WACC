// package test.backend.unit

// import wacc._
// import test.Utils._
// import parsley.{Failure, Result, Success}
// import org.scalactic.Bool
// import scala.collection.mutable.ListBuffer
// import org.scalatest.BeforeAndAfterEach
// import org.scalatest.flatspec.AnyFlatSpec
// import org.scalatest.compatible.Assertion
// import org.scalatest.matchers.should.Matchers._
// import sys.process._

// class translateExit extends AnyFlatSpec with BeforeAndAfterEach {

//   val pos = (0, 0)
//   val node = Program(List(), List(Exit(IntLiter(-1)(pos))(pos)))(pos)
//   val filename = ".." + java.io.File.separator + "X86Code.s"

//   //This is based on the reference compiler output for valid/exit-1.wacc
//   val instrs: ListBuffer[Instruction] = ListBuffer(
//     Directive("intel_syntax noprefix"),
//     Directive("globl main"),
//     Directive("section .rodata"),
//     Directive("text"),
//     Label("main"),
//     PushRegisters(List(FP, G0)),
//     Mov(FP, SP),
//     Mov(Dest, Immediate32(-1)),
//     Mov(Arg0, Dest),
//     CallInstr("exit"),
//     Mov(Dest, Immediate32(0)),
//     PopRegisters(List(G0, FP)),
//     ReturnInstr(),
//     Label("_exit"),
//     PushRegisters(List(FP)),
//     Mov(FP, SP),
//     AndInstr(SP, Immediate32(-16)),
//     CallPLT("exit"),
//     Mov(SP, FP),
//     PopRegisters(List(FP)),
//     ReturnInstr()
//   )

//   val instrsTranslated = List(
//         ".intel_syntax noprefix",
//         ".globl main",
//         ".section .rodata",
//         ".text",
//         "main:",
//         "  push rbp",
//         "  push rbx",
//         "  mov rbp, rsp",
//         "  mov rax, -1",
//         "  mov rdi, rax",
//         "  call _exit",
//         "  mov rax, 0",
//         "  pop rbx",
//         "  pop rbp",
//         "  ret",
//         "",
//         "_exit:",
//         "  push rbp",
//         "  mov rbp, rsp",
//         "  and rsp, -16",
//         "  call exit@plt",
//         "  mov rsp, rbp",
//         "  pop rbp",
//         "  ret",
//         ""
//       ).mkString("\n")

//   "compiler" should "create IR for basic exit program" in {

//     X86IRGenerator.generateIR(node) should be(
//       instrs
//     )
//   }

//   it should "create X86 assembly for basic exit program" in {

//     println(X86CodeGenerator.makeAssemblyIntel(instrs))
//     X86CodeGenerator.makeAssemblyIntel(instrs) should be {
//       instrsTranslated
//     }
//   }

//   it should "save the X86 assembly for basic exit program" in {
//     Main.saveGeneratedCode(node)
//     val fileContent = scala.io.Source.fromFile(filename).mkString
//     fileContent should be {
//       instrsTranslated
//     }
//   }
// }
