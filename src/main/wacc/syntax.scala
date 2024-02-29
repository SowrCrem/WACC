package wacc

import parsley.Parsley
import parsley.generic
import parsley.errors
import parsley.position._
import parsley.ap._
import parsley.syntax.zipped
import parsley.errors.combinator.ErrorMethods


trait Position {
  val pos: (Int, Int)
}

trait ParserSingletonBridgePos[+A] extends generic.ErrorBridge {
    def con(pos: (Int, Int)): A
    final def <#(op: Parsley[_]): Parsley[A] = pos.map(this.con) <* op
}

trait ParserBridgePos0[A] extends ParserSingletonBridgePos[A] {
    def apply()(pos: (Int, Int)): A

    override final def con(pos: (Int, Int)): A = this.apply()(pos)
}

trait ParserBridgePos1[-A, +B] extends ParserSingletonBridgePos[A => B] {
    def apply(x: A)(pos: (Int, Int)): B
    def apply(x: Parsley[A]): Parsley[B] = error(ap1(pos.map(con), x))

    override final def con(pos: (Int, Int)): A => B = this.apply(_)(pos)
}

trait ParserBridgePos2[-A, -B, +C] extends ParserSingletonBridgePos[(A, B) => C] {
    def apply(x: A, y: B)(pos: (Int, Int)): C
    def apply(x: Parsley[A], y: =>Parsley[B]): Parsley[C] = error(ap2(pos.map(con), x, y))

    override final def con(pos: (Int, Int)): (A, B) => C = this.apply(_, _)(pos)
}

trait ParserBridgePos3[-A, -B, -C, +D] extends ParserSingletonBridgePos[(A, B, C) => D] {
    def apply(x: A, y: B, z: C)(pos: (Int, Int)): D
    def apply(x: Parsley[A], y: =>Parsley[B], z: =>Parsley[C]): Parsley[D] = error(ap3(pos.map(con), x, y, z))

    override final def con(pos: (Int, Int)): (A, B, C) => D = this.apply(_, _, _)(pos)
}

trait ParserBridgePos4[-A, -B, -C, -D, +E] extends ParserSingletonBridgePos[(A, B, C, D) => E] {
    def apply(x: A, y: B, z: C, w: D)(pos: (Int, Int)): E
    def apply(x: Parsley[A], y: =>Parsley[B], z: =>Parsley[C], w: => Parsley[D]): Parsley[E] = error(ap4(pos.map(con), x, y, z, w))

    override final def con(pos: (Int, Int)): (A, B, C, D) => E = this.apply(_, _, _, _)(pos)
}


// Program (Extending Position)

case class Program(funcList: List[Func], stats: List[Stat])(val pos:(Int, Int)) extends Position {
  var symbolTable: SymbolTable  = new SymbolTable(None);
}
object Program extends ParserBridgePos2[List[Func], List[Stat], Program]

case class Func(typeNode: TypeNode, ident: Ident, paramList: ParamList, statList: List[Stat])(val pos: (Int, Int)) extends Position with TypeNode {
  var symbolTable: SymbolTable = new SymbolTable(None);
}
object Func extends ParserBridgePos4[TypeNode, Ident, ParamList, List[Stat], Func]

// Param-List (Extending Position)

case class ParamList(paramList: List[Param])(val pos: (Int, Int)) extends Position
object ParamList extends ParserBridgePos1[List[Param], ParamList]

// Param (Extending Position)

case class Param(typenode: TypeNode, ident: Ident)(val pos: (Int, Int)) extends Position with Expr  {
  typeNode = typenode;
}
object Param extends ParserBridgePos2[TypeNode, Ident, Param]

// Statements (Extending Position)
sealed trait Stat extends Position

