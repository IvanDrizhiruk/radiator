import static java.lang.System.out;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;

public class StopWatchDemo {
	private final static Logger LOGGER = Logger.getLogger(StopWatchDemo.class
			.getCanonicalName());

	public static void doSomethingJustToBeDoingIt(
			final int numberOfTimesToDoNothing) {
		for (int count = 0; count < numberOfTimesToDoNothing; count++) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
			} catch (InterruptedException interruptEx) {
				LOGGER.log(Level.INFO,
						"Don't interrupt me when I'm trying to sleep!",
						interruptEx);
			}
		}
	}

	public static void printElapsedTime(final int numberLoops,
			final Stopwatch stopwatch) {
		if (stopwatch.isRunning()) {
			out.println("WARNING! Your stopwatch is still running!");
		} else {
			out.println(numberLoops + " loops required: ");
			out.println("\t" + stopwatch.toString(6));
			out.println("\t" + stopwatch.elapsedMillis() + " elapsed milliseconds.");
			out.println("\t" + stopwatch.elapsedTime(TimeUnit.MINUTES) + " minutes");
			out.println("\t" + stopwatch.elapsedTime(TimeUnit.SECONDS) + " seconds");
			out.println("\t" + stopwatch.elapsedTime(TimeUnit.MILLISECONDS) + " milliseconds");
			out.println("\t" + stopwatch.elapsedTime(TimeUnit.NANOSECONDS) + " nanoseconds");
		}
	}

	public static void main(final String[] arguments) {
		final Stopwatch stopwatch = new Stopwatch();

		int numberTimes = 5;
		stopwatch.start();
		doSomethingJustToBeDoingIt(numberTimes);
		stopwatch.stop();
		printElapsedTime(numberTimes, stopwatch);

		numberTimes = 2;
		stopwatch.start();
		doSomethingJustToBeDoingIt(numberTimes);
		stopwatch.stop();
		printElapsedTime(numberTimes, stopwatch);

	}
}