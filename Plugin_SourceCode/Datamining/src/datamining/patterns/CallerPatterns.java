package datamining.patterns;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import datamining.handlers.SampleHandler;
import datamining.visitors.MethodDeclarationVisitor;

public class CallerPatterns {
	HashMap<MethodDeclaration, String> methodSuspect = new HashMap<>();
	HashSet<MethodDeclaration> bad_Callers = new HashSet<>();

	private void invocationsForPrint() {
		for (MethodDeclaration declaration : methodSuspect.keySet()) {
			String type = methodSuspect.get(declaration);
			SampleHandler.printMessage(String.format("These methods has %s pattern", type));
			SampleHandler.printMessage(declaration.toString());
			SampleHandler.printMessage(
					"\n_________________________________________________________________________________\n");

		}
	}

	public CallerPatterns(HashMap<MethodDeclaration, String> suspectMethods) {
		this.methodSuspect = suspectMethods;
	}

	private void targetCallersFinder(IPackageFragment packageFragment) throws JavaModelException {
		for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			CompilationUnit parsedCompilationUnit = ExceptionFinder.parse(unit);

			MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor(methodSuspect.keySet());
			parsedCompilationUnit.accept(methodDeclarationVisitor);
			bad_Callers.addAll(methodDeclarationVisitor.callSuspectMethodsGetter());

			for (MethodDeclaration md : methodDeclarationVisitor.callSuspectMethodsGetter()) {
				methodSuspect.put(md, "BAD_CALLER");
			}
		}
	}

	public void patternCallersFinder(IProject project) throws JavaModelException {
		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();

		for (IPackageFragment mypackage : packages) {
			targetCallersFinder(mypackage);
		}
		invocationsForPrint();
	}

	public HashMap<MethodDeclaration, String> suspectMethodsGetter() {
		return methodSuspect;
	}
}
