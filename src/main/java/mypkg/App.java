package mypkg;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "hello", description = "Say hello.", //
		mixinStandardHelpOptions = true)
public class App implements Runnable {

	@Option(names = { "-n", "--name" }, defaultValue = "world", description = "Name to greet")
	protected String name;

	@Override
	public void run() {
		System.out.println("Hello " + this.name);
	}

	public static void main(String[] args) {
		int exitCode = new CommandLine(new App()).execute(args);
		System.exit(exitCode);
	}
}