case class Skip()(val pos: (Int, Int)) extends Stat
object Skip extends ParserBridgePos0[Stat]
case class IdentAsgn (typeNode: TypeNode, ident: Ident, rvalue: Position)(val pos: (Int, Int)) extends Stat
object IdentAsgn extends ParserBridgePos3[TypeNode, Ident, Position, IdentAsgn]
case class AsgnEq(lhs: Position, rhs: Position)(val pos: (Int, Int)) extends Stat
object AsgnEq extends ParserBridgePos2[Position, Position, AsgnEq]
case class Read(lhs: Expr)(val pos: (Int, Int)) extends Stat
object Read extends ParserBridgePos1[Expr, Read]
case class Free(expr: Expr)(val pos: (Int, Int)) extends Stat
object Free extends ParserBridgePos1[Expr, Free]
case class Return(expr: Expr)(val pos: (Int, Int)) extends Stat
object Return extends ParserBridgePos1[Expr, Return] {
  override def labels: List[String] = List("return")
}
case class Exit(expr: Expr)(val pos: (Int, Int)) extends Stat
object Exit extends ParserBridgePos1[Expr, Exit] {
  override def labels = List("exit statement")
}
case class Print(expr: Expr)(val pos: (Int, Int)) extends Stat
object Print extends ParserBridgePos1[Expr, Print]
case class Println(expr: Expr)(val pos: (Int, Int)) extends Stat
object Println extends ParserBridgePos1[Expr, Println]
case class If(expr: Expr, stats1: List[Stat], stats2: List[Stat])(val pos: (Int, Int)) extends Stat {
  var symbolTableTrue: SymbolTable = new SymbolTable(None);
  var symbolTableFalse: SymbolTable = new SymbolTable(None);

}
object If extends ParserBridgePos3[Expr, List[Stat], List[Stat], If] {
  override def labels = List("if statement")

}
case class While(expr: Expr, stats: List[Stat])(val pos: (Int, Int)) extends Stat {
  var symbolTable: SymbolTable = new SymbolTable(None);  
}
object While extends ParserBridgePos2[Expr, List[Stat], While] {
  override def labels = List("while loop")
}
case class BeginEnd(stats: List[Stat])(val pos: (Int, Int)) extends Stat {
  var symbolTable: SymbolTable = new SymbolTable(None);
}
object BeginEnd extends ParserBridgePos1[List[Stat], BeginEnd]

// RValue 
sealed trait RValue 

// Expr (Extending Position)
sealed trait Expr extends Position with RValue {
  var typeNode: TypeNode = null;
}


case class NewPair(fst: Expr, snd: Expr)(val pos: (Int, Int)) extends Expr
object NewPair extends ParserBridgePos2[Expr, Expr, NewPair]
case class Call(ident: Ident, args: List[Expr])(val pos: (Int, Int)) extends Stat
object Call extends ParserBridgePos2[Ident, List[Expr], Call]

case class ArrayLiter(exprList: List[Expr])(val pos: (Int, Int)) extends Expr  
object ArrayLiter extends ParserBridgePos1[List[Expr], ArrayLiter]

case class FstNode(expr: Expr)(val pos: (Int, Int)) extends Expr with LValue {
  override def toString: String = s"first element of $expr"
}
object FstNode extends ParserBridgePos1[Expr, FstNode]

case class SndNode(expr: Expr)(val pos: (Int, Int)) extends Expr with LValue
object SndNode extends ParserBridgePos1[Expr, SndNode]

// LValue (Extending Expr)

sealed trait LValue extends Expr


  // list of expressions so we can have x[1][2][3] etc
case class ArrayElem(ident: Ident, eList: List[Expr])(val pos: (Int, Int)) extends LValue { 
  typeNode = ident.typeNode
}
object ArrayElem extends ParserBridgePos2[Ident, List[Expr], ArrayElem]

// Binary Operators

sealed trait BinOp extends Expr

sealed trait IntBinOp extends BinOp {
  typeNode = IntTypeNode()(0, 0)
}
sealed trait CompBinOp extends BinOp {
  typeNode = BoolTypeNode()(0, 0)
}
sealed trait BoolBinOp extends BinOp {
  typeNode = BoolTypeNode()(0, 0)
}

