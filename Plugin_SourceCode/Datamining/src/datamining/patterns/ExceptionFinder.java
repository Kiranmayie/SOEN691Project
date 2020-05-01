package datamining.patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

import datamining.handlers.SampleHandler;
import datamining.visitors.VisitorOfCatchClause;
import datamining.visitors.Node;
import datamining.visitors.VisitorOfThrowsStatement;
import datamining.visitors.TryCatchInfo;
import datamining.visitors.TryStatementVisitor;

public class ExceptionFinder {

	HashMap<MethodDeclaration, String> methodSuspect = new HashMap<>();
	int numOverCatch = 0, overcatchabort = 0;
	int specific = 0;
	int numofSubSumption = 0, numofNonRevoverableException = 0, numofRevoverableException = 0;
	int numofDocumentedException = 0;
	VisitorOfCatchClause visitorException;
	public static StringBuilder stringBuild = new StringBuilder();
	public static StringBuilder stringbuilttxt = new StringBuilder();
	public static HashMap<String, String> extendsRuntimeException = new HashMap<String, String>();
	public static HashMap<String, String> extendsExceptions = new HashMap<String, String>();
	public static HashMap<Node, Set<String>> ExceptionMap = new HashMap<>();
	public static HashMap<Node, Set<Node>> CallGraph = new HashMap<Node, Set<Node>>();

	public void exceptionsFinder(IProject project) throws JavaModelException, ClassNotFoundException {

		extendsInitException();
		extendsInitRunTimeException();
		stringbuilttxt.append(" SOEN 691 Assignment: Discovering Anti Patterns \n");

		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();

		for (IPackageFragment mypackage : packages) {

			targetCatchClausesFinder(mypackage);

		}
	}

