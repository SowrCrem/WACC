package wacc
import parsley.generic

sealed trait Node

sealed trait Expr extends Node

// Atoms (Extending Expr)
sealed trait Atom extends Expr
case class IntLiter(value: Int) extends Atom
case class BoolLiter(value: Boolean) extends Atom
case class CharLiter(value: Char) extends Atom
case class StringLiter(value: String) extends Atom
case class Brackets(expr: Expr) extends Atom
case class PairLiter() extends Atom
case class Ident(value: String) extends Atom
case class Error(value: String) extends Atom

object IntLiter extends generic.ParserBridge1[Int, Expr]
object BoolLiter extends generic.ParserBridge1[Boolean, Expr]
object CharLiter extends generic.ParserBridge1[Char, Expr]
object StringLiter extends generic.ParserBridge1[String, Expr]
object Brackets extends generic.ParserBridge1[Expr, Expr]
object Ident extends generic.ParserBridge1[String, Expr]
