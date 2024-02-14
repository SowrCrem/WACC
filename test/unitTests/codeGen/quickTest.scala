// import org.scalatest.flatspec.AnyFlatSpec
// import org.scalatest.matchers.should.Matchers._
// import wacc.Main
// import wacc.{
//   Position,
//   IntLiter,
//   CharLiter,
//   StringLiter,
//   BoolLiter,
//   Ident,
//   Brackets,
//   Null,
//   Error,
//   ArrayElem,
//   Plus,
//   Mul,
//   Div,
//   Not,
//   Len,
//   Skip,
//   Read,
//   If,
//   While,
//   Free,
//   Return,
//   Exit,
//   Print,
//   Println,
//   StatJoin,
//   IdentAsgn,
//   IntTypeNode,
//   PairTypeNode,
//   PairElemTypeNode,
//   ArrayTypeNode,
//   BoolTypeNode,
//   CharTypeNode,
//   StringTypeNode,
//   Func,
//   ParamList,
//   Param,
//   Program,
//   TypeNode,
//   Call,
//   Expr,
//   NewPair,
//   ArgList,
//   ArrayLiter,
//   Neg,
//   Ord,
//   Chr,
//   Mod,
//   Minus,
//   GreaterThan,
//   GreaterThanEq,
//   LessThan,
//   LessThanEq,
//   Equals,
//   NotEquals,
//   And,
//   Or
// }
// import parsley.{Failure, Result, Success}
// import wacc.parser._
// import wacc.lexer._
// import org.scalactic.Bool
// import org.scalatest.compatible.Assertion
// import wacc.SymbolTable
// import org.scalatest.BeforeAndAfterEach
// import wacc.TypeChecker
// import wacc.IRGenerator
// import wacc.Register
// import wacc.Mov
// import wacc.BranchLink
// import wacc.Immediate
// import wacc.AL
// import wacc.PopRegisters
// import wacc.Directive
// import wacc.PushRegisters
// import wacc.Label
// import wacc.Ldr
// import wacc.BitClear
// import scala.collection.mutable.ListBuffer
// import wacc.Arm32CodeGenerator
// import wacc.Instruction

// class quickTEst extends AnyFlatSpec with BeforeAndAfterEach {

//   val pos = (0, 0)

//   //This is based on the reference compiler output for valid/exit-1.wacc

//   "compiler" should "create IR for basic exit program" in {
//     val node = Program(List(), Exit(IntLiter(-1)(pos))(pos))(pos)

//     IRGenerator.astToIR(node) should be(
//       List(Ldr(Register("r0"), Immediate(-1), AL()), BranchLink("_exit"))
//     )

//     val instrs: ListBuffer[Instruction] = ListBuffer(
//       Directive("data"),
//       Directive("align 4"),
//       Directive("text"),
//       Directive("global main"),
//       Label("main"),
//       PushRegisters(List(Register("fp"), Register("lr"))),
//       PushRegisters(List(Register("r8"), Register("r10"), Register("r12"))),
//       Mov(Register("fp"), Register("sp"), AL()),
//       Ldr(Register("r0"), Immediate(-1), AL()),
//       BranchLink("_exit"),
//       Label("_exit"),
//       PushRegisters(List(Register("fp"), Register("r1"))),
//       Mov(Register("fp"), Register("sp"), AL()),
//       BitClear(Register("sp"), Register("sp"), 7),
//       BranchLink("exit"),
//       Mov(Register("sp"), Register("fp"), AL()),
//       PopRegisters(List(Register("fp"), Register("pc"))),
//       Mov(Register("r0"), Immediate(0), AL()),
//       PopRegisters(List(Register("r8"), Register("r10"), Register("r12"))),
//       PopRegisters(List(Register("fp"), Register("pc")))
//     )

//     IRGenerator.generateIR(node) should be(
//       instrs
//     )

//     Arm32CodeGenerator.transInstr(
//       Ldr(Register("r0"), Immediate(-1), AL())
//     ) should be(
//       "ldr r0, =-1"
//     )

//     instrs.map(Arm32CodeGenerator.transInstr) should be(
//       ListBuffer(
//         ".data",
//         ".align 4",
//         ".text",
//         ".global main",
//         "main:",
//         "push {fp, lr}",
//         "push {r8, r10, r12}",
//         "mov fp, sp",
//         "ldr r0, =-1",
//         "bl _exit",
//         "_exit:",
//         "push {fp, r1}",
//         "mov fp, sp",
//         "bic sp, sp, 7",
//         "bl exit",
//         "mov sp, fp",
//         "pop {fp, pc}",
//         "mov r0, =0",
//         "pop {r8, r10, r12}",
//         "pop {fp, pc}"
//       )
//     )

//   }
// }