	private void extendsInitRunTimeException() {
		extendsRuntimeException.put("Exception", "Throwable");
		extendsRuntimeException.put("RuntimeException", "Exception");
		extendsRuntimeException.put("AnnotationTypeMismatchException", "RuntimeException");
		extendsRuntimeException.put("ArithmeticException", "RuntimeException");
		extendsRuntimeException.put("ArrayStoreException", "RuntimeException");
		extendsRuntimeException.put("BufferOverflowException", "RuntimeException");
		extendsRuntimeException.put("BufferUnderflowException", "RuntimeException");
		extendsRuntimeException.put("CannotRedoException", "RuntimeException");
		extendsRuntimeException.put("CannotUndoException", "RuntimeException");
		extendsRuntimeException.put("ClassCastException", "RuntimeException");
		extendsRuntimeException.put("ClassCastException", "RuntimeException");
		extendsRuntimeException.put("CMMException", "RuntimeException");
		extendsRuntimeException.put("CompletionException", "RuntimeException");
		extendsRuntimeException.put("ConcurrentModificationException", "RuntimeException");
		extendsRuntimeException.put("DirectoryIteratorException", "ConcurrentModificationException");
		extendsRuntimeException.put("DataBindingException", "RuntimeException");
		extendsRuntimeException.put("DateTimeException", "RuntimeException");
		extendsRuntimeException.put("DateTimeParseException", "DateTimeException");
		extendsRuntimeException.put("UnsupportedTemporalTypeException", "DateTimeException");
		extendsRuntimeException.put("ZoneRulesException", "DateTimeException");
		extendsRuntimeException.put("DOMException", "RuntimeException");
		extendsRuntimeException.put("EmptyStackException", "RuntimeException");
		extendsRuntimeException.put("EnumConstantNotPresentException", "RuntimeException");
		extendsRuntimeException.put("EventException", "RuntimeException");
		extendsRuntimeException.put("FileSystemAlreadyExistsException", "RuntimeException");
		extendsRuntimeException.put("FileSystemNotFoundException", "RuntimeException");
		extendsRuntimeException.put("IllegalArgumentException", "RuntimeException");
		extendsRuntimeException.put("IllegalChannelGroupException", "IllegalArgumentException");
		extendsRuntimeException.put("IllegalCharsetNameException", "IllegalArgumentException");
		extendsRuntimeException.put("IllegalFormatException", "IllegalArgumentException");
		extendsRuntimeException.put("DuplicateFormatFlagsException", "IllegalFormatException");
		extendsRuntimeException.put("FormatFlagsConversionMismatchException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalFormatCodePointException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalFormatConversionException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalFormatFlagsException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalFormatPrecisionException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalFormatWidthException", "IllegalFormatException");
		extendsRuntimeException.put("MissingFormatArgumentException", "IllegalFormatException");
		extendsRuntimeException.put("MissingFormatWidthException", "IllegalFormatException");
		extendsRuntimeException.put("UnknownFormatConversionException", "IllegalFormatException");
		extendsRuntimeException.put("UnknownFormatFlagsException", "IllegalFormatException");
		extendsRuntimeException.put("IllegalSelectorException", "IllegalArgumentException");
		extendsRuntimeException.put("IllegalThreadStateException", "IllegalArgumentException");
		extendsRuntimeException.put("InvalidKeyException", "IllegalArgumentException");
		extendsRuntimeException.put("InvalidOpenTypeException", "IllegalArgumentException");
		extendsRuntimeException.put("InvalidParameterException", "IllegalArgumentException");
		extendsRuntimeException.put("InvalidPathException", "IllegalArgumentException");
		extendsRuntimeException.put("KeyAlreadyExistsException", "IllegalArgumentException");
		extendsRuntimeException.put("NumberFormatException", "IllegalArgumentException");
		extendsRuntimeException.put("PatternSyntaxException", "IllegalArgumentException");
		extendsRuntimeException.put("ProviderMismatchException", "IllegalArgumentException");
		extendsRuntimeException.put("UnresolvedAddressException", "IllegalArgumentException");
		extendsRuntimeException.put("UnsupportedAddressTypeException", "IllegalArgumentException");
		extendsRuntimeException.put("UnsupportedCharsetException", "IllegalArgumentException");
		extendsRuntimeException.put("IllegalMonitorStateException", "RuntimeException");
		extendsRuntimeException.put("IllegalPathStateException", "RuntimeException");
		extendsRuntimeException.put("AcceptPendingException", "IllegalStateException");
		extendsRuntimeException.put("AlreadyBoundException", "IllegalStateException");
		extendsRuntimeException.put("AlreadyConnectedException", "IllegalStateException");
		extendsRuntimeException.put("CancellationException", "IllegalStateException");
		extendsRuntimeException.put("CancelledKeyException", "IllegalStateException");
		extendsRuntimeException.put("ClosedDirectoryStreamException", "IllegalStateException");
		extendsRuntimeException.put("ClosedFileSystemException", "IllegalStateException");
		extendsRuntimeException.put("ClosedSelectorException", "IllegalStateException");
		extendsRuntimeException.put("ClosedWatchServiceException", "IllegalStateException");
		extendsRuntimeException.put("ConnectionPendingException", "IllegalStateException");
		extendsRuntimeException.put("FormatterClosedException", "IllegalStateException");
		extendsRuntimeException.put("IllegalBlockingModeException", "IllegalStateException");
		extendsRuntimeException.put("IllegalComponentStateException", "IllegalStateException");
		extendsRuntimeException.put("InvalidDnDOperationException", "IllegalStateException");
		extendsRuntimeException.put("InvalidMarkException", "IllegalStateException");
		extendsRuntimeException.put("NoConnectionPendingException", "IllegalStateException");
		extendsRuntimeException.put("NonReadableChannelException", "IllegalStateException");
		extendsRuntimeException.put("NonWritableChannelException", "IllegalStateException");
		extendsRuntimeException.put("NotYetBoundException", "IllegalStateException");
		extendsRuntimeException.put("NotYetConnectedException", "IllegalStateException");
		extendsRuntimeException.put("OverlappingFileLockException", "IllegalStateException");
		extendsRuntimeException.put("ReadPendingException", "IllegalStateException");
		extendsRuntimeException.put("ShutdownChannelGroupException", "IllegalStateException");
		extendsRuntimeException.put("WritePendingException", "IllegalStateException");
		extendsRuntimeException.put("IllegalStateException", "RuntimeException");
		extendsRuntimeException.put("IllformedLocaleException", "RuntimeException");
		extendsRuntimeException.put("ImagingOpException", "RuntimeException");
		extendsRuntimeException.put("IncompleteAnnotationException", "RuntimeException");
		extendsRuntimeException.put("IndexOutOfBoundsException", "RuntimeException");
		extendsRuntimeException.put("ArrayIndexOutOfBoundsException", "IndexOutOfBoundsException");
		extendsRuntimeException.put("StringIndexOutOfBoundsException", "IndexOutOfBoundsException");
		extendsRuntimeException.put("JMRuntimeException", "RuntimeException");
		extendsRuntimeException.put("MonitorSettingException", "JMRuntimeException");
		extendsRuntimeException.put("RuntimeErrorException", "JMRuntimeException");
		extendsRuntimeException.put("RuntimeMBeanException", "JMRuntimeException");
		extendsRuntimeException.put("RuntimeOperationsException", "JMRuntimeException");
		extendsRuntimeException.put("LSException", "RuntimeException");
		extendsRuntimeException.put("MalformedParameterizedTypeException", "RuntimeException");
		extendsRuntimeException.put("MalformedParametersException", "RuntimeException");
		extendsRuntimeException.put("MirroredTypesException", "RuntimeException");
		extendsRuntimeException.put("MissingResourceException", "RuntimeException");
		extendsRuntimeException.put("NegativeArraySizeException", "RuntimeException");
		extendsRuntimeException.put("NoSuchElementException", "RuntimeException");
		extendsRuntimeException.put("InputMismatchException", "NoSuchElementException");
		extendsRuntimeException.put("NullPointerException", "RuntimeException");
		extendsRuntimeException.put("ProfileDataException", "RuntimeException");
		extendsRuntimeException.put("ProviderException", "RuntimeException");
		extendsRuntimeException.put("ProviderNotFoundException", "RuntimeException");
		extendsRuntimeException.put("RasterFormatException", "RuntimeException");
		extendsRuntimeException.put("RejectedExecutionException", "RuntimeException");
		extendsRuntimeException.put("SecurityException", "RuntimeException");
		extendsRuntimeException.put("AccessControlException", "SecurityException");
		extendsRuntimeException.put("RMISecurityException", "SecurityException");
		extendsRuntimeException.put("SystemException", "RuntimeException");
		extendsRuntimeException.put("TypeConstraintException", "RuntimeException");
		extendsRuntimeException.put("TypeNotPresentException", "RuntimeException");
		extendsRuntimeException.put("UncheckedIOException", "RuntimeException");
		extendsRuntimeException.put("UndeclaredThrowableException", "RuntimeException");
		extendsRuntimeException.put("UnknownEntityException", "RuntimeException");
		extendsRuntimeException.put("UnknownAnnotationValueException", "UnknownEntityException");
		extendsRuntimeException.put("UnknownElementException", "UnknownEntityException");
		extendsRuntimeException.put("UnknownTypeException", "UnknownEntityException");
		extendsRuntimeException.put("UnmodifiableSetException", "RuntimeException");
		extendsRuntimeException.put("UnsupportedOperationException", "RuntimeException");
		extendsRuntimeException.put("HeadlessException", "UnsupportedOperationException");
		extendsRuntimeException.put("ReadOnlyBufferException", "UnsupportedOperationException");
		extendsRuntimeException.put("ReadOnlyFileSystemException", "UnsupportedOperationException");
		extendsRuntimeException.put("WebServiceException", "RuntimeException");
		extendsRuntimeException.put("ProtocolException", "WebServiceException");
		extendsRuntimeException.put("HTTPException", "ProtocolException");
		extendsRuntimeException.put("SOAPFaultException", "ProtocolException");
		extendsRuntimeException.put("WrongMethodTypeException", "RuntimeException");
	}

