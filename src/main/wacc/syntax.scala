package wacc
import parsley.generic

sealed trait Node



// Expr (Extending Node)

sealed trait Expr extends Node

case class ArrayElem(ident: Ident, eList: List[Expr]) extends Expr // list of expressions so we can have x[1][2][3] etc
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
object Mul extends generic.ParserBridge2[Expr, Expr, IntBinOp] 
case class Div(lhs: Expr, rhs: Expr) extends IntBinOp
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


// Types 
sealed trait Type

sealed trait BaseType extends Type
case class IntType() extends BaseType with PairElemType
case class BoolType() extends BaseType with PairElemType
case class CharType() extends BaseType with PairElemType
case class StringType() extends BaseType with PairElemType

case class ArrayType(elementType: Type) extends Type with PairElemType
case class PairType(fst: PairElemType, snd: PairElemType) extends Type with PairElemType

sealed trait PairElemType extends Type

// sealed trait PairBaseType extends PairElemType

// case class PairBaseIntType() extends PairBaseType
// case class PairBaseBoolType() extends PairBaseType
// case class PairBaseCharType() extends PairBaseType
// case class PairBaseStringType() extends PairBaseType

case class PairArrayType(elementType: Type) extends PairElemType