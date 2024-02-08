package wacc
import parsley.generic

sealed trait Node

// Program (Extending Node)

case class Program(funcList: List[Func], stat: Stat) extends Node
object Program extends generic.ParserBridge2[List[Func], Stat, Program]


case class Func(typeNode: TypeNode, ident: Ident, paramList: ParamList, stat: Stat) extends Node
object Func extends generic.ParserBridge4[TypeNode, Ident, ParamList, Stat, Func]

// Param-List (Extending Node)

case class ParamList(paramList: List[Param]) extends Node
object ParamList extends generic.ParserBridge1[List[Param], ParamList]



// Param (Extending Node)

case class Param(typeNode: TypeNode, ident: Ident) extends Node
object Param extends generic.ParserBridge2[TypeNode, Ident, Param]

// Statements (Extending Node)
sealed trait Stat extends Node

case class Skip() extends Stat
case class IdentAsgn (typeNode: TypeNode, ident: Ident, expr: Node) extends Stat
object IdentAsgn extends generic.ParserBridge3[TypeNode, Ident, Node, Stat]
case class AsgnEq(lhs: Node, rhs: Node) extends Stat
object AsgnEq extends generic.ParserBridge2[Node, Node, Stat]
case class Read(lhs: Expr) extends Stat
object Read extends generic.ParserBridge1[Expr, Stat]
case class Free(expr: Expr) extends Stat
object Free extends generic.ParserBridge1[Expr, Stat]
case class Return(expr: Expr) extends Stat
object Return extends generic.ParserBridge1[Expr, Stat] {
  override def labels() = List("return")
}
case class Exit(expr: Expr) extends Stat
object Exit extends generic.ParserBridge1[Expr, Stat] {
  override def labels() = List("exit statement")
}
case class Print(expr: Expr) extends Stat
object Print extends generic.ParserBridge1[Expr, Stat]
case class Println(expr: Expr) extends Stat
object Println extends generic.ParserBridge1[Expr, Stat]
case class If(expr: Expr, stat1: Stat, stat2: Stat) extends Stat
object If extends generic.ParserBridge3[Expr, Stat, Stat, Stat] {
  override def labels() = {
    List("if statement")
  }
  
}
case class While(expr: Expr, stat: Stat) extends Stat
object While extends generic.ParserBridge2[Expr, Stat, Stat] {
  override def labels() = List("while loop")
}
case class BeginEnd(stat: Stat) extends Stat
object BeginEnd extends generic.ParserBridge1[Stat, Stat]
case class StatJoin (statList: List[Stat]) extends Stat
object StatJoin extends generic.ParserBridge1[List[Stat], Stat] {
  override def labels() = List("statement")
}



// RValue 

sealed trait RValue 

// Expr (Extending Node)

sealed trait Expr extends Node with RValue


case class NewPair(fst: Expr, snd: Expr) extends Expr
object NewPair extends generic.ParserBridge2[Expr, Expr, Expr]

case class ArgList (argList: List[Expr]) extends Node
object ArgList extends generic.ParserBridge1[List[Expr], ArgList]

case class Call(ident: Ident, args: ArgList) extends Stat
object Call extends generic.ParserBridge2[Ident, ArgList, Stat]

case class ArrayLiter(exprList: List[Expr]) extends Expr  
object ArrayLiter extends generic.ParserBridge1[List[Expr], Expr]

case class FstNode(expr: Expr) extends Expr with LValue
object FstNode extends generic.ParserBridge1[Expr, Expr]

case class SndNode(expr: Expr) extends Expr with LValue
object SndNode extends generic.ParserBridge1[Expr, Expr]


// LValue (Extending Expr)

sealed trait LValue extends Expr


case class ArrayElem(ident: Ident, eList: List[Expr]) extends LValue// list of expressions so we can have x[1][2][3] etc
object ArrayElem extends generic.ParserBridge2[Atom, List[Expr], Expr] {
  def apply(atom: Atom, exprList: List[Expr]): Expr = atom match {
    case ident: Ident => ArrayElem(ident, exprList)
    case _ => throw new IllegalArgumentException("Expected Ident, got " + atom)
  }
}

// Binary Operators

sealed trait BinOp extends Expr

sealed trait IntBinOp extends BinOp
sealed trait CompBinOp extends BinOp
sealed trait BoolBinOp extends BinOp

