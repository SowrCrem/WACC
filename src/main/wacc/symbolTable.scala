package wacc
import scala.collection.mutable.{Map, Stack}
import scala.annotation.tailrec

/* TODOs:
   [ ] Somehow store link between parent and child STs; or scopes
   [ ] exitScope is not used at all (unnecessary function)

   */

class SymbolTable(val parent: Option[SymbolTable]) {

  val dictionary: Map[String, Position] = Map()

  // Add identity and position node into dictionary
  def add(identName: String, position: Position): Unit = {
    dictionary.addOne(identName, position)
  }

  // Look up name in symbol table and maybe return position
  def lookup(name: String): Option[Position] = {
    dictionary.get(name)
  }

  @tailrec
  final def lookupAll(name: String, symbolTable: Option[SymbolTable]) : Option[Position] = {
    symbolTable match {
      case None => None 
      case Some(st) =>
        val inferred = st.lookup(name)
        inferred match {
          case None => lookupAll(name, st.parent)
          case Some(value) => Some(value)
        }
    }
  }

  def enterScope(): SymbolTable = {
    new SymbolTable(Some(this))
  }

  def exitScope(): Option[SymbolTable] = {
    this.parent
  }

}
