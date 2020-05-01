package datamining.visitors;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.UnionType;

public class ActionsHandler extends ASTVisitor {
	public boolean actionAbort = false;
	public boolean actionContinue = false;
	public boolean actionDefault = false;
	public boolean actionEmpty = false;
	public boolean actionLog = false;
	public boolean actionMethod = false;
	public boolean actionNestedtry = false;
	public boolean actionReturn = false;
	public boolean actionThrowCurrent = false;
	public boolean actionThrowNew = false;
	public boolean actionThrowWrap = false;
	public boolean actionTODO = false;

	@Override
	public boolean visit(TryStatement node) {
		this.actionNestedtry = true;
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {

		List<Statement> temp = node.getBody().statements();

		if (temp.size() == 0) {
			this.actionEmpty = true;
		}
		for (Statement i : temp) {
			if (i.toString().contains("TODO") || i.toString().contains("FIXME")) {
				this.actionTODO = true;
			}

		}
		return super.visit(node);
	}

	public boolean isActionDefault() {
		return actionDefault;
	}

	public boolean isActionEmpty() {
		return actionEmpty;
	}

	public boolean isActionAbort() {
		return actionAbort;
	}

	public boolean isActionContinue() {
		return actionContinue;
	}

	public boolean isActionLog() {
		return actionLog;
	}

	public boolean isActionMethod() {
		return actionMethod;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		Expression expression = node.getExpression();
		if (expression instanceof ClassInstanceCreation) {
			List<Expression> i = ((ClassInstanceCreation) expression).arguments();

			boolean flag = false;
			for (Expression ex : i) {
				if (ex.getAST().getClass().toString().contains("Exception")) {
					this.actionThrowWrap = true;
					flag = true;
					break;
				}
			}
			if (flag == false) {
				this.actionThrowNew = true;
			}
		} else {
			this.actionThrowCurrent = true;
		}

		return super.visit(node);
	}

	public boolean isActionThrowCurrent() {
		return actionThrowCurrent;
	}

	public boolean isActionThrowNew() {
		return actionThrowNew;
	}

	public boolean isActionNestedtry() {
		return actionNestedtry;
	}

	public boolean isActionReturn() {
		return actionReturn;
	}

	public boolean isActionThrowWrap() {
		return actionThrowWrap;
	}

	public boolean isActionTODO() {
		return actionTODO;
	}

	@Override
	public boolean visit(ContinueStatement node) {

		this.actionContinue = true;
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {

		this.actionReturn = true;
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {

		String method = node.toString();

		if (method.contains("System.exit"))
			this.actionAbort = true;
		else if (method.contains("log") || method.contains("debug") || method.contains("info")
				|| method.contains("warning")) {
			this.actionLog = true;
		} else {
			this.actionMethod = true;
		}

		return super.visit(node);
	}

}
