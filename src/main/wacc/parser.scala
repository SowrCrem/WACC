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
        val int     : Parsley[Atom] = integer.map(n => {if (n.isValidInt) IntLiter(n.toInt) else Error("Integer too large")})
        val bool    : Parsley[Atom] = ("true" as BoolLiter(true)) | ("false" as BoolLiter(false))
        val char    : Parsley[Atom] = CharLiter(lexer.char)
        val string  : Parsley[Atom] = StringLiter(lexer.string)
        val ident   : Parsley[Atom] = Ident(lexer.ident)
        val brackets: Parsley[Atom] = Brackets("(" ~> expr <~ ")")
        val pair    : Parsley[Atom] = "null" as Null()
        
        int | bool | char | string | ident | brackets | pair
    }

    
}