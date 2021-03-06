package datamining.visitors;
import java.io.File;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

import datamining.patterns.ExceptionFinder;

public class VisitorOfThrowsStatement extends ASTVisitor {
	public static File fl;
	public static int numberOfThrowsGenericAP;
	public static int numberOfThrowsKitchenSinkAP;

	public static File getF() {
		return fl;
	}
	
	private ASTNode findParentMethodDeclaration(ASTNode node) {
		if(node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			return node.getParent();
		} else {
			return findParentMethodDeclaration(node.getParent());
		}
	}

	public static void setF(File f) {
		VisitorOfThrowsStatement.fl = f;
	}

	public static int getNumOfThrowsGenericAP() {
		return numberOfThrowsGenericAP;
	}

	public static void setNumOfThrowsGenericAP(int numOfThrowsGenericAP) {
		VisitorOfThrowsStatement.numberOfThrowsGenericAP = numOfThrowsGenericAP;
	}

	public static int getNumOfThrowsKitchenSinkAP() {
		return numberOfThrowsKitchenSinkAP;
	}

	public static void setNumOfThrowsKitchenSinkAP(int numOfThrowsKitchenSinkAP) {
		VisitorOfThrowsStatement.numberOfThrowsKitchenSinkAP = numOfThrowsKitchenSinkAP;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		findThrowsGeneric(node);
		findThrowsKitchenSink(node);
		return super.visit(node);
	}

	private MethodDeclaration findMethodForThrow(ThrowStatement node) {
		return (MethodDeclaration) findParentMethodDeclaration(node);
	}
	
	public void findThrowsKitchenSink(MethodDeclaration node) {
		List<Name> l = node.thrownExceptions();
		int lssize = l.size();
		if(lssize>1)
		{
			numberOfThrowsKitchenSinkAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Throws Kitchen Sink***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + fl.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n Method : " + node);
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}
	public void findThrowsGeneric(MethodDeclaration node) {
		boolean throwsGenericeFlag = false;
		List<Name> l = node.thrownExceptions();
		int lssize = l.size();
		for(int i=0;i<lssize;i++){
			Expression e = (Expression)l.get(i);
			if(e.toString().trim().equals(new String("Exception"))){
				throwsGenericeFlag=true;
			}
		}
		if(throwsGenericeFlag)
		{
			numberOfThrowsGenericAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Throws Generic***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + fl.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n Method  : " + node);
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}

	}



	

}
