package wacc

object x86IRGenerator {
    
//   def astToIR(position: Position): Buffer[Instruction] = position match {
//     case Program(funcList, stat) => {
//       val funcIR = new ListBuffer[Instruction]
//       for (func <- funcList) {
//         funcIR.appendAll(astToIR(func))
//       }
//       val ir = new ListBuffer[Instruction]
//       ir.appendAll(astToIR(stat))
//       ir.appendAll(funcIR)

//     }
//     case Exit(IntLiter(value)) => {
//       exitFunc = true;
//       Buffer(
//         Ldr(Register("r0"), Immediate(value.toInt), AL()),
//         BranchLink("_exit")
//       )
//     }
//   }


}