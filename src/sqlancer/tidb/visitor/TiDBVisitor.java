package sqlancer.tidb.visitor;

import sqlancer.tidb.ast.TiDBColumnReference;
import sqlancer.tidb.ast.TiDBConstant;
import sqlancer.tidb.ast.TiDBExpression;
import sqlancer.tidb.ast.TiDBFunctionCall;
import sqlancer.tidb.ast.TiDBJoin;
import sqlancer.tidb.ast.TiDBSelect;
import sqlancer.tidb.ast.TiDBTableReference;

public interface TiDBVisitor {

	public default void visit(TiDBExpression expr) {
		if (expr instanceof TiDBConstant) {
			visit((TiDBConstant) expr);
		} else if (expr instanceof TiDBColumnReference) {
			visit((TiDBColumnReference) expr);
		} else if (expr instanceof TiDBSelect) {
			visit((TiDBSelect) expr);
		} else if (expr instanceof TiDBTableReference) {
			visit((TiDBTableReference) expr);
		} else if (expr instanceof TiDBFunctionCall) {
			visit((TiDBFunctionCall) expr);
		} else if (expr instanceof TiDBJoin) {
			visit((TiDBJoin) expr);
		} else {
			throw new AssertionError(expr.getClass());
		}
	}

	void visit(TiDBFunctionCall call);

	void visit(TiDBConstant expr);

	void visit(TiDBColumnReference expr);

	void visit(TiDBTableReference expr);

	void visit(TiDBSelect select);
	
	void visit(TiDBJoin join);

	public static String asString(TiDBExpression expr) {
		TiDBToStringVisitor v = new TiDBToStringVisitor();
		v.visit(expr);
		return v.getString();
	}

}