package datamining.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WhileStatement;

import datamining.patterns.ExceptionFinder;
import datamining.visitors.Node;

import org.eclipse.jdt.core.dom.IMethodBinding;



public class TryStatementVisitor extends ASTVisitor {
	

	List<TryCatchInfo> tryCatchInfo = new LinkedList<>();
	public static File fl;
	public static int numOfTryBlocks;
	public static int numofTryLOC;
	public static int numofTryBlockSLOC;
	
	public static int declarationTry;
	public static int conditionTry;
	public static int loopTry;
	public static int EHTry;
	public static int otherTry;
	
	
	public static int actionAbort=0;
	public static int actionContinue=0;
	public static int actionDefault=0;
	public static int actionEmpty=0;
	public static int actionLog=0;
	public static int actionMethod=0;
	public static int actionNestedTry=0;
	public static int actionReturn=0;
	public static int actionThrowCurrent=0;
	public static int actionThrowNew=0;
	public static int actionThrowWrap=0;
	public static int actionTODO=0;
	ActionEntityHandler handlingactionEntity;
	
	public static HashSet<String> exceptionSetfromTry;
	public HashSet<String> getExceptionSetfromTry() {
		return exceptionSetfromTry;
	}

	public void setExceptionSetfromTry(HashSet<String> exceptionSetfromTry) {
		this.exceptionSetfromTry = exceptionSetfromTry;
	}
	public static Set<String> wholeExceptionsInCatch = new HashSet<String>();
	

	public TryStatementVisitor() {
		exceptionSetfromTry = new HashSet<String>();
	}

	public ActionEntityHandler getHandlingActionEntity()
	{
		return this.handlingactionEntity;
	}
	
	public static void setWholeExceptionsInCatch(Set<String> wholeExceptionsInCatch) {
		TryStatementVisitor.wholeExceptionsInCatch = wholeExceptionsInCatch;
	}

	public  static Set<String> getWholeExceptionsInCatch() {
		return wholeExceptionsInCatch;
	}
	
	public void setFile(File f){
		this.fl = f;
	}
	
	public static int getNumOfTryBlocks() {
		return numOfTryBlocks;
	}

	public static void setNumOfTryBlocks(int numOfTryBlocks) {
		TryStatementVisitor.numOfTryBlocks = numOfTryBlocks;
	}

	public static int getNumofTryLOC() {
		return numofTryLOC;
	}

	public static void setNumofTryLOC(int numofTryLOC) {
		TryStatementVisitor.numofTryLOC = numofTryLOC;
	}

	public static int getNumofTryBlockSLOC() {
		return numofTryBlockSLOC;
	}

	public static void setNumofTryBlockSLOC(int numofTryBlockSLOC) {
		TryStatementVisitor.numofTryBlockSLOC = numofTryBlockSLOC;
	}

	public static int getDeclarationTry() {
		return declarationTry;
	}

	public static void setDeclarationTry(int declarationTry) {
		TryStatementVisitor.declarationTry = declarationTry;
	}

	public static int getConditionTry() {
		return conditionTry;
	}

	public static void setConditionTry(int conditionTry) {
		TryStatementVisitor.conditionTry = conditionTry;
	}

	public static int getLoopTry() {
		return loopTry;
	}

	public static void setLoopTry(int loopTry) {
		TryStatementVisitor.loopTry = loopTry;
	}

	public static int getEHTry() {
		return EHTry;
	}

	public static void setEHTry(int eHTry) {
		TryStatementVisitor.EHTry = eHTry;
	}

	public static int getOtherTry() {
		return otherTry;
	}

	public static void setOtherTry(int otherTry) {
		TryStatementVisitor.otherTry = otherTry;
	}

	@Override
	public boolean visit(TryStatement node) {
		
		TryCatchInfo tryCatchInfoObject = new TryCatchInfo();
		tryCatchInfoObject = getExceptionNamesFromCatchBlock(node);
		MethodInvocationVisitor visitor = getMethodInvocation(node);
		
		tryCatchInfoObject.setExceptionThrownByMethod(visitor.getExceptionNames());
		tryCatchInfoObject.setBinaryNameforThrowExceptions(visitor.getClassNames());
		tryCatchInfoObject.setFuncName(visitor.getFunctionName());
		tryCatchInfoObject.setNumberOfInvokedMehtods(visitor.getFunctionName().size());
		tryCatchInfo.add(tryCatchInfoObject);
		numOfTryBlocks++;
		numofTryLOC = numofTryLOC + node.getBody().statements().size();
		numofTryBlockSLOC = numofTryBlockSLOC + findTryBlockSLOC(node.getBody().statements());
		
		//Try Scope metric code
		ActionsHandler actions=new ActionsHandler();
		
		List<CatchClause> list= node.catchClauses();
		for(CatchClause catchclause: list)
		{
			catchclause.accept(actions);
		}
		
		if(actions.isActionAbort())
			this.actionAbort++;
		if(actions.isActionContinue())
			this.actionContinue++;
		if(actions.isActionDefault())
			this.actionDefault++;
		if(actions.isActionEmpty())
			this.actionEmpty++;
		if(actions.isActionLog())
			this.actionLog++;
		if(actions.isActionNestedtry())
			this.actionNestedTry++;
		if(actions.isActionReturn())
			this.actionReturn++;
		if(actions.isActionMethod())
			this.actionMethod++;
		if(actions.isActionThrowCurrent())
			this.actionThrowCurrent++;
		if(actions.isActionThrowNew())
			this.actionThrowNew++;
		if(actions.isActionThrowWrap())
			this.actionThrowWrap++;
		if(actions.isActionTODO())
			this.actionTODO++;
		
		
		ASTNode tryscope= node.getParent().getParent();
		if(tryscope instanceof MethodDeclaration) {
			this.declarationTry++;
		}
		else if( tryscope instanceof IfStatement||tryscope instanceof SwitchStatement) {
			this.conditionTry++;
		}
		else if( tryscope instanceof ForStatement||tryscope instanceof WhileStatement) {
			this.loopTry++;
		}
		else if(tryscope instanceof CatchClause) {
			this.EHTry++;
		}else {
			this.otherTry++;
		}
		
		return super.visit(node);
	}
	
