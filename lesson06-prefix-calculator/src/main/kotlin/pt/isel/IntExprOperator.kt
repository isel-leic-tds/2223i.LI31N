package pt.isel

import java.lang.IllegalArgumentException

class IntExprOperator(
    private val opr : Operator,
    private val a : IntExpr,
    private val b : IntExpr
) : IntExpr{
    override fun eval(): Int {
        return opr.eval(a.eval(), b.eval())
    }
}
