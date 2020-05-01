package datamining.visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import datamining.patterns.ExceptionFinder;
import datamining.visitors.Node;

public class MethodInvocationVisitor extends ASTVisitor{
	Set<MethodDeclaration> declarationSuspect = new HashSet<MethodDeclaration>();
	HashSet<MethodInvocation> invocationSuspect = new HashSet<MethodInvocation>();
	List<String> namesOfException = new LinkedList<>();
	List<Class>	namesOfClass = new LinkedList<>();
	List<String> nameOfFunction = new LinkedList<>();
	
	public MethodInvocationVisitor(Set<MethodDeclaration> suspectDeclarations) {
		this.declarationSuspect = suspectDeclarations;
	}
	
	public HashSet<MethodInvocation> getSuspectInvocations() {
		return invocationSuspect;
	}
	
	public List<String> getExceptionNames() {
		return namesOfException;
	}

	public List<String> getFunctionName() {
		return nameOfFunction;
	}
	
	public List<Class> getClassNames() {
		return namesOfClass;
	}
	
	
	
	
	
}
