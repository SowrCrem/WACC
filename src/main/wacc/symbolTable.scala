package wacc
import scala.collection.mutable.{Map, Stack}
import scala.annotation.tailrec

class SymbolTable(val parent: Option[SymbolTable]) {

  val dictionary: Map[String, Position] = Map()

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



  // could map string to AST type position itself
  // private val dictionary: Map[String, TypeNode] = Map()



  // // Look up name in all enclosing symbol tables and maybe return position
  // def lookupAll(name: String): Option[TypeNode] = {
  //   lookup(name) match {
  //     case None => {
  //       var currentScope: symbolTable = this
  //       var result: Option[TypeNode] = None
  //       while (result.isEmpty && currentScope.parent.isDefined) {
  //         currentScope = currentScope.parent.get
  //         result = currentScope.lookup(name)
  //       }

  //       result
  //     }
  //     case Some(value) => Some(value)
  //   }
  // }

  // // Check if types t1 and t2 are compatible
  // // TODO: Finish implementation
  // def assignCompat(lhs: TypeNode, rhs: TypeNode): Boolean = {
  //   if (lhs.isInstanceOf[ArrayTypeNode] && rhs.isInstanceOf[ArrayTypeNode]) {
  //     // TODO: check if element types are compatible
  //     return true
  //   } else {
  //     return false
  //   }
  // }

  // // TODO: Finish cases for semantic checker
  // def semanticCheck(position: Position): Either[String, Int] = {
  //   position match {
  //     case Program(funcList, stat) => {
  //       // for (func@Func(typeNode, ident, _, stat) <- funcList) {
  //       //   // For each function
  //       // }
  //       return Right(0)
  //     }
  //   }
  // }
}
