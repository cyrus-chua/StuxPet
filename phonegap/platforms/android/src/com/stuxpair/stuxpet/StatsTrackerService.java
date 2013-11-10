package com.stuxpair.stuxpet;

import android.app.IntentService;
import android.content.Intent;

public class StatsTrackerService extends IntentService {

	private int stepsTaken = 0;

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public StatsTrackerService() {
		super("StatsTrackerService");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.
		startStepsCounter();
		long endTime = System.currentTimeMillis() + 20 * 1000;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					
				} catch (Exception e) {
				}
			}
		}
	}

	public void startStepsCounter() {
		stepsTaken++;
	}

	public int getStepsTaken() {
		return stepsTaken;
	}

	public void setStepsTaken(int stepsTaken) {
		this.stepsTaken = stepsTaken;
	}

}