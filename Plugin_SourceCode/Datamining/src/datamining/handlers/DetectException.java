package datamining.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.JavaModelException;

import datamining.patterns.ExceptionFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class DetectException extends AbstractHandler {
	public static PrintWriter writerCsv = null;
	public static PrintWriter writertxtCsv = null;

	private void detectionInProjects(IProject[] projects) throws ClassNotFoundException {

		try {
			String filePath = System.getProperty("user.home") + "/Desktop/Result.csv";
			File file = new File(filePath);
			if (file.exists())
				file.delete();
			writerCsv = new PrintWriter(file);

			ExceptionFinder.stringBuild.append("file_name");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Destructive Wrapping AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Catch and Do Nothing AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Catch Generic AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Dummy Handler AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("number_of_Catch_Blocks");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Catch Block LOC");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Catch Block SLOC");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Throw Within Finally AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Invoked methods");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try Blocks");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try Block LOC");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Catch And Return Null AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Ignoring Interrupted Exception AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Incomplete Implementation AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Log and Return Null AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Log and Throw AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Mutliline log AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Nested Try AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Relying on Get Cause AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try Block SLOC");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Throws Generic AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Throws Kitchen Sink AP");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Over Catches");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Over Catches and Abort");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("numofDocumentedException");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try in Declaration");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try in Condition");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try in EH");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try in Loop");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Number of Try in Other");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Abort");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Continue");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Default");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Empty");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action ThrowCurrent");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action ThrowNew");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action ThrowWrap");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action TODO");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Log");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Method");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action NestedTry");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Action Return");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Abort");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Continue");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action NestedTry");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Return");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action ThrowCurrent");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action ThrowNew");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action ThrowWrap");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Default");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Empty");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Log");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action Method");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("Percentage Action TODO");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("subSumptionPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("specificPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("numofRevoverableException");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("numofNonRevoverableException");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("overcatchPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("overcatchAbortPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("doNothingPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("returnNullPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("catchGenericPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("destructiveWrappingPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("logReturnNullPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("logThrowPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("multipleLineLogPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("actions_Nestedtry_PercentageFromCatch");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("dummyPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("ignoreInterruptedExceptionPercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("incompletePercentage");
			ExceptionFinder.stringBuild.append(",");
			ExceptionFinder.stringBuild.append("throwInFinallyPercentage");
			ExceptionFinder.stringBuild.append("\n");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		for (IProject project : projects) {
			SampleHandler.printMessage("Starting");
			SampleHandler.printMessage("DETECTING IN: " + project.getName());
			ExceptionFinder exceptionFinder = new ExceptionFinder();

			try {
				exceptionFinder.exceptionsFinder(project);

			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		writerCsv.write(ExceptionFinder.stringBuild.toString());
		writerCsv.close();
	}

	@Override
	public Object execute(ExecutionEvent event) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		IProject[] projects = root.getProjects();

		try {
			detectionInProjects(projects);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		SampleHandler.printMessage("DETECTION OF ANTI PATTERN IS DONE");

		return null;
	}

}
