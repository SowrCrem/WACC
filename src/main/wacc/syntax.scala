package wacc
import parsley.generic

sealed trait Node



// Expr (Extending Node)

sealed trait Expr extends Node

case class ArrayElem(ident: Ident, eList: List[Expr]) extends Expr // list of expressions so we can have x[1][2][3] etc


// Binary Operators

sealed trait BinOp extends Expr

sealed trait IntBinOp extends BinOp
sealed trait CompBinOp extends BinOp
sealed trait BoolBinOp extends BinOp

case class Mult(lhs: Expr, rhs: Expr) extends IntBinOp
case class Div(lhs: Expr, rhs: Expr) extends IntBinOp
case class Mod(lhs: Expr, rhs: Expr) extends IntBinOp
case class Plus(lhs: Expr, rhs: Expr) extends IntBinOp
case class Minus(lhs: Expr, rhs: Expr) extends IntBinOp

case class GreaterThan(lhs: Expr, rhs: Expr) extends CompBinOp
case class GreaterThanEq(lhs: Expr, rhs: Expr) extends CompBinOp
case class LessThan(lhs: Expr, rhs: Expr) extends CompBinOp
case class LessThanEq(lhs: Expr, rhs: Expr) extends CompBinOp
case class Equals(lhs: Expr, rhs: Expr) extends CompBinOp
case class NotEquals(lhs: Expr, rhs: Expr) extends CompBinOp

case class And(lhs: Expr, rhs: Expr) extends BoolBinOp
case class Or(lhs: Expr, rhs: Expr) extends BoolBinOp


// Unary Operators

sealed trait UnaryOp extends Expr

case class Ord(expr: Expr) extends UnaryOp 
case class Chr(expr: Expr) extends UnaryOp
case class Neg(expr: Expr) extends UnaryOp
case class Not(expr: Expr) extends UnaryOp
case class Len(expr: Expr) extends UnaryOp

// Atoms (Extending Expr)
sealed trait Atom extends Expr
case class IntLiter(value: Int) extends Atom
case class BoolLiter(value: Boolean) extends Atom
case class CharLiter(value: Char) extends Atom
case class StringLiter(value: String) extends Atom
case class Brackets(expr: Expr) extends Atom
case class Null() extends Atom
case class Ident(value: String) extends Atom
case class Error(value: String) extends Atom

object IntLiter extends generic.ParserBridge1[Int, Atom]
object BoolLiter extends generic.ParserBridge1[Boolean, Atom]
object CharLiter extends generic.ParserBridge1[Char, Atom]
object StringLiter extends generic.ParserBridge1[String, Atom]
object Brackets extends generic.ParserBridge1[Expr, Atom]
object Ident extends generic.ParserBridge1[String, Atom]
