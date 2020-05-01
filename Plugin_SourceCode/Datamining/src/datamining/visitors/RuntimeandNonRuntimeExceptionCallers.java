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
	public static ArrayList<String> FindNonRuntimeExceptions(MethodInvocation methodInvocation) throws JavaModelException {
		IMethodBinding iMethodBinding = methodInvocation.resolveMethodBinding().getMethodDeclaration();
		ArrayList<String> exceptions = new ArrayList<>();
		for(ITypeBinding i:iMethodBinding.getExceptionTypes()) {
			String name = i.getQualifiedName();
			if(name.contains(".")) {
				int j = name.lastIndexOf(".");
				String name1 = name.substring(j+1, name.length());
				exceptions.add(name1);

			}
			else
				exceptions.add(name);
		}
		return exceptions;
	}
	
	private static String getJavaDocument(IMember iMember) throws JavaModelException {
		ISourceRange iSourceRange = iMember.getJavadocRange();
		String Text = iMember.getOpenable().getBuffer().getText(iSourceRange.getOffset(), iSourceRange.getLength());
		Text = Text.replaceAll("^/[*][*][ \t]*\n?", "")
					.replaceAll("\n?[ \t]*[*]/$", "") .replaceAll("^\\s*[*]", "\n")
					.replaceAll("\n\\s*[*]", "\n").replaceAll("<[^>]*>", "")
					.replaceAll("[{]@code([^}]*)[}]", "$1").replaceAll("&nbsp;", " ")
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
		return Text;
	}
	
	public static ArrayList<String> FindRuntimeExceptions(MethodInvocation methodInvocation) throws JavaModelException {
		IMethodBinding iMethodBinding = methodInvocation.resolveMethodBinding().getMethodDeclaration();
		ArrayList<String> list = new ArrayList<>();
		ISourceRange iSourceRange = ((IMethod) iMethodBinding.getJavaElement()).getJavadocRange();
		if (iSourceRange != null) {
			String temp = getJavaDocument((IMethod) iMethodBinding.getJavaElement());
			list.addAll(FindExceptionsInJavadoc(temp));
		}
		return list;

	}
	
	public static ArrayList<String> FindNonRuntimeExceptions(IMethodBinding imb) throws JavaModelException {
		ArrayList<String> exceptions = new ArrayList<>();
		for(ITypeBinding i:imb.getExceptionTypes()) {
			String name = i.getQualifiedName();
			if(name.contains(".")) {
				int j = name.lastIndexOf(".");
				String name1 = name.substring(j+1, name.length());
				exceptions.add(name1);

			}
			else
				exceptions.add(name);
		}
		return exceptions;
	}

	

}