	public HashMap<MethodDeclaration, String> suspectMethodsGetter() {
		return methodSuspect;
	}

	private void targetCatchClausesFinder(IPackageFragment packageFragment)
			throws JavaModelException, ClassNotFoundException {
		for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			CompilationUnit parsedCompilationUnit = parse(unit);

			String path = unit.getPath().toString();
			if (path.contains("/test/")) {
				continue;
			}

			visitorException = new VisitorOfCatchClause();
			SampleHandler.printMessage(unit.getCorrespondingResource().getLocation().toFile().toString());
			visitorException.setF(unit.getCorrespondingResource().getLocation().toFile());
			countersOfInitializeMetric();
			parsedCompilationUnit.accept(visitorException);
			TryStatementVisitor tryStatmentVisitor = new TryStatementVisitor();
			tryStatmentVisitor.setFile(unit.getCorrespondingResource().getLocation().toFile());
			parsedCompilationUnit.accept(tryStatmentVisitor);
			VisitorOfThrowsStatement throwsStatementVisitor = new VisitorOfThrowsStatement();
			throwsStatementVisitor.setF(unit.getCorrespondingResource().getLocation().toFile());
			parsedCompilationUnit.accept(throwsStatementVisitor);
			missingExceptionOverCatchFinder(tryStatmentVisitor);
			missingExceptionOverCatchAbortFinder(tryStatmentVisitor);

			catchRecoverabilityFinder(tryStatmentVisitor);
			if (TryStatementVisitor.getNumOfTryBlocks() != 0 && VisitorOfCatchClause.getNumOfCatchBlocks() != 0) {
				float abort_Percentage = (TryStatementVisitor.getActionAbort()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float continue_Percentage = (TryStatementVisitor.getActionContinue()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float default_Percentage = (TryStatementVisitor.getActionDefault()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float empty_Percentage = (TryStatementVisitor.getActionEmpty() / TryStatementVisitor.getNumofTryLOC())
						* 100;
				float actions_Log_Percentage = (TryStatementVisitor.getActionLog()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Method_Percentage = (TryStatementVisitor.getActionMethod()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Nestedtry_PercentageFromTry = (TryStatementVisitor.getActionNestedTry()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Return_Percentage = (TryStatementVisitor.getActionReturn()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Throwcurrent_Percentage = (TryStatementVisitor.getActionThrowCurrent()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Thrownew_Percentage = (TryStatementVisitor.getActionThrowNew()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Throwwrap_Percentage = (TryStatementVisitor.getActionThrowWrap()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float actions_Todo_Percentage = (TryStatementVisitor.getActionTODO()
						/ TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float subSumptionPercentage = (numofSubSumption / TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float specificPercentage = (specific / TryStatementVisitor.getNumOfTryBlocks()) * 100;
				float overcatchPercentage = (numOverCatch / VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float overcatchAbortPercentage = (overcatchabort / VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float doNothingPercentage = (VisitorOfCatchClause.getNumOfCatchAndDoNothingAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float returnNullPercentage = (VisitorOfCatchClause.getNumOfCatchAndReturnNullAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float catchGenericPercentage = (VisitorOfCatchClause.getNumOfCatchGenericAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float destructiveWrappingPercentage = (VisitorOfCatchClause.getNumOfDestructiveWrapAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float dummyPercentage = (VisitorOfCatchClause.getNumOfDummyHandlerAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float ignoreInterruptedExceptionPercentage = (VisitorOfCatchClause
						.getNumOfIgnoreInterruptedExceptionAP() / VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float incompletePercentage = (VisitorOfCatchClause.getNumOfIncompleteImplementationAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float logReturnNullPercentage = (VisitorOfCatchClause.getNumOfLogAndReturnNullAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float logThrowPercentage = (VisitorOfCatchClause.getNumOfLogAndThrowAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float multipleLineLogPercentage = (VisitorOfCatchClause.getNumOfMultilineLogAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float actions_Nestedtry_PercentageFromCatch = (VisitorOfCatchClause.getNumOfNestedTryAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;
				float throwInFinallyPercentage = (VisitorOfCatchClause.getNumOfThrowWithinFinallyAP()
						/ VisitorOfCatchClause.getNumOfCatchBlocks()) * 100;

				specificFinder(tryStatmentVisitor);
				documentedExceptionFinder(tryStatmentVisitor);
				stringBuild.append("" + unit.getCorrespondingResource().getLocation().toFile());
				stringBuild.append(',');
				SampleHandler.printMessage("NumOfCatchBlocks" + VisitorOfCatchClause.getNumOfCatchBlocks());

				stringBuild.append("" + VisitorOfCatchClause.getNumOfCatchBlocks());
				stringBuild.append(",");
				if (VisitorOfCatchClause.getNumOfCatchBlocks() == 0) {
					stringBuild.append("0");
					stringBuild.append(",");
					stringBuild.append("0");
					stringBuild.append(",");
				} else {
					stringBuild.append("" + VisitorOfCatchClause.getNumofCatchBlockLOC()
							/ VisitorOfCatchClause.getNumOfCatchBlocks());
					stringBuild.append(",");
					stringBuild.append("" + VisitorOfCatchClause.getNumofCatchBlockSLOC()
							/ VisitorOfCatchClause.getNumOfCatchBlocks());
					stringBuild.append(",");
				}
				stringBuild.append("" + VisitorOfCatchClause.getNumOfCatchAndReturnNullAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfDestructiveWrapAP());
				stringBuild.append(",");

				stringBuild.append("" + VisitorOfCatchClause.getNumOfCatchAndDoNothingAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfCatchGenericAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfDummyHandlerAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfIgnoreInterruptedExceptionAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfIncompleteImplementationAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfLogAndReturnNullAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfLogAndThrowAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfMultilineLogAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfNestedTryAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfRelyingOnGetCauseAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfCatchClause.getNumOfThrowWithinFinallyAP());
				stringBuild.append(",");
				int numofInvokedMethods = findNumberOfInvokedMethods(tryStatmentVisitor);
				stringBuild.append(numofInvokedMethods);
				stringBuild.append(",");

				stringBuild.append("" + TryStatementVisitor.getNumOfTryBlocks());
				stringBuild.append(",");
				if (TryStatementVisitor.getNumOfTryBlocks() == 0) {
					stringBuild.append("0");
					stringBuild.append(",");
					stringBuild.append("0");
					stringBuild.append(",");
				} else {
					stringBuild.append(
							"" + TryStatementVisitor.getNumofTryLOC() / TryStatementVisitor.getNumOfTryBlocks());
					stringBuild.append(",");
					stringBuild.append(
							"" + TryStatementVisitor.getNumofTryBlockSLOC() / TryStatementVisitor.getNumOfTryBlocks());
					stringBuild.append(",");
				}
				stringBuild.append("" + VisitorOfThrowsStatement.getNumOfThrowsGenericAP());
				stringBuild.append(",");
				stringBuild.append("" + VisitorOfThrowsStatement.getNumOfThrowsKitchenSinkAP());
				stringBuild.append(",");
				stringBuild.append("" + numOverCatch);
				stringBuild.append(",");
				stringBuild.append("" + overcatchabort);
				stringBuild.append(",");
				stringBuild.append(numofDocumentedException);
				stringBuild.append(',');
				stringBuild.append(TryStatementVisitor.getDeclarationTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getConditionTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getEHTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getLoopTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getOtherTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionAbort());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionContinue());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionDefault());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionEmpty());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionLog());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionMethod());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionNestedTry());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionReturn());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionThrowCurrent());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionThrowNew());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionThrowWrap());
				stringBuild.append(",");
				stringBuild.append(TryStatementVisitor.getActionTODO());
				stringBuild.append(",");

				stringBuild.append(abort_Percentage);
				stringBuild.append(",");
				stringBuild.append(continue_Percentage);
				stringBuild.append(",");
				stringBuild.append(default_Percentage);
				stringBuild.append(",");
				stringBuild.append(empty_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Log_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Method_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Nestedtry_PercentageFromTry);
				stringBuild.append(",");
				stringBuild.append(actions_Return_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Throwcurrent_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Thrownew_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Throwwrap_Percentage);
				stringBuild.append(",");
				stringBuild.append(actions_Todo_Percentage);
				stringBuild.append(",");
				stringBuild.append(subSumptionPercentage);
				stringBuild.append(",");
				stringBuild.append(specificPercentage);
				stringBuild.append(",");
				stringBuild.append(numofRevoverableException);
				stringBuild.append(",");
				stringBuild.append(numofNonRevoverableException);
				stringBuild.append(",");
				stringBuild.append(overcatchPercentage);
				stringBuild.append(",");
				stringBuild.append(overcatchAbortPercentage);
				stringBuild.append(",");
				stringBuild.append(doNothingPercentage);
				stringBuild.append(",");
				stringBuild.append(returnNullPercentage);
				stringBuild.append(",");
				stringBuild.append(catchGenericPercentage);
				stringBuild.append(",");
				stringBuild.append(destructiveWrappingPercentage);
				stringBuild.append(",");
				stringBuild.append(dummyPercentage);
				stringBuild.append(",");
				stringBuild.append(ignoreInterruptedExceptionPercentage);
				stringBuild.append(",");
				stringBuild.append(incompletePercentage);
				stringBuild.append(",");
				stringBuild.append(logReturnNullPercentage);
				stringBuild.append(",");
				stringBuild.append(logThrowPercentage);
				stringBuild.append(",");
				stringBuild.append(multipleLineLogPercentage);
				stringBuild.append(",");
				stringBuild.append(actions_Nestedtry_PercentageFromCatch);
				stringBuild.append(",");
				stringBuild.append(throwInFinallyPercentage);
				stringBuild.append("\n");
			}

		}
	}

	private void extendsInitException() {
		extendsExceptions.put("Exception", "Throwable");
		extendsExceptions.put("RuntimeException", "Exception");
		extendsExceptions.put("AnnotationTypeMismatchException", "RuntimeException");
		extendsExceptions.put("ArithmeticException", "RuntimeException");
		extendsExceptions.put("ArrayStoreException", "RuntimeException");
		extendsExceptions.put("BufferOverflowException", "RuntimeException");
		extendsExceptions.put("BufferUnderflowException", "RuntimeException");
		extendsExceptions.put("CannotRedoException", "RuntimeException");
		extendsExceptions.put("CannotUndoException", "RuntimeException");
		extendsExceptions.put("ClassCastException", "RuntimeException");
		extendsExceptions.put("ClassCastException", "RuntimeException");
		extendsExceptions.put("CMMException", "RuntimeException");
		extendsExceptions.put("CompletionException", "RuntimeException");
		extendsExceptions.put("ConcurrentModificationException", "RuntimeException");
		extendsExceptions.put("DirectoryIteratorException", "ConcurrentModificationException");
		extendsExceptions.put("DataBindingException", "RuntimeException");
		extendsExceptions.put("DateTimeException", "RuntimeException");
		extendsExceptions.put("DateTimeParseException", "DateTimeException");
		extendsExceptions.put("UnsupportedTemporalTypeException", "DateTimeException");
		extendsExceptions.put("ZoneRulesException", "DateTimeException");
		extendsExceptions.put("DOMException", "RuntimeException");
		extendsExceptions.put("EmptyStackException", "RuntimeException");
		extendsExceptions.put("EnumConstantNotPresentException", "RuntimeException");
		extendsExceptions.put("EventException", "RuntimeException");
		extendsExceptions.put("FileSystemAlreadyExistsException", "RuntimeException");
		extendsExceptions.put("FileSystemNotFoundException", "RuntimeException");
		extendsExceptions.put("IllegalArgumentException", "RuntimeException");
		extendsExceptions.put("IllegalChannelGroupException", "IllegalArgumentException");
		extendsExceptions.put("IllegalCharsetNameException", "IllegalArgumentException");
		extendsExceptions.put("IllegalFormatException", "IllegalArgumentException");
		extendsExceptions.put("DuplicateFormatFlagsException", "IllegalFormatException");
		extendsExceptions.put("FormatFlagsConversionMismatchException", "IllegalFormatException");
		extendsExceptions.put("IllegalFormatCodePointException", "IllegalFormatException");
		extendsExceptions.put("IllegalFormatConversionException", "IllegalFormatException");
		extendsExceptions.put("IllegalFormatFlagsException", "IllegalFormatException");
		extendsExceptions.put("IllegalFormatPrecisionException", "IllegalFormatException");
		extendsExceptions.put("IllegalFormatWidthException", "IllegalFormatException");
		extendsExceptions.put("MissingFormatArgumentException", "IllegalFormatException");
		extendsExceptions.put("MissingFormatWidthException", "IllegalFormatException");
		extendsExceptions.put("UnknownFormatConversionException", "IllegalFormatException");
		extendsExceptions.put("UnknownFormatFlagsException", "IllegalFormatException");
		extendsExceptions.put("IllegalSelectorException", "IllegalArgumentException");
		extendsExceptions.put("IllegalThreadStateException", "IllegalArgumentException");
		extendsExceptions.put("InvalidKeyException", "IllegalArgumentException");
		extendsExceptions.put("InvalidOpenTypeException", "IllegalArgumentException");
		extendsExceptions.put("InvalidParameterException", "IllegalArgumentException");
		extendsExceptions.put("InvalidPathException", "IllegalArgumentException");
		extendsExceptions.put("KeyAlreadyExistsException", "IllegalArgumentException");
		extendsExceptions.put("NumberFormatException", "IllegalArgumentException");
		extendsExceptions.put("PatternSyntaxException", "IllegalArgumentException");
		extendsExceptions.put("ProviderMismatchException", "IllegalArgumentException");
		extendsExceptions.put("UnresolvedAddressException", "IllegalArgumentException");
		extendsExceptions.put("UnsupportedAddressTypeException", "IllegalArgumentException");
		extendsExceptions.put("UnsupportedCharsetException", "IllegalArgumentException");
		extendsExceptions.put("IllegalMonitorStateException", "RuntimeException");
		extendsExceptions.put("IllegalPathStateException", "RuntimeException");
		extendsExceptions.put("AcceptPendingException", "IllegalStateException");
		extendsExceptions.put("AlreadyBoundException", "IllegalStateException");
		extendsExceptions.put("AlreadyConnectedException", "IllegalStateException");
		extendsExceptions.put("CancellationException", "IllegalStateException");
		extendsExceptions.put("CancelledKeyException", "IllegalStateException");
		extendsExceptions.put("ClosedDirectoryStreamException", "IllegalStateException");
		extendsExceptions.put("ClosedFileSystemException", "IllegalStateException");
		extendsExceptions.put("ClosedSelectorException", "IllegalStateException");
		extendsExceptions.put("ClosedWatchServiceException", "IllegalStateException");
		extendsExceptions.put("ConnectionPendingException", "IllegalStateException");
		extendsExceptions.put("FormatterClosedException", "IllegalStateException");
		extendsExceptions.put("IllegalBlockingModeException", "IllegalStateException");
		extendsExceptions.put("IllegalComponentStateException", "IllegalStateException");
		extendsExceptions.put("InvalidDnDOperationException", "IllegalStateException");
		extendsExceptions.put("InvalidMarkException", "IllegalStateException");
		extendsExceptions.put("NoConnectionPendingException", "IllegalStateException");
		extendsExceptions.put("NonReadableChannelException", "IllegalStateException");
		extendsExceptions.put("NonWritableChannelException", "IllegalStateException");
		extendsExceptions.put("NotYetBoundException", "IllegalStateException");
		extendsExceptions.put("NotYetConnectedException", "IllegalStateException");
		extendsExceptions.put("OverlappingFileLockException", "IllegalStateException");
		extendsExceptions.put("ReadPendingException", "IllegalStateException");
		extendsExceptions.put("ShutdownChannelGroupException", "IllegalStateException");
		extendsExceptions.put("WritePendingException", "IllegalStateException");
		extendsExceptions.put("IllegalStateException", "RuntimeException");
		extendsExceptions.put("IllformedLocaleException", "RuntimeException");
		extendsExceptions.put("ImagingOpException", "RuntimeException");
		extendsExceptions.put("IncompleteAnnotationException", "RuntimeException");
		extendsExceptions.put("IndexOutOfBoundsException", "RuntimeException");
		extendsExceptions.put("ArrayIndexOutOfBoundsException", "IndexOutOfBoundsException");
		extendsExceptions.put("StringIndexOutOfBoundsException", "IndexOutOfBoundsException");
		extendsExceptions.put("JMRuntimeException", "RuntimeException");
		extendsExceptions.put("MonitorSettingException", "JMRuntimeException");
		extendsExceptions.put("RuntimeErrorException", "JMRuntimeException");
		extendsExceptions.put("RuntimeMBeanException", "JMRuntimeException");
		extendsExceptions.put("RuntimeOperationsException", "JMRuntimeException");
		extendsExceptions.put("LSException", "RuntimeException");
		extendsExceptions.put("MalformedParameterizedTypeException", "RuntimeException");
		extendsExceptions.put("MalformedParametersException", "RuntimeException");
		extendsExceptions.put("MirroredTypesException", "RuntimeException");
		extendsExceptions.put("MissingResourceException", "RuntimeException");
		extendsExceptions.put("NegativeArraySizeException", "RuntimeException");
		extendsExceptions.put("NoSuchElementException", "RuntimeException");
		extendsExceptions.put("InputMismatchException", "NoSuchElementException");
		extendsExceptions.put("NullPointerException", "RuntimeException");
		extendsExceptions.put("ProfileDataException", "RuntimeException");
		extendsExceptions.put("ProviderException", "RuntimeException");
		extendsExceptions.put("ProviderNotFoundException", "RuntimeException");
		extendsExceptions.put("RasterFormatException", "RuntimeException");
		extendsExceptions.put("RejectedExecutionException", "RuntimeException");
		extendsExceptions.put("SecurityException", "RuntimeException");
		extendsExceptions.put("AccessControlException", "SecurityException");
		extendsExceptions.put("RMISecurityException", "SecurityException");
		extendsExceptions.put("SystemException", "RuntimeException");
		extendsExceptions.put("TypeConstraintException", "RuntimeException");
		extendsExceptions.put("TypeNotPresentException", "RuntimeException");
		extendsExceptions.put("UncheckedIOException", "RuntimeException");
		extendsExceptions.put("UndeclaredThrowableException", "RuntimeException");
		extendsExceptions.put("UnknownEntityException", "RuntimeException");
		extendsExceptions.put("UnknownAnnotationValueException", "UnknownEntityException");
		extendsExceptions.put("UnknownElementException", "UnknownEntityException");
		extendsExceptions.put("UnknownTypeException", "UnknownEntityException");
		extendsExceptions.put("UnmodifiableSetException", "RuntimeException");
		extendsExceptions.put("UnsupportedOperationException", "RuntimeException");
		extendsExceptions.put("HeadlessException", "UnsupportedOperationException");
		extendsExceptions.put("ReadOnlyBufferException", "UnsupportedOperationException");
		extendsExceptions.put("ReadOnlyFileSystemException", "UnsupportedOperationException");
		extendsExceptions.put("WebServiceException", "RuntimeException");
		extendsExceptions.put("ProtocolException", "WebServiceException");
		extendsExceptions.put("HTTPException", "ProtocolException");
		extendsExceptions.put("SOAPFaultException", "ProtocolException");
		extendsExceptions.put("WrongMethodTypeException", "RuntimeException");

	}

	public void countersOfInitializeMetric() {

		VisitorOfCatchClause.setNumofCatchBlockLOC(0);
		VisitorOfCatchClause.setNumOfCatchBlocks(0);
		VisitorOfCatchClause.setNumofCatchBlockSLOC(0);
		VisitorOfCatchClause.setNumOfCatchAndReturnNullAP(0);
		VisitorOfCatchClause.setNumOfDestructiveWrapAP(0);
		VisitorOfCatchClause.setNumOfCatchAndDoNothingAP(0);
		VisitorOfCatchClause.setNumOfCatchGenericAP(0);
		VisitorOfCatchClause.setNumOfDummyHandlerAP(0);
		VisitorOfCatchClause.setNumOfIgnoreInterruptedExceptionAP(0);
		VisitorOfCatchClause.setNumOfIncompleteImplementationAP(0);
		VisitorOfCatchClause.setNumOfLogAndReturnNullAP(0);
		VisitorOfCatchClause.setNumOfLogAndThrowAP(0);
		VisitorOfCatchClause.setNumOfMultilineLogAP(0);
		VisitorOfCatchClause.setNumOfNestedTryAP(0);
		VisitorOfCatchClause.setNumOfRelyingOnGetCauseAP(0);

		TryStatementVisitor.setNumOfTryBlocks(0);
		TryStatementVisitor.setNumofTryLOC(0);
		TryStatementVisitor.setNumofTryBlockSLOC(0);

		TryStatementVisitor.setDeclarationTry(0);
		TryStatementVisitor.setConditionTry(0);
		TryStatementVisitor.setEHTry(0);
		TryStatementVisitor.setLoopTry(0);
		TryStatementVisitor.setOtherTry(0);
		TryStatementVisitor.setWholeExceptionsInCatch(null);
		TryStatementVisitor.setActionAbort(0);
		TryStatementVisitor.setActionContinue(0);
		TryStatementVisitor.setActionDefault(0);
		TryStatementVisitor.setActionEmpty(0);
		TryStatementVisitor.setActionLog(0);
		TryStatementVisitor.setActionMethod(0);
		TryStatementVisitor.setActionNestedTry(0);
		TryStatementVisitor.setActionReturn(0);
		TryStatementVisitor.setActionThrowCurrent(0);
		TryStatementVisitor.setActionThrowNew(0);
		TryStatementVisitor.setActionThrowWrap(0);
		TryStatementVisitor.setActionTODO(0);

		VisitorOfThrowsStatement.setNumOfThrowsGenericAP(0);
		VisitorOfThrowsStatement.setNumOfThrowsKitchenSinkAP(0);
	}

	public int findNumberOfInvokedMethods(TryStatementVisitor tryStatementVisitor) {
		int x = 0;
		for (TryCatchInfo info : tryStatementVisitor.getTryCatchInfo()) {
			x = x + info.getNumberOfInvokedMehtods();
		}
		return x;
	}

	public void missingExceptionOverCatchFinder(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {

		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {

			if (info.getExceptionThrownByMethod().size() < 1 && (info.getCatchBlockException().contains("Exception")
					|| info.getCatchBlockException().contains("Throwable"))) {
				Set<String> settry = tryStatmentVisitor.exceptionSetfromTry;

				List<String> catches = info.getCatchBlockException();

				if (catches == null || catches.size() == 0) {
					break;
				}

				for (String sc : catches) {
					for (String st : settry) {
						String exception = ExceptionFinder.extendsExceptions.get(sc);
						while (exception != null) {

							if (exception.equals(st)) {
								// overcatch
								numOverCatch++;

							} else {
								exception = ExceptionFinder.extendsExceptions.get(exception);
								if (exception == null) {
									break;
								}
							}

						}
					}
				}
			}

		}

	}

	public static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		return (CompilationUnit) parser.createAST(null);
	}

	public void missingExceptionOverCatchAbortFinder(TryStatementVisitor tryStatmentVisitor)
			throws ClassNotFoundException {

		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {

			if (info.getExceptionThrownByMethod().size() < 1 && (info.getCatchBlockException().contains("Exception")
					|| info.getCatchBlockException().contains("Throwable"))) {
				Set<String> settry = tryStatmentVisitor.exceptionSetfromTry;

				List<String> catches = info.getCatchBlockException();

				if (catches == null || catches.size() == 0) {
					break;
				}

				for (String sc : catches) {
					for (String st : settry) {
						String exception = ExceptionFinder.extendsExceptions.get(sc);
						while (exception != null) {

							if (exception.equals(st) && info.getBody().contains("System.exit")) {
								// overcatch abort
								overcatchabort++;

							} else {
								exception = ExceptionFinder.extendsExceptions.get(exception);
								if (exception == null) {
									break;
								}
							}

						}
					}
				}
			}

		}

	}

	public void catchRecoverabilityFinder(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {
		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {

			if (info.getExceptionThrownByMethod().size() < 1 && (info.getCatchBlockException().contains("Exception")
					|| info.getCatchBlockException().contains("Throwable"))) {
				List<String> catches = info.getCatchBlockException();

				if (catches == null || catches.size() == 0) {
					break;
				}

				for (String sc : catches) {
					if (extendsRuntimeException.containsKey(sc) || extendsExceptions.containsKey(sc)) {
						numofRevoverableException++;
					} else {
						numofNonRevoverableException++;
					}
				}

			}

		}
	}

	public void specificFinder(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {

		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {

			if (info.getExceptionThrownByMethod().size() < 1 && (info.getCatchBlockException().contains("Exception")
					|| info.getCatchBlockException().contains("Throwable"))) {
				Set<String> settry = tryStatmentVisitor.getExceptionSetfromTry();

				List<String> catches = info.getCatchBlockException();

				if (catches == null || catches.size() == 0) {
					break;
				}

				for (String sc : catches) {
					for (String st : settry) {
						if (st.equals(sc)) {
							// specific
							specific++;

						}
					}
				}
			}

		}
	}

	public void documentedExceptionFinder(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {

		Set<String> settry = tryStatmentVisitor.getExceptionSetfromTry();
		for (String st : settry) {
			if (ExceptionMap.containsKey(st)) {
				numofDocumentedException++;
			}
		}
	}

	public void subSumptionFinder(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {

		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {

			if (info.getExceptionThrownByMethod().size() < 1 && (info.getCatchBlockException().contains("Exception")
					|| info.getCatchBlockException().contains("Throwable"))) {
				Set<String> settry = tryStatmentVisitor.getExceptionSetfromTry();

				List<String> catches = info.getCatchBlockException();

				if (catches == null || catches.size() == 0) {
					break;
				}

				for (String sc : catches) {
					for (String st : settry) {
						String exception = ExceptionFinder.extendsExceptions.get(st);
						while (exception != null) {

							if (exception.equals(sc)) {

								numofSubSumption++;

							} else {
								exception = ExceptionFinder.extendsExceptions.get(exception);
								if (exception == null) {
									break;
								}
							}

						}
					}
				}
			}

		}
	}

}
