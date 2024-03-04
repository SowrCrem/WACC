package wacc

import scala.collection.mutable._
import parsley.internal.machine.instructions.Instr
import parsley.internal.deepembedding.singletons.Offset

object functionGenerator {

  private val funcLabelsAndBody = new HashMap[String, ListBuffer[Instruction]]()

  private val labelsToTables = new HashMap[String, SymbolTable]()

  private val labelsToFuncs = new HashMap[String, Func]()

  private val funcDefined = new HashMap[String, Boolean]()

  def isDefined(name: String): Boolean = {
    funcDefined(name)
  }

  def setDefined(name: String): Unit = {
    funcDefined.put(name, true)
  }

  def addFunction(name: String, body: ListBuffer[Instruction]): Unit = {
    funcLabelsAndBody.put(name, body)
    funcDefined.put(name, false)
  }

  def addFunction(
      name: String,
      func: Func,
      body: ListBuffer[Instruction]
  ): Unit = {
    funcLabelsAndBody.put(name, body)
    labelsToTables.put(name, func.symbolTable)
    labelsToFuncs.put(name, func)
    funcDefined.put(name, false)
  }

  def getFunctionNode(name: String): Func = {
    labelsToFuncs(name)
  }

  def getFunctionLabel(name: String): String = {
    name
  }

  def getFunctionBody(name: String): ListBuffer[Instruction] = {
    funcLabelsAndBody(name)
  }

  def getFunctionTable(name: String): SymbolTable = {
    labelsToTables(name)
  }

  def generateFunctionCode(): ListBuffer[Instruction] = {

    val code = new ListBuffer[Instruction]()
    for ((label, body) <- funcLabelsAndBody) {
    

      code += Label("_" + label)
      code ++= body
     
    }
    code

  }

  def reset(): Unit = {
    funcLabelsAndBody.clear()
    labelsToFuncs.clear()
    labelsToTables.clear()
    funcDefined.clear()
  }
}
