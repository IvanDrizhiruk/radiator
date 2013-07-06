package ua.dp.ardas.radiator.utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.google.common.base.Stopwatch;

public class Timer {
	Stopwatch timer = new Stopwatch();

	public Timer() {
		timer.start();
	}

	public long elapsedTimeInMilliseconds() {
		timer.stop();
		return timer.elapsedTime(MILLISECONDS);
	}
}
