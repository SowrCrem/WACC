package wacc
import parsley.{Parsley, Result}
import parsley.expr.chain

import parsley.errors.ErrorBuilder
import parsley.combinator
import lexer.implicits.implicitSymbol
import lexer.{integer, fully, char, implicits, ident, string}

object parser {
    def parse(input: String): Result[String, Node] = parser.parse(input)
    private val parser = fully(program)

    private lazy val program: Parsley[Node] = expr
    
    private val add = (x: BigInt, y: BigInt) => x + y
    private val sub = (x: BigInt, y: BigInt) => x - y

    private lazy val expr: Parsley[ExprNode] = atoms

    lazy val atoms: Parsley[Atom] = {
        val int = integer.map(n => {if (n.isValidInt) IntLiterNode(n.toInt) else ErrorNode("Integer too large")})
        val bool = ("true" as BoolLiterNode(true)) | ("false" as BoolLiterNode(false))
        val char = ("'" ~> lexer.char <~ "'") map CharLiterNode
        val string = lexer.string map StringLiterNode
        val ident = lexer.ident map IdentNode
        val brackets = "(" ~> expr <~ ")" map BracketsNode
        val pair = "null" as PairLiterNode()
        
        int | bool | char | string | ident | pair
    }

    
}