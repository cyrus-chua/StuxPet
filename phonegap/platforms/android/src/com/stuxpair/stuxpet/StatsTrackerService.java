package com.stuxpair.stuxpet;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;

public class StatsTrackerService extends IntentService {

	private int stepsTaken = 0;
	private StuxPetDB petData = new StuxPetDB(this);
	public final static int MINUS_HEALTH = 1;
	public final static int MINUS_HUNGER = 2;
	public final static int MINUS_HAPPY = 3;
	public final static int PLUS_SHIT = 4;

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
		petData.open();
		if (intent.getExtras() != null) {
			Bundle extras = intent.getExtras();
			switch (extras.getInt("action")) {
			case MINUS_HEALTH:
				Log.i("statsTracker", "health -1");
				petData.updateStats("", 0, -1, 0, 0, 0);
				break;
			case MINUS_HUNGER:
				Log.i("statsTracker", "hunger -2");
				petData.updateStats("", 0, 0, -2, 0, 0);
				handleHunger();
				break;
			case MINUS_HAPPY:
				Log.i("statsTracker", "happiness -1");
				petData.updateStats("", 0, 0, 0, -1, 0);
				break;
			case PLUS_SHIT:
				Log.i("statsTracker", "shit +1");
				petData.updateStats("", 0, 0, 0, 0, 1);
				break;
			}
		} else {
			handleHealth();
			handleHunger();
			handleHappy();
			handleShit();
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

	public void handleHealth() {
		Intent health = new Intent(this, StatsReceiver.class);
		health.putExtra("action", MINUS_HEALTH);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MINUS_HEALTH,
				health, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// Time to add alarm
		calendar.add(Calendar.MINUTE, 2);
	
		setAlarm(pendingIntent, calendar.getTimeInMillis(), 2 * 60 * 1000);
	}

	public void handleHunger() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.MINUTE);
		if (hour < 9)
			calendar.set(Calendar.MINUTE, 9);
		else if (hour < 30)
			calendar.set(Calendar.MINUTE, 30);
		else if (hour < 45)
			calendar.set(Calendar.MINUTE, 45);
		else {
			calendar.add(Calendar.HOUR, 1);
			calendar.set(Calendar.MINUTE, 9);
		}

		calendar.set(Calendar.SECOND, 1);

		Intent hunger = new Intent(this, StatsReceiver.class);
		hunger.putExtra("action", MINUS_HUNGER);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MINUS_HUNGER,
				hunger, PendingIntent.FLAG_UPDATE_CURRENT);
		setAlarm(pendingIntent, calendar.getTimeInMillis(), 0);
	}

	public void handleHappy() {
		Intent happy = new Intent(this, StatsReceiver.class);
		happy.putExtra("action", MINUS_HAPPY);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MINUS_HAPPY,
				happy, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		// Time to add alarm
		calendar.add(Calendar.MINUTE, 2);

		setAlarm(pendingIntent, calendar.getTimeInMillis(), 2 *60 * 1000);
	}
	
	public void handleShit() {
		Intent health = new Intent(this, StatsReceiver.class);
		health.putExtra("action", PLUS_SHIT);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, PLUS_SHIT,
				health, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// Time to add alarm
		calendar.add(Calendar.MINUTE, 4);
	
		setAlarm(pendingIntent, calendar.getTimeInMillis(), 4 * 60 * 1000);
	}

	public void setAlarm(PendingIntent pendingIntent, long time, long recurrTime) {
		Log.i("current time", Calendar.getInstance().getTime().toString());
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		if (recurrTime == 0)
			alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
		else
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, recurrTime, pendingIntent);
	}

}