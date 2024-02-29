package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr
import parsley.internal.deepembedding.singletons.Offset

object functionGenerator {
    private val funcLabelsAndBody = new HashMap[String, ListBuffer[Instruction]]()

    def addFunction(name: String, body: ListBuffer[Instruction]): Unit = {
        funcLabelsAndBody.put(name, body)
    }

    def generateFunctionCode(): ListBuffer[Instruction] = {
        val code = new ListBuffer[Instruction]()
        for ((label, body) <- funcLabelsAndBody) {
            code += Label(label)
            code ++= body
        }
        code
    }   

    def reset() : Unit = {
        funcLabelsAndBody.clear()
    }
}