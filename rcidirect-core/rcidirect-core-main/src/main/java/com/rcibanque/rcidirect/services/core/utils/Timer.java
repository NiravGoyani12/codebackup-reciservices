package com.rcibanque.rcidirect.services.core.utils;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public final class Timer {

	/**
	 * @param pStart Start timer
	 * @return New timer
	 */
	public static Timer getInstance(boolean pStart) {
		Timer res = new Timer();
		if(pStart) {
			res.start();
		}
		return res;
	}


	private long _start;

	private long _intermediary;


	private Timer() {
		super();
	}

	/**
	 * Start the timer
	 */
	public void start() {
		_start = getCurrentTimeInMs();
		_intermediary = _start;
	}

	/**
	 * @return Intermediary time (hh:mm:ss:ms) since start or last intermediary time
	 */
	public String getTimeIntermediary() {
		long currentTimeinMs = getCurrentTimeInMs();
		String res = getTime(currentTimeinMs - _intermediary);
		_intermediary = currentTimeinMs;
		return res;
	}

	/**
	 * @return Total time (hh:mm:ss:ms) since start
	 */
	public String getTimeTotal() {
		return getTime(getTimeTotalMS());
	}

	/**
	 * @return Total time in milliseconds
	 */
	public long getTimeTotalMS() {
		return getCurrentTimeInMs() - _start;
	}

	/**
	 * @param pTimeInMs Time in ms
	 * @return String representation of time
	 */
	public static String getTime(Long pTimeInMs) {

		long timeInSec = pTimeInMs / 1000;

		int h = (int) (timeInSec / 3600);
		int m = (int) ((timeInSec % 3600) / 60);
		int s = (int) (timeInSec % 60);
		int ms = (int) (pTimeInMs % 1000);

		String res = StringUtils.EMPTY;

		res += h < 10 ? "0" : StringUtils.EMPTY;
		res += Integer.toString(h);
		res += ":";
		res += m < 10 ? "0" : StringUtils.EMPTY;
		res += Integer.toString(m);
		res += ":";
		res += s < 10 ? "0" : StringUtils.EMPTY;
		res += Integer.toString(s);
		res += ":";
		res += ms < 10 ? "00" : ms < 100 ? "0" : StringUtils.EMPTY;
		res += Integer.toString(ms);

		return res;
	}

	private long getCurrentTimeInMs() {
		return Calendar.getInstance().getTimeInMillis();
	}

}