case class Mul(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends IntBinOp 
object Mul extends ParserBridgePos2[Expr, Expr, IntBinOp] {
  def unapply(mul: Mul): Option[(Expr, Expr)] = Some((mul.lhs, mul.rhs))
}
case class Div(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends IntBinOp {
  def unapply(div: Div): Option[(Expr, Expr)] = Some((div.lhs, div.rhs))
}
object Div extends ParserBridgePos2[Expr, Expr, IntBinOp]
case class Mod(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends IntBinOp
object Mod extends ParserBridgePos2[Expr, Expr, IntBinOp]
case class Plus(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends IntBinOp
object Plus extends ParserBridgePos2[Expr, Expr, IntBinOp]
case class Minus(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends IntBinOp
object Minus extends ParserBridgePos2[Expr, Expr, IntBinOp]

case class GreaterThan(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object GreaterThan extends ParserBridgePos2[Expr, Expr, CompBinOp]
case class GreaterThanEq(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object GreaterThanEq extends ParserBridgePos2[Expr, Expr, CompBinOp]
case class LessThan(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object LessThan extends ParserBridgePos2[Expr, Expr, CompBinOp]
case class LessThanEq(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object LessThanEq extends ParserBridgePos2[Expr, Expr, CompBinOp]
case class Equals(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object Equals extends ParserBridgePos2[Expr, Expr, CompBinOp]
case class NotEquals(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends CompBinOp
object NotEquals extends ParserBridgePos2[Expr, Expr, CompBinOp]

case class And(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends BoolBinOp
object And extends ParserBridgePos2[Expr, Expr, BoolBinOp]
case class Or(lhs: Expr, rhs: Expr)(val pos: (Int, Int)) extends BoolBinOp
object Or extends ParserBridgePos2[Expr, Expr, BoolBinOp]


// Unary Operators

sealed trait UnaryOp extends Expr

case class Ord(expr: Expr)(val pos: (Int, Int)) extends UnaryOp {
  typeNode = IntTypeNode()(0, 0)
}
object Ord extends ParserBridgePos1[Expr, UnaryOp]
case class Chr(expr: Expr)(val pos: (Int, Int)) extends UnaryOp  {
  typeNode = CharTypeNode()(0, 0)
}
object Chr extends ParserBridgePos1[Expr, UnaryOp]
case class Neg(expr: Expr)(val pos: (Int, Int)) extends UnaryOp {
  typeNode = IntTypeNode()(0, 0)
}
object Neg extends ParserBridgePos1[Expr, UnaryOp]
case class Not(expr: Expr)(val pos: (Int, Int)) extends UnaryOp {
  typeNode = BoolTypeNode()(0, 0)
}
object Not extends ParserBridgePos1[Expr, UnaryOp]
case class Len(expr: Expr)(val pos: (Int, Int)) extends UnaryOp
object Len extends ParserBridgePos1[Expr, UnaryOp]

// Atoms (Extending Expr)
sealed trait Atom extends Expr

case class IntLiter(value: Int)(val pos: (Int, Int)) extends Atom {
  typeNode = IntTypeNode()(0, 0)
}
object IntLiter extends ParserBridgePos1[Int, IntLiter]
case class BoolLiter(value: Boolean)(val pos: (Int, Int)) extends Atom {
  typeNode = BoolTypeNode()(0, 0)
}
object BoolLiter extends ParserBridgePos1[Boolean, BoolLiter] {
  override def labels: List[String] = List("boolean")
}
case class CharLiter(value: Char)(val pos: (Int, Int)) extends Atom {
  typeNode = CharTypeNode()(0, 0)
}
object CharLiter extends ParserBridgePos1[Char, CharLiter]
case class StringLiter(value: String)(val pos: (Int, Int)) extends Atom {
  typeNode = StringTypeNode()(0, 0)
}
object StringLiter extends ParserBridgePos1[String, StringLiter]

case class Brackets(expr: Expr)(val pos: (Int, Int)) extends Atom {
  typeNode = expr.typeNode
}
object Brackets extends ParserBridgePos1[Expr, Brackets]
case class Null()(val pos: (Int, Int)) extends Atom with LValue with PairElemTypeNode
object Null extends ParserBridgePos0[Null]
case class Ident(value: String)(val pos: (Int, Int)) extends Atom with LValue {
}
object Ident extends ParserBridgePos1[String, Ident]


// Type nodes
trait TypeNode extends Position {
  var isParam : Boolean = false;
  val size = 0;
}

sealed trait BaseTypeNode extends TypeNode with PairElemTypeNode
case class IntTypeNode()(val pos: (Int, Int)) extends BaseTypeNode {
  override def toString: String = "integer"
  override val size: Int = 8
}
object IntTypeNode extends ParserBridgePos0[BaseTypeNode]

case class BoolTypeNode()(val pos: (Int, Int)) extends BaseTypeNode {
  override def toString: String = "bool"

  override val size: Int = 8

}
object BoolTypeNode extends ParserBridgePos0[BaseTypeNode]

case class CharTypeNode()(val pos: (Int, Int)) extends BaseTypeNode {
  override def toString: String = "char"

  override val size: Int = 8
}
object CharTypeNode extends ParserBridgePos0[BaseTypeNode]

case class StringTypeNode()(val pos: (Int, Int)) extends BaseTypeNode {
  override def toString: String = "string"

  override val size: Int = 8
}
object StringTypeNode extends ParserBridgePos0[BaseTypeNode]


case class ArrayTypeNode(elementType: TypeNode)(val pos: (Int, Int)) extends PairElemTypeNode {

  override def toString: String = s"$elementType[]"
  var length: Int = 0
  override val size: Int = 8 

}
object ArrayTypeNode extends ParserBridgePos1[TypeNode, ArrayTypeNode]

sealed trait PairElemTypeNode extends TypeNode 


case class ErrorTypeNode()(val pos: (Int, Int)) extends TypeNode with PairElemTypeNode 
object ErrorTypeNode extends ParserBridgePos0[ErrorTypeNode]


case class PairTypeNode(fst: PairElemTypeNode, snd: PairElemTypeNode)(val pos: (Int, Int)) extends TypeNode with LValue with PairElemTypeNode {
  override def toString: String = s"pair(${fst.toString}, ${snd.toString})"

}
object PairTypeNode extends ParserBridgePos2[PairElemTypeNode, PairElemTypeNode, PairTypeNode]
