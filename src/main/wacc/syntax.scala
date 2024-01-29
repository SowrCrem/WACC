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
case class Null() extends Atom
case class Ident(value: String) extends Atom
case class Error(value: String) extends Atom

object IntLiter extends generic.ParserBridge1[Int, Atom]
object BoolLiter extends generic.ParserBridge1[Boolean, Atom]
object CharLiter extends generic.ParserBridge1[Char, Atom]
object StringLiter extends generic.ParserBridge1[String, Atom]
object Brackets extends generic.ParserBridge1[Expr, Atom]
object Ident extends generic.ParserBridge1[String, Atom]