case class Mul(lhs: Expr, rhs: Expr) extends IntBinOp 
object Mul extends generic.ParserBridge2[Expr, Expr, IntBinOp] {
  def unapply(mul: Mul): Option[(Expr, Expr)] = Some((mul.lhs, mul.rhs))
}
case class Div(lhs: Expr, rhs: Expr) extends IntBinOp {
  def unapply(div: Div): Option[(Expr, Expr)] = Some((div.lhs, div.rhs))
}
object Div extends generic.ParserBridge2[Expr, Expr, IntBinOp]
case class Mod(lhs: Expr, rhs: Expr) extends IntBinOp
object Mod extends generic.ParserBridge2[Expr, Expr, IntBinOp]
case class Plus(lhs: Expr, rhs: Expr) extends IntBinOp
object Plus extends generic.ParserBridge2[Expr, Expr, IntBinOp]
case class Minus(lhs: Expr, rhs: Expr) extends IntBinOp
object Minus extends generic.ParserBridge2[Expr, Expr, IntBinOp]

case class GreaterThan(lhs: Expr, rhs: Expr) extends CompBinOp
object GreaterThan extends generic.ParserBridge2[Expr, Expr, CompBinOp]
case class GreaterThanEq(lhs: Expr, rhs: Expr) extends CompBinOp
object GreaterThanEq extends generic.ParserBridge2[Expr, Expr, CompBinOp]
case class LessThan(lhs: Expr, rhs: Expr) extends CompBinOp
object LessThan extends generic.ParserBridge2[Expr, Expr, CompBinOp]
case class LessThanEq(lhs: Expr, rhs: Expr) extends CompBinOp
object LessThanEq extends generic.ParserBridge2[Expr, Expr, CompBinOp]
case class Equals(lhs: Expr, rhs: Expr) extends CompBinOp
object Equals extends generic.ParserBridge2[Expr, Expr, CompBinOp]
case class NotEquals(lhs: Expr, rhs: Expr) extends CompBinOp
object NotEquals extends generic.ParserBridge2[Expr, Expr, CompBinOp]

case class And(lhs: Expr, rhs: Expr) extends BoolBinOp
object And extends generic.ParserBridge2[Expr, Expr, BoolBinOp]
case class Or(lhs: Expr, rhs: Expr) extends BoolBinOp
object Or extends generic.ParserBridge2[Expr, Expr, BoolBinOp]


// Unary Operators

sealed trait UnaryOp extends Expr

case class Ord(expr: Expr) extends UnaryOp 
object Ord extends generic.ParserBridge1[Expr, UnaryOp]
case class Chr(expr: Expr) extends UnaryOp
object Chr extends generic.ParserBridge1[Expr, UnaryOp]
case class Neg(expr: Expr) extends UnaryOp
object Neg extends generic.ParserBridge1[Expr, UnaryOp]
case class Not(expr: Expr) extends UnaryOp
object Not extends generic.ParserBridge1[Expr, UnaryOp]
case class Len(expr: Expr) extends UnaryOp
object Len extends generic.ParserBridge1[Expr, UnaryOp]

// Atoms (Extending Expr)
sealed trait Atom extends Expr

case class IntLiter(value: Int) extends Atom
object IntLiter extends generic.ParserBridge1[Int, Atom]
case class BoolLiter(value: Boolean) extends Atom
object BoolLiter extends generic.ParserBridge1[Boolean, Atom] {
  override def labels() = List("boolean")
}
case class CharLiter(value: Char) extends Atom
object CharLiter extends generic.ParserBridge1[Char, Atom]
case class StringLiter(value: String) extends Atom
object StringLiter extends generic.ParserBridge1[String, Atom]

case class Brackets(expr: Expr) extends Atom
object Brackets extends generic.ParserBridge1[Expr, Atom]
case class Null() extends Atom with LValue with PairElemTypeNode
case class Ident(value: String) extends Atom with LValue
object Ident extends generic.ParserBridge1[String, Ident]
case class Error(value: String) extends Atom 



// Type nodes
trait TypeNode extends Node 

sealed trait BaseTypeNode extends TypeNode with PairElemTypeNode
case class IntTypeNode() extends BaseTypeNode {
  override def toString: String = "integer"
}
case class BoolTypeNode() extends BaseTypeNode {
  override def toString: String = "bool"
}
case class CharTypeNode() extends BaseTypeNode {
  override def toString: String = "char"
}
case class StringTypeNode() extends BaseTypeNode {
  override def toString: String = "string"
}

case class ArrayTypeNode(elementType: TypeNode) extends PairElemTypeNode

sealed trait PairElemTypeNode extends TypeNode

case class ErrorTypeNode() extends TypeNode with PairElemTypeNode 


case class PairTypeNode(fst: PairElemTypeNode, snd: PairElemTypeNode) extends TypeNode with LValue
object PairTypeNode extends generic.ParserBridge2[PairElemTypeNode, PairElemTypeNode, TypeNode]
