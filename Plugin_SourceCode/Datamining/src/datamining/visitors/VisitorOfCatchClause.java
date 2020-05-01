package datamining.visitors;

import org.eclipse.jdt.core.dom.*;

import datamining.patterns.ExceptionFinder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VisitorOfCatchClause extends ASTVisitor{

	public static File f;

	public static int numOfCatchBlocks;
	public static int numofCatchBlockLOC;
	public static int numofCatchBlockSLOC;

	public static int numOfCatchAndReturnNullAP;
	public static int numOfDestructiveWrapAP;
	public static int numOfCatchAndDoNothingAP;
	public static int numOfCatchGenericAP;
	public static int numOfDummyHandlerAP;
	public static int numOfIgnoreInterruptedExceptionAP;
	public static int numOfIncompleteImplementationAP;
	public static int numOfLogAndReturnNullAP;
	public static int numOfLogAndThrowAP;
	public static int numOfMultilineLogAP;
	public static int numOfNestedTryAP;
	public static int numOfRelyingOnGetCauseAP;
	public static int numOfThrowWithinFinallyAP;
	


	@Override
	public boolean visit(CatchClause node) {

		numOfCatchBlocks++;
		numofCatchBlockLOC = numofCatchBlockLOC + node.getBody().statements().size();
		numofCatchBlockSLOC = numofCatchBlockSLOC + findCatchBlockSLOC(node.getBody().statements());

		findReturnNullCatches(node);
		findDestructiveWrapping(node);
		findCatchAndDoNothing(node);
		findCatchGeneric(node);
		findLogAPs(node);
		findIgnoringInterruptedException(node);
		findIncompleteImplementation(node);
		findMultiLineLog(node);
		findNestedTry(node);
		findRelyingOnGetCause(node);

		return super.visit(node);

	}
	
	public static File getF() {
		return f;
	}

	public static void setF(File f) {
		VisitorOfCatchClause.f = f;
	}

	public static int getNumOfCatchBlocks() {
		return numOfCatchBlocks;
	}

	public static void setNumOfCatchBlocks(int numOfCatchBlocks) {
		VisitorOfCatchClause.numOfCatchBlocks = numOfCatchBlocks;
	}

	public static int getNumofCatchBlockLOC() {
		return numofCatchBlockLOC;
	}

	public static void setNumofCatchBlockLOC(int numofCatchBlockLOC) {
		VisitorOfCatchClause.numofCatchBlockLOC = numofCatchBlockLOC;
	}

	public static int getNumofCatchBlockSLOC() {
		return numofCatchBlockSLOC;
	}

	public static void setNumofCatchBlockSLOC(int numofCatchBlockSLOC) {
		VisitorOfCatchClause.numofCatchBlockSLOC = numofCatchBlockSLOC;
	}

	public static int getNumOfCatchAndReturnNullAP() {
		return numOfCatchAndReturnNullAP;
	}

	public static void setNumOfCatchAndReturnNullAP(int numOfCatchAndReturnNullAP) {
		VisitorOfCatchClause.numOfCatchAndReturnNullAP = numOfCatchAndReturnNullAP;
	}

	public static int getNumOfDestructiveWrapAP() {
		return numOfDestructiveWrapAP;
	}

	public static void setNumOfDestructiveWrapAP(int numOfDestructiveWrapAP) {
		VisitorOfCatchClause.numOfDestructiveWrapAP = numOfDestructiveWrapAP;
	}

	public static int getNumOfCatchAndDoNothingAP() {
		return numOfCatchAndDoNothingAP;
	}

	public static void setNumOfCatchAndDoNothingAP(int numOfCatchAndDoNothingAP) {
		VisitorOfCatchClause.numOfCatchAndDoNothingAP = numOfCatchAndDoNothingAP;
	}

	public static int getNumOfCatchGenericAP() {
		return numOfCatchGenericAP;
	}

	public static void setNumOfCatchGenericAP(int numOfCatchGenericAP) {
		VisitorOfCatchClause.numOfCatchGenericAP = numOfCatchGenericAP;
	}

	public static int getNumOfDummyHandlerAP() {
		return numOfDummyHandlerAP;
	}

	public static void setNumOfDummyHandlerAP(int numOfDummyHandlerAP) {
		VisitorOfCatchClause.numOfDummyHandlerAP = numOfDummyHandlerAP;
	}

	public static int getNumOfIgnoreInterruptedExceptionAP() {
		return numOfIgnoreInterruptedExceptionAP;
	}

	public static void setNumOfIgnoreInterruptedExceptionAP(int numOfIgnoreInterruptedExceptionAP) {
		VisitorOfCatchClause.numOfIgnoreInterruptedExceptionAP = numOfIgnoreInterruptedExceptionAP;
	}

	public static int getNumOfIncompleteImplementationAP() {
		return numOfIncompleteImplementationAP;
	}

	public static void setNumOfIncompleteImplementationAP(int numOfIncompleteImplementationAP) {
		VisitorOfCatchClause.numOfIncompleteImplementationAP = numOfIncompleteImplementationAP;
	}

	public static int getNumOfLogAndReturnNullAP() {
		return numOfLogAndReturnNullAP;
	}

	public static void setNumOfLogAndReturnNullAP(int numOfLogAndThrowNullAP) {
		VisitorOfCatchClause.numOfLogAndReturnNullAP = numOfLogAndThrowNullAP;
	}

	public static int getNumOfLogAndThrowAP() {
		return numOfLogAndThrowAP;
	}

	public static void setNumOfLogAndThrowAP(int numOfLogAndThrowAP) {
		VisitorOfCatchClause.numOfLogAndThrowAP = numOfLogAndThrowAP;
	}

	public static int getNumOfMultilineLogAP() {
		return numOfMultilineLogAP;
	}

	public static void setNumOfMultilineLogAP(int numOfMultilineLogAP) {
		VisitorOfCatchClause.numOfMultilineLogAP = numOfMultilineLogAP;
	}

	public static int getNumOfNestedTryAP() {
		return numOfNestedTryAP;
	}

	public static void setNumOfNestedTryAP(int numOfNestedTryAP) {
		VisitorOfCatchClause.numOfNestedTryAP = numOfNestedTryAP;
	}

	public static int getNumOfRelyingOnGetCauseAP() {
		return numOfRelyingOnGetCauseAP;
	}

	public static void setNumOfRelyingOnGetCauseAP(int numOfRelyingOnGetCauseAP) {
		VisitorOfCatchClause.numOfRelyingOnGetCauseAP = numOfRelyingOnGetCauseAP;
	}

	public static int getNumOfThrowWithinFinallyAP() {
		return numOfThrowWithinFinallyAP;
	}

	public static void setNumOfThrowWithinFinallyAP(int numOfThrowWithinFinallyAP) {
		VisitorOfCatchClause.numOfThrowWithinFinallyAP = numOfThrowWithinFinallyAP;
	}


	public static int findCatchBlockSLOC(List<Statement> statements) {
		int statementListSize = statements.size();
		int sloc =0;
		for(int i=0;i<statementListSize;i++){
			Statement s = (Statement)statements.get(i);
			String content = s.toString();
			if( content.contains("//") || (content.contains("/*") && content.contains("*/")) )
				continue;
			else
				sloc++;
		}
		return sloc;
	}

	public void findReturnNullCatches(CatchClause node) {

		String body = node.getBody().toString();
		boolean returnNullOrNot = false;

		if((body.trim().contains("return null;")) || (body.trim().contains("return (null);"))) {
			returnNullOrNot = true;
		}

		if(returnNullOrNot) {
			numOfCatchAndReturnNullAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Return null***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findDestructiveWrapping(CatchClause node) {
		List<Statement> statements = node.getBody().statements();
		int statementListSize = statements.size();
		for(int i=0;i<statementListSize;i++){
			Statement s = (Statement)statements.get(i);
			String content = s.toString();
			if( content.contains("throw") && content.contains("new")&& content.contains("getMessage") ){
				SingleVariableDeclaration var = node.getException();
				String incoming = var.getType().toString();
				content =content.replaceAll("[\\W+]", " ");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(content.split(" ")));
				boolean flag= false;
				for ( String k: temp)
				{
					if(k.contentEquals(incoming))
					{
						flag=true;
						break;
					}
				}
				if(flag==false)
				{
					if(!temp.contains(incoming))
					{
						ExceptionFinder.stringbuilttxt.append("\n **************ANTI-PATTERN : Destructive Wrapping**************");
						ExceptionFinder.stringbuilttxt.append(" \n FILE NAME : " + f.getAbsolutePath());
						ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
						ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
						numOfDestructiveWrapAP++;
					}
				}
			}
		}
	}

	public void findCatchAndDoNothing(CatchClause node) {
		if(node.getBody().statements().isEmpty())
		{
			numOfCatchAndDoNothingAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Catch and Do Nothing ***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findCatchGeneric(CatchClause node) {
		String nodeType = node.getException().getType().toString();
		if( (nodeType.equalsIgnoreCase("Exception")) || (nodeType.equalsIgnoreCase("Throwable")) ){
			numOfCatchGenericAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Catch Generic ***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findLogAPs(CatchClause node) {

		String body = node.getBody().toString();
		boolean throwflag = false;
		boolean printstacktraceflag = false;
		boolean logflag = false;
		boolean returnnullflag = false;

		if(body.contains(new StringBuffer("printStackTrace"))){
			printstacktraceflag = true;
		}

		if((body.trim().contains("return null;")) || (body.trim().contains("return (null);"))) {
			returnnullflag = true;
		}

		if( (body.contains(new StringBuffer("log.error"))) ||
				(body.contains(new StringBuffer("log.info"))) ||
				(body.contains(new StringBuffer("log.warn"))) ||
				(body.contains(new StringBuffer("log.debug"))) ||
				(body.contains(new StringBuffer("log.trace"))) ||
				(body.contains(new StringBuffer("log.fatal"))) ||
				(body.contains(new StringBuffer("logger.trace"))) ||
				(body.contains(new StringBuffer("logger.fatal"))) ||
				(body.contains(new StringBuffer("logger.error"))) ||
				(body.contains(new StringBuffer("logger.info"))) ||
				(body.contains(new StringBuffer("logger.warn"))) ||
				(body.contains(new StringBuffer("logger.debug"))) ) {
			logflag = true;
		}

		List<Statement> statements = node.getBody().statements();
		int statementListSize = statements.size();
		for(int i=0;i<statementListSize;i++){
			Statement s = (Statement)statements.get(i);
			String content = s.toString();
			if( content.contains("throw") && content.contains("new")){
				throwflag = true;
			}
		}
		
		
		if(returnnullflag && logflag){ 
			numOfLogAndReturnNullAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Log and Return Null ***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}

		if(throwflag && (logflag || printstacktraceflag)){
			numOfLogAndThrowAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Log and Throw***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}

		if((logflag || printstacktraceflag)&& !returnnullflag && !throwflag && !printstacktraceflag)
		{
			numOfDummyHandlerAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Dummy Handler***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findIgnoringInterruptedException(CatchClause node) {
		String nodeType = node.getException().getType().toString();
		boolean ignoreflag = true;
		if(nodeType.equalsIgnoreCase("InterruptedException")){
			List<Statement> lst = node.getBody().statements();
			for(int i=0; i<lst.size(); i++){
				Statement s = (Statement)lst.get(i);
				String content = s.toString().trim();
				if(!(content.equalsIgnoreCase(""))){
					ignoreflag = false;
				}
			}
			if(ignoreflag){
				numOfIgnoreInterruptedExceptionAP++;
				ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Ignore Interrupted Exception***************");
				ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
				ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
				ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
			}
		}
		
	}

	public void findIncompleteImplementation(CatchClause node) {
		String body = node.getBody().toString();
		if(body.contains(new StringBuffer("TODO")) || body.contains(new StringBuffer("FIX ME")) || body.contains(new StringBuffer("Fix")) || body.contains(new StringBuffer("Todo")) ){
			numOfIncompleteImplementationAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Incomplete Implementation***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findMultiLineLog(CatchClause node) {
		List<Statement> l = node.getBody().statements();
		int logLinesCounter = 0;
		for(int i=0; i<l.size(); i++){
			Statement s = (Statement)l.get(i);
			String content = s.toString();
			if( (content.contains(new StringBuffer("log.error"))) ||
					(content.contains(new StringBuffer("log.info"))) ||
					(content.contains(new StringBuffer("log.warn"))) ||
					(content.contains(new StringBuffer("log.debug"))) ||
					(content.contains(new StringBuffer("log.trace"))) ||
					(content.contains(new StringBuffer("log.fatal"))) ||
					(content.contains(new StringBuffer("logger.trace"))) ||
					(content.contains(new StringBuffer("logger.fatal"))) ||
					(content.contains(new StringBuffer("logger.error"))) ||
					(content.contains(new StringBuffer("logger.info"))) ||
					(content.contains(new StringBuffer("logger.warn"))) ||
					(content.contains(new StringBuffer("logger.debug"))) ) {
				logLinesCounter++;
			}
		}
		if(logLinesCounter>1){
			numOfMultilineLogAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Multiline Log***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findNestedTry(CatchClause node) {
		if(node.getParent().getParent().getNodeType() == ASTNode.TRY_STATEMENT || node.getParent().getParent().getParent().getNodeType() == ASTNode.TRY_STATEMENT) {
			numOfNestedTryAP++;
			ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Nested Try***************");
			ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
			ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
		}
	}

	public void findRelyingOnGetCause(CatchClause node) {
		List<Statement> lists = node.getBody().statements();
		int lssizee = lists.size();
		for(int i=0;i<lssizee;i++){
			if( (lists.get(i).toString().trim().contains("getCause"))){
				numOfRelyingOnGetCauseAP++;
				ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Relying on Get Clause***************");
				ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
				ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
				ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
			}
		}
	}

	public void findThrowWithinFinally(CatchClause node) {

		boolean throwWithinFinallyFlag = false;
		if(node.getParent().getNodeType() == ASTNode.TRY_STATEMENT) {
			TryStatement tryNode = (TryStatement) node.getParent();
			List<Statement> lists = tryNode.getFinally().statements();
			int lssizee = lists.size();
			for(int i=0;i<lssizee;i++){
				if( (lists.get(i).toString().trim().contains("throw")) &&
						(lists.get(i).toString().trim().contains("new"))){
					throwWithinFinallyFlag=true;
				}
			}
			if(throwWithinFinallyFlag)
			{
				numOfThrowWithinFinallyAP++;
				ExceptionFinder.stringbuilttxt.append("\n ***************ANTI-PATTERN : Throw Within Finally***************");
				ExceptionFinder.stringbuilttxt.append("\n FILE NAME : " + f.getAbsolutePath());
				ExceptionFinder.stringbuilttxt.append("\n CATCH CLAUSE : " + findMethodForCatch(node));
				ExceptionFinder.stringbuilttxt.append("\n_________________________________________________________________________________\n");
			}
		}

	}

	private ASTNode findParentMethodDeclaration(ASTNode node) {
		ASTNode parent = node.getParent();
		if(parent.getNodeType() == ASTNode.METHOD_DECLARATION) {
			return parent;
		} else {
			return findParentMethodDeclaration(parent);
		}
		
	}

	private ASTNode findMethodForCatch(CatchClause catchClause) {
		return catchClause.getParent();
	}

}
