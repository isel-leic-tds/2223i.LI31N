package pt.isel

class IntExprLiteral(private val nr : Int) : IntExpr {
    override fun eval(): Int {
        return nr
    }
}
