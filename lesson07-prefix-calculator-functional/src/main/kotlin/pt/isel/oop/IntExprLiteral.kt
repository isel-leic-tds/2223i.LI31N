package pt.isel.oop

class IntExprLiteral(private val nr : Int) : IntExpr {
    override fun eval(): Int {
        return nr
    }
}
