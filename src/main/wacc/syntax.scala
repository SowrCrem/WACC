package wacc

sealed trait Node

sealed trait ExprNode extends Node

// Atoms (Extending Expr)
sealed trait Atom extends ExprNode
case class IntLiterNode(value: Int) extends Atom
case class BoolLiterNode(value: Boolean) extends Atom
case class CharLiterNode(value: Char) extends Atom
case class StringLiterNode(value: String) extends Atom
case class BracketsNode(expr: ExprNode) extends Atom
case class PairLiterNode() extends Atom

case class IdentNode(value: String) extends Atom


case class ErrorNode(value: String) extends Atom