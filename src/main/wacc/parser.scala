package wacc
import parsley.{Parsley, Result}
import parsley.expr.chain

import parsley.errors.ErrorBuilder
import parsley.combinator
import lexer.implicits.implicitSymbol
import lexer.{integer, fully, char, implicits, ident, string}

object parser {
    // def parse(input: String): Result[String, Node] = parser.parse(input)
    val parser = fully(program)

    private lazy val program: Parsley[Node] = expr
    
    private val add = (x: BigInt, y: BigInt) => x + y
    private val sub = (x: BigInt, y: BigInt) => x - y

    private lazy val expr: Parsley[Expr] = atoms

    lazy val atoms: Parsley[Atom] = {
        val int = integer.map(n => {if (n.isValidInt) IntLiter(n.toInt) else Error("Integer too large")})
        val bool = ("true" as BoolLiter(true)) | ("false" as BoolLiter(false))
        val char = lexer.char map CharLiter
        val string = lexer.string map StringLiter
        val ident = lexer.ident map Ident
        val brackets = "(" ~> expr <~ ")" map Brackets
        val pair = "null" as PairLiter()
        
        int | bool | char | string | ident | brackets | pair
    }

    
}