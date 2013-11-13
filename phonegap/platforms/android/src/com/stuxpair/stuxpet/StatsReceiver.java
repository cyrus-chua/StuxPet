package com.stuxpair.stuxpet;

import org.json.JSONException;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StatsReceiver extends BroadcastReceiver {

	StuxPetDB petData;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("StatsReceiver", "received Alarm");
		petData = new StuxPetDB(context);
		petData.open();
		String petName = "Your StuxPet";
		try {
			petName = petData.getStatsJSON().getString("species");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		petData.close();

		Bundle extras = intent.getExtras();

		if (extras.getBoolean("isZero")) {

			String message = "", message2 = "Tap here to";
			int icon = R.drawable.ic_launcher;
			switch (extras.getInt("action")) {
			case StatsTrackerService.MINUS_HEALTH:
				message = petName + " is sick :S";
				message2 = " give " + petName + " vitamins";
				icon = R.drawable.heart;
				break;
			case StatsTrackerService.MINUS_HUNGER:
				message = petName + " is hungry.";
				message2 = " feed " + petName;
				icon = R.drawable.meat;
				break;
			case StatsTrackerService.MINUS_HAPPY:
				message = petName + " is sad :(";
				message2 = " cheer " + petName + " up with a cake!";
				icon = R.drawable.smiley;
				break;
			}

			Notification pop = new Notification.Builder(context)
					.setSmallIcon(icon).setContentTitle(message)
					.setContentText(message2).build();

			((NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE)).notify(
					extras.getInt("action"), pop);

		} else {
			Intent statsTracker = new Intent(context, StatsTrackerService.class);
			statsTracker.putExtras(intent.getExtras());
			context.startService(statsTracker);

		}

	}

}
