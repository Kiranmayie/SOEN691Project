package datamining.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.jface.resource.ImageDescriptor;

public class SampleHandler extends AbstractHandler {

	private static final String CONSOLE_NAME = "Anti Patterns";
	private static MessageConsole my_Console;
	private static MessageConsoleStream out;

	static public void printMessage(String message) {
		out.println(message);
	}

	private MessageConsole consoleFinder(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();

		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];

		MessageConsole my_Console = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { my_Console });
		return my_Console;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SampleHandler.my_Console = consoleFinder(CONSOLE_NAME);
		SampleHandler.out = my_Console.newMessageStream();

		DetectException detectException = new DetectException();
		detectException.execute(event);

		return null;
	}

}
