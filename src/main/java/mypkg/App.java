package mypkg;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "hello", description = "Say hello.", //
		mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {

	@Option(names = { "-n", "--name" }, defaultValue = "world", description = "Name to greet")
	protected String name;

	@Override
	public Integer call() {
		System.out.println("Hello " + this.name);
		return 0;
	}

	public static void main(String[] args) {
		int exitCode = new CommandLine(new App()).execute(args);
		System.exit(exitCode);
	}
}
