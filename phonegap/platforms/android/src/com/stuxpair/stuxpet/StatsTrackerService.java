package com.stuxpair.stuxpet;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StatsTrackerService extends IntentService {

	private int stepsTaken = 0;
	private StuxPetDB petData = new StuxPetDB(this);
	public final static int MINUS_HUNGER = 1;
	public final static int MINUS_HEALTH = 2;
	public final static int MINUS_HAPPY = 3;

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
		petData.open();
		if (intent.getExtras() != null) {
			Bundle extras = intent.getExtras();
			switch (extras.getInt("action")) {
			case MINUS_HUNGER:
				petData.updateStats("", -2, 0, 0);
				handleHunger();
				break;
			case MINUS_HEALTH:
				Log.i("statsTracker", "minus_health");
				petData.updateStats("", 0, -1, 0);
				handleHealth();
				break;
			case MINUS_HAPPY:
				Log.i("statsTracker", "minus_happy");
				petData.updateStats("", 0, 0, -1);
				handleHappy();
				break;
			}
		} else {
			handleHealth();
			handleHunger();
			handleHappy();
		}
		petData.close();
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

	public void handleHunger() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour < 9)
			calendar.set(Calendar.HOUR_OF_DAY, 9);
		else if (hour < 13)
			calendar.set(Calendar.HOUR_OF_DAY, 13);
		else if (hour < 19)
			calendar.set(Calendar.HOUR_OF_DAY, 19);
		else {
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 9);
		}
		
		calendar.set(Calendar.MINUTE, 1);
		
		Intent hunger = new Intent(this, StatsReceiver.class);
		hunger.putExtra("action", MINUS_HUNGER);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				hunger, PendingIntent.FLAG_UPDATE_CURRENT);
		setAlarm(pendingIntent, calendar.getTimeInMillis());
	}

	public void handleHealth() {
		Intent health = new Intent(this, StatsReceiver.class);
		health.putExtra("action", MINUS_HEALTH);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
				health, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// Time to add alarm
		calendar.add(Calendar.HOUR_OF_DAY, 2);

		setAlarm(pendingIntent, calendar.getTimeInMillis());
	}

	public void handleHappy() {
		Intent happy = new Intent(this, StatsReceiver.class);
		happy.putExtra("action", MINUS_HAPPY);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2,
				happy, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		// Time to add alarm
		calendar.add(Calendar.HOUR_OF_DAY, 2);

		setAlarm(pendingIntent, calendar.getTimeInMillis());
	}

	public void setAlarm(PendingIntent pendingIntent, Long time) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
	}

}