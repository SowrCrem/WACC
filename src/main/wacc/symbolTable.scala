package wacc
import scala.collection.mutable.{Map, Stack}

class symbolTable(val parent: Option[symbolTable]) {
  // could map string to AST type node itself
  private val dictionary: Map[String, TypeNode] = Map()

  // Add name and node to symbol table
  def add(name: String, ident: TypeNode): Unit = {
    dictionary += (name -> ident)
  }

  // Look up name in symbol table and maybe return node
  def lookup(name: String): Option[TypeNode] = {
    dictionary.get(name)
  }

  // Look up name in all enclosing symbol tables and maybe return node
  def lookupAll(name: String): Option[TypeNode] = {
    lookup(name) match {
      case None => {
        var currentScope: symbolTable = this
        var result: Option[TypeNode] = None
        while (result.isEmpty && currentScope.parent.isDefined) {
          currentScope = currentScope.parent.get
          result = currentScope.lookup(name)
        }

        result
      }
      case Some(value) => Some(value)
    }
  }

  // Check if types t1 and t2 are compatible
  // TODO: Finish implementation
  def assignCompat(lhs: TypeNode, rhs: TypeNode): Boolean = {
    if (lhs.isInstanceOf[ArrayTypeNode] && rhs.isInstanceOf[ArrayTypeNode]) {
      // TODO: check if element types are compatible
      return true
    } else {
      return false
    }
  }

  // TODO: Finish cases for semantic checker
  def semanticCheck(node: Node): Either[String, Int] = {
    node match {
      case Program(funcList, stat) => {
        // for (func@Func(typeNode, ident, _, stat) <- funcList) {
        //   // For each function
        // }
        return Right(0)
      }
    }
  }
}
