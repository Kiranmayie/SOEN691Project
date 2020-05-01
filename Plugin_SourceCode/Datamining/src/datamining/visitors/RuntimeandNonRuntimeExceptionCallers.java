package datamining.visitors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class RuntimeandNonRuntimeExceptionCallers {

	
	public static ArrayList<String> FindRuntimeExceptions(IMethodBinding iMethodBinding ) throws JavaModelException {
		ArrayList<String> list = new ArrayList<>();
		if((IMethod) iMethodBinding.getJavaElement() != null) {
			ISourceRange iSourceRange = ((IMethod) iMethodBinding.getJavaElement()).getJavadocRange();
			if (iSourceRange != null) {
				String temp = getJavaDocument((IMethod) iMethodBinding.getJavaElement());
				list.addAll(FindExceptionsInJavadoc(temp));
			}	
		}
		return list;

	}
	
	
	
	public static ArrayList<String> FindExceptionsInJavadoc(String javadocrefactored){
		ArrayList<String> exceptionList = new ArrayList<String>();
		String[] temp = javadocrefactored.split("\n");
		List<String> arraylist = Arrays.asList(temp);
		for(String i : arraylist) {
			if(i.contains("throws")) {
				String[] str = i.split(" ");
				for(String s: str) {
					if(s.contains("Exception")) {
						exceptionList.add(s);
						break;
					}
				}
			}
		}
		return exceptionList;
	}
	
	

}
