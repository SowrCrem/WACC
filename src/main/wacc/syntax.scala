package wacc
import parsley.generic

sealed trait Node

// Program (Extending Node)

case class Program(funcList: List[Func], stat: Stat) extends Node
object Program extends generic.ParserBridge2[List[Func], Stat, Program]


case class Func(typeNode: Type, ident: Ident, paramList: ParamList, stat: Stat) extends Node
object Func extends generic.ParserBridge4[Type, Ident, ParamList, Stat, Func]

// Param-List (Extending Node)

case class ParamList(paramList: List[Param]) extends Node
object ParamList extends generic.ParserBridge1[List[Param], ParamList]



// Param (Extending Node)

case class Param(typeNode: Type, ident: Expr) extends Node
object Param extends generic.ParserBridge2[Type, Expr, Param]

// Statements (Extending Node)
sealed trait Stat extends Node

case class Skip() extends Stat
case class IdentAsgn (typeNode: Type, ident: Expr, expr: Node) extends Stat
object IdentAsgn extends generic.ParserBridge3[Type, Expr, Node, Stat]
case class AsgnEq(lhs: Node, rhs: Node) extends Stat
object AsgnEq extends generic.ParserBridge2[Node, Node, Stat]
case class Read(lhs: LValue) extends Stat
object Read extends generic.ParserBridge1[LValue, Stat]
case class Free(expr: Expr) extends Stat
object Free extends generic.ParserBridge1[Expr, Stat]
case class Return(expr: Expr) extends Stat
object Return extends generic.ParserBridge1[Expr, Stat]
case class Exit(expr: Expr) extends Stat
object Exit extends generic.ParserBridge1[Expr, Stat]
case class Print(expr: Expr) extends Stat
object Print extends generic.ParserBridge1[Expr, Stat]
case class Println(expr: Expr) extends Stat
object Println extends generic.ParserBridge1[Expr, Stat]
case class If(expr: Expr, stat1: Stat, stat2: Stat) extends Stat
object If extends generic.ParserBridge3[Expr, Stat, Stat, Stat]
case class While(expr: Expr, stat: Stat) extends Stat
object While extends generic.ParserBridge2[Expr, Stat, Stat]
case class BeginEnd(stat: Stat) extends Stat
object BeginEnd extends generic.ParserBridge1[Stat, Stat]
case class StatJoin (statList: List[Stat]) extends Stat
object StatJoin extends generic.ParserBridge1[List[Stat], Stat]



// RValue 

sealed trait RValue 

// Expr (Extending Node)

sealed trait Expr extends Node with RValue


case class NewPair(fst: Expr, snd: Expr) extends Expr
object NewPair extends generic.ParserBridge2[Expr, Expr, Expr]

case class ArgList (argList: List[Expr]) extends Node
object ArgList extends generic.ParserBridge1[List[Expr], ArgList]

case class Call(ident: Ident, list: Node) extends Stat
object Call extends generic.ParserBridge2[Ident, Node, Stat]

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
case class Null() extends Atom with LValue with PairElemType
case class Ident(value: String) extends Atom with LValue
case class Error(value: String) extends Atom 

object IntLiter extends generic.ParserBridge1[Int, Atom]
object BoolLiter extends generic.ParserBridge1[Boolean, Atom]
object CharLiter extends generic.ParserBridge1[Char, Atom]
object StringLiter extends generic.ParserBridge1[String, Atom]
object Brackets extends generic.ParserBridge1[Expr, Atom]
object Ident extends generic.ParserBridge1[String, Atom]


// Types 
sealed trait Type

sealed trait BaseType extends Type with PairElemType
case class IntType() extends BaseType 
case class BoolType() extends BaseType 
case class CharType() extends BaseType 
case class StringType() extends BaseType 

case class ArrayType(elementType: Type) extends PairElemType

sealed trait PairElemType extends Type

// sealed trait PairBaseType extends PairElemType
// case class PairBaseIntType() extends PairBaseType
// case class PairBaseBoolType() extends PairBaseType
// case class PairBaseCharType() extends PairBaseType
// case class PairBaseStringType() extends PairBaseType


case class PairType(fst: PairElemType, snd: PairElemType) extends Type with LValue
object PairType extends generic.ParserBridge2[PairElemType, PairElemType, Type]
