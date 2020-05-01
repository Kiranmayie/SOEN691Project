package datamining.visitors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

public class VisitorOfTheBlock extends ASTVisitor {

	private List<Statement> statements = new LinkedList<>();

	@Override
	public boolean visit(Block node) {
		statements = node.statements();
		return super.visit(node);
	}

	public List getStatements() {
		return statements;
	}

}
