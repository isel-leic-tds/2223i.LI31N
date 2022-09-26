package pt.isel.oop

object CalculatorOop {
    fun parsePrefix(input: String): Int {
        val tokens = input.split(" ").iterator()
        val expr = parse(tokens)
        return expr.eval()
    }

    private fun parse(tokens : Iterator<String>) : IntExpr {
        val token = tokens.next()
        val nr: Int? = token.toIntOrNull()
        return if (nr != null) IntExprLiteral(nr)
        else IntExprOperator(Operator.parse(token[0]), parse(tokens), parse(tokens))
    }
}
