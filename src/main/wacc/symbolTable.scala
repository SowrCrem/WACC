package wacc
import scala.collection.mutable.{Map, Stack}
import scala.annotation.tailrec
 
class SymbolTable(val parent: Option[SymbolTable]) {

  var dictionary: Map[String, TypeNode] = Map()
  var children: List[SymbolTable] = List()

  var LazyVars: Set[String] = Set()

  // Add identity and position node into dictionary
  def add(identName: String, position: TypeNode): Unit = {
    dictionary.addOne(identName, position)
  }

  def addLazyVar(identName: String): Unit = {
    LazyVars += identName
    printf("Added %s to lazy vars\n", identName)
  }

  def isLazyVar(identName: String): Boolean = {
    LazyVars.contains(identName)
  }

  // Look up name in symbol table and maybe return position
  def lookup(name: String): Option[Position] = {
    dictionary.get(name)
  }

  // TODO: Make this global (non-dependent on its class)
  @tailrec
  final def lookupAll(
    name: String, 
    symbolTable: Option[SymbolTable]
  ) : Option[Position] = symbolTable match {
    case None => None 
    case Some(st) => st.lookup(name) match {
      case None        => lookupAll(name, st.parent)
      case Some(value) => Some(value)
    }
  }

  // Add child to symbol table children list
  private def addChild(child: SymbolTable): SymbolTable = {
    children = children :+ child
    child
  }

  // Return a new symbol table with this as its parent
  def enterScope(): SymbolTable = {
    addChild(new SymbolTable(Some(this)))
  }

  // Return to parent symbol table
  def exitScope(): Option[SymbolTable] = {
    this.parent
  }

  def printSymbolTable(): Unit = {
    println("====Symbol Table==") 
    if (dictionary.size == 0) {
      println("Empty")
    }
    println(dictionary)
    children.foreach(_.printSymbolTable())
    println("==================")
  }

}
