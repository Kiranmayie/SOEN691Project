package datamining.visitors;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;


public class MethodDeclarationVisitor extends ASTVisitor{
	HashSet<MethodDeclaration> suspectMethodsCaller = new HashSet<>();
	HashSet<MethodDeclaration> declarationSuspect = new HashSet<MethodDeclaration>();
 
	public MethodDeclarationVisitor(Set<MethodDeclaration> suspectDeclarations) {
		this.declarationSuspect.addAll(suspectDeclarations);
	}
	
	public HashSet<MethodDeclaration> callSuspectMethodsGetter() {
		return suspectMethodsCaller;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor(declarationSuspect);
		node.accept(methodInvocationVisitor);
		
		if(!methodInvocationVisitor.getSuspectInvocations().isEmpty()) {
			suspectMethodsCaller.add(node);
			declarationSuspect.add(node);
		}
		
		return super.visit(node);
	}
	
}
