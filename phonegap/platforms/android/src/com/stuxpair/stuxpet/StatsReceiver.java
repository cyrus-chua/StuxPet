package com.stuxpair.stuxpet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StatsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent statsTracker = new Intent(context, StatsTrackerService.class);
		statsTracker.putExtras(intent.getExtras());
		context.startService(statsTracker);
		Log.d("StatsReceiver", "received Alarm");
	}

}