	public static void setActionAbort(int actionAbort) {
		TryStatementVisitor.actionAbort=actionAbort;
	}

	public static void setActionContinue(int actionContinue) {
		TryStatementVisitor.actionContinue=actionContinue;
	}

	public static void setActionDefault(int actionDefault) {
		TryStatementVisitor.actionDefault=actionDefault;
	}

	public static void setActionEmpty(int actionEmpty) {
		TryStatementVisitor.actionEmpty=actionEmpty;
	}

	public static void setActionLog(int actionLog) {
		TryStatementVisitor.actionLog=actionLog;
	}

	public static void setActionMethod(int actionMethod) {
		TryStatementVisitor.actionMethod=actionMethod;
	}

	public static void setActionNestedTry(int actionNestedTry) {
		TryStatementVisitor.actionNestedTry=actionNestedTry;
	}

	public static void setActionReturn(int actionReturn) {
		TryStatementVisitor.actionReturn=actionReturn;
	}

	public static void setActionThrowCurrent(int actionThrowCurrent) {
		TryStatementVisitor.actionThrowCurrent=actionThrowCurrent;
	}

	public static void setActionThrowNew(int actionThrowNew) {
		TryStatementVisitor.actionThrowNew=actionThrowNew;
	}

	public static void setActionThrowWrap(int actionThrowWrap) {
		TryStatementVisitor.actionThrowWrap=actionThrowWrap;
	}

	public static void setActionTODO(int actionTODO) {
		TryStatementVisitor.actionTODO=actionTODO;
	}

	public static int getActionAbort() {
		return actionAbort;
	}

	public static int getActionContinue() {
		return actionContinue;
	}

	public static int getActionDefault() {
		return actionDefault;
	}

	public static int getActionEmpty() {
		return actionEmpty;
	}

	public static int getActionLog() {
		return actionLog;
	}

	public static int getActionMethod() {
		return actionMethod;
	}

	public static int getActionNestedTry() {
		return actionNestedTry;
	}

	public static int getActionReturn() {
		return actionReturn;
	}

	public static int getActionThrowCurrent() {
		return actionThrowCurrent;
	}

	public static int getActionThrowNew() {
		return actionThrowNew;
	}

	public static int getActionThrowWrap() {
		return actionThrowWrap;
	}

	public static int getActionTODO() {
		return actionTODO;
	}


	
	/**
	 * get all exception names
	 * 
	 * @param node
	 * @return
	 */
	private static MethodInvocationVisitor getMethodInvocation(TryStatement node) {
		VisitorOfTheBlock blockVisitor = new VisitorOfTheBlock();
		
		node.getBody().accept(blockVisitor);

		
		return callMethodInvocationForTryBlock(blockVisitor);
	}
	
	

	/**
	 * Method invocation for call to method in each line in block
	 * 
	 * @param blockVisitor
	 * @return
	 */
	private static MethodInvocationVisitor callMethodInvocationForTryBlock(VisitorOfTheBlock blockVisitor) {
		HashSet<MethodDeclaration> suspectException = new HashSet<MethodDeclaration>();
		MethodInvocationVisitor methodInvocation = new MethodInvocationVisitor(suspectException);

		for (Iterator iterator = blockVisitor.getStatements().iterator(); iterator.hasNext();) {
			Statement statement = (Statement) iterator.next();
			if (statement instanceof ExpressionStatement) {
				ExpressionStatement expressionStatement = (ExpressionStatement) statement;
				expressionStatement.getExpression().accept(methodInvocation);
			}

		}
		return methodInvocation;
	}


	
	
	
	/**
	 * 
	 * Get exception from catch block
	 * 
	 * @param node
	 * @return
	 */
	private static TryCatchInfo getExceptionNamesFromCatchBlock(TryStatement node) {
		TryCatchInfo info = new TryCatchInfo();
		for (Iterator<CatchClause> iterator = node.catchClauses().iterator(); iterator.hasNext();) {
			CatchClause catchClause = iterator.next();
			info.addCatchBlockException(catchClause.getException().getType().toString());
			info.setBody(node.toString());
			info.setFileName(fl.getAbsolutePath());
			
		}
		return info;
	}

	public List<TryCatchInfo> getTryCatchInfo() {
		return tryCatchInfo;
	}

	public void setTryCatchInfo(List<TryCatchInfo> tryCatchInfo) {
		this.tryCatchInfo = tryCatchInfo;
	}

	
	
	public static int findTryBlockSLOC(List<Statement> statements) {
		int statementListSize = statements.size();
		int x =0;
		for(int i=0;i<statementListSize;i++){
			Statement s = statements.get(i);
			String content = s.toString();
			if( content.contains("//") || (content.contains("/*") && content.contains("*/")) )
				continue;
			else
				x++;
		}
		return x;
	}
	
}
