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
	
	
	@Override
	public boolean visit(MethodInvocation node) {
		if (node == null) {
			return super.visit(node);
		}
		IMethodBinding declarationLinked;
		
		try {
		declarationLinked = node.resolveMethodBinding().getMethodDeclaration();
		}
		catch (Exception e) {
			return super.visit(node);
		}
		
				ITypeBinding[] typesException = declarationLinked.getExceptionTypes();
				for (int i = 0; i < typesException.length; i++) {
					namesOfException.add(typesException[i].getBinaryName());
					namesOfClass.add(typesException[i].getClass());
				}
				nameOfFunction.add(node.getName().toString());
				
		
		for(MethodDeclaration suspectDeclaration: declarationSuspect) {
			
			if(suspectDeclaration.resolveBinding().getMethodDeclaration().isEqualTo(declarationLinked)) {
				invocationSuspect.add(node);
			}
			
		}
		
		ArrayList<String> exceptionList = new ArrayList<String>();
		ITypeBinding iTypeBinding ;
		IMethodBinding iMethodBinding;
		IPackageBinding iPackageBinding;
		
		try {
	
			iTypeBinding = node.resolveMethodBinding().getDeclaringClass();
			 iMethodBinding = node.resolveMethodBinding().getMethodDeclaration();
			 iPackageBinding =	node.resolveMethodBinding().getMethodDeclaration().getDeclaringClass().getPackage();
			
		}
		catch (Exception e) {
			return super.visit(node);
		}
		String classCalled = iTypeBinding.getName();
		String methodCalled = iMethodBinding.toString();
		String packagesCalled = iPackageBinding.getName();
		Node calledNode = new Node(methodCalled, classCalled, packagesCalled);
		ASTNode astNode = node;
		MethodDeclaration methodDeclarationCall = null ;
		while(true) {
			if(astNode instanceof MethodDeclaration) {
				methodDeclarationCall = (MethodDeclaration)astNode;
				break;
			}
			else if(astNode instanceof TypeDeclaration) {
				return super.visit(node);//to check
			}		
			try {
				astNode = astNode.getParent();
			}
			catch (Exception ex) {
				return super.visit(node);//to check
			}
			

		}
		
		
		IMethodBinding bindingForCall = methodDeclarationCall.resolveBinding();
		ITypeBinding itbCall = methodDeclarationCall.resolveBinding().getDeclaringClass();
		IPackageBinding ipbCall = methodDeclarationCall.resolveBinding().getDeclaringClass().getPackage();
		
		String classNameCall = itbCall.getName();
		String methodNameCall = bindingForCall.toString();
		String packageNameCall = ipbCall.getName();
		Node nodeCall = new Node(methodNameCall, classNameCall, packageNameCall);

		
		if(ExceptionFinder.CallGraph.containsKey(nodeCall)) {
			Set<Node> adjCall = new HashSet<Node>();
			adjCall = ExceptionFinder.CallGraph.get(nodeCall);
			adjCall.add(calledNode);
			ExceptionFinder.CallGraph.put(nodeCall, adjCall);
			
		}
		else {
			Set<Node> adjCall = new HashSet<Node>();
			adjCall.add(calledNode);
			ExceptionFinder.CallGraph.put(nodeCall, adjCall);
		}
		

		if(!ExceptionFinder.CallGraph.containsKey(calledNode)) {
			Set<Node> adjCall = new HashSet<Node>();
			ExceptionFinder.CallGraph.put(calledNode, adjCall);
			
		}

		Set<String> setExceptionCall = new HashSet<String>();
		Set<String> setExceptionCalled = new HashSet<String>();
		try {
			setExceptionCall.addAll(RuntimeandNonRuntimeExceptionCallers.FindNonRuntimeExceptions(bindingForCall));
			setExceptionCall.addAll(RuntimeandNonRuntimeExceptionCallers.FindRuntimeExceptions(bindingForCall));
			
			setExceptionCalled.addAll(RuntimeandNonRuntimeExceptionCallers.FindNonRuntimeExceptions(iMethodBinding));
			setExceptionCalled.addAll(RuntimeandNonRuntimeExceptionCallers.FindRuntimeExceptions(iMethodBinding));
			
		}
		catch(JavaModelException ex) {
			
		}

		if(ExceptionFinder.ExceptionMap.containsKey(calledNode)) {
			Set<String> tempSet = new HashSet<String>();
			tempSet = ExceptionFinder.ExceptionMap.get(calledNode);
			tempSet.addAll(setExceptionCalled);
			ExceptionFinder.ExceptionMap.put(calledNode, tempSet);
		}
		else {
			ExceptionFinder.ExceptionMap.put(calledNode, setExceptionCalled);
		}

		if(ExceptionFinder.ExceptionMap.containsKey(nodeCall)) {
			Set<String> tempSet = new HashSet<String>();
			tempSet = ExceptionFinder.ExceptionMap.get(nodeCall);
			tempSet.addAll(setExceptionCall);
			ExceptionFinder.ExceptionMap.put(nodeCall, tempSet);
		}
		else {

			ExceptionFinder.ExceptionMap.put(nodeCall, setExceptionCall);
			
		}
		
		
		
		Set<Node> calledNodeSet = new HashSet<Node>();
		
		calledNodeSet = ExceptionFinder.CallGraph.get(calledNode);
		if(calledNodeSet == null) {
			calledNodeSet = new HashSet<Node>();
			calledNodeSet.add(calledNode);
		}
		else {
			calledNodeSet.add(calledNode);
		}

		
		
		Set<String> exceptionSet = new HashSet<String>();
		for(Node temp:calledNodeSet) {
			if(ExceptionFinder.ExceptionMap.get(temp)!=null) {
				exceptionSet.addAll(ExceptionFinder.ExceptionMap.get(temp));
			}
		}

		TryStatementVisitor.exceptionSetfromTry.addAll(exceptionSet);
		
		
		return super.visit(node);
	}
	
	
	
}
