package datamining.visitors;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

public class TryCatchInfo {
	List<String> functionName = new LinkedList<>();
	List<String> catchBlockException = new LinkedList<>();
	List<String> exceptionThrownByMethod = new LinkedList<>();
	List<Class> throwExceptionsBinaryName = new LinkedList<>();
	String FileName;
	MethodDeclaration CatchBody;
	String body;
	int numberOfInvokedMehtods;

	public void addExceptionThrownByMethod(String exceptionThrownByMethod) {
		this.exceptionThrownByMethod.add(exceptionThrownByMethod);
	}

	public int getNumberOfInvokedMehtods() {
		return numberOfInvokedMehtods;
	}

	public void setNumberOfInvokedMehtods(int numberOfInvokedMehtods) {
		this.numberOfInvokedMehtods = numberOfInvokedMehtods;
	}

	public List<Class> getBinaryNameforThrowExceptions() {
		return throwExceptionsBinaryName;
	}

	public void setBinaryNameforThrowExceptions(List<Class> binaryNameforThrowExceptions) {
		this.throwExceptionsBinaryName = binaryNameforThrowExceptions;
	}

	public MethodDeclaration getCatchBody() {
		return CatchBody;
	}

	public void setCatchBody(MethodDeclaration catchBody) {
		CatchBody = catchBody;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public List<String> getFuncName() {
		return functionName;
	}

	public void addFuncName(String funcName) {
		this.functionName.add(funcName);
	}

	public void setFuncName(List<String> funcNames) {
		this.functionName = funcNames;
	}

	public List<String> getCatchBlockException() {
		return catchBlockException;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addCatchBlockException(String catchBlockException) {
		this.catchBlockException.add(catchBlockException);
	}

	public List<String> getExceptionThrownByMethod() {
		return exceptionThrownByMethod;
	}

	public void setExceptionThrownByMethod(List<String> exceptionThrownByMethod) {
		this.exceptionThrownByMethod = exceptionThrownByMethod;
	}

}
