package pt.isel.fp

sealed class IntExpr { }
class IntExprLiteral(val nr : Int) : IntExpr() {}
class IntExprOperator(
    val opr : Operator,
    val a : IntExpr,
    val b : IntExpr
) : IntExpr()

fun IntExpr.eval(): Int {
    return when(this) {
        is IntExprLiteral -> nr
        is IntExprOperator -> opr.eval(a.eval(), b.eval())

    }
}
