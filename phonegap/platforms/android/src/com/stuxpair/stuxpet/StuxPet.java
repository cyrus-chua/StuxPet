/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.stuxpair.stuxpet;

import java.util.Calendar;

import org.apache.cordova.Config;
import org.apache.cordova.DroidGap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class StuxPet extends DroidGap {
	StuxPetDB petData;
	PendingIntent pendingIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		petData = new StuxPetDB(this);
		checkPet();
		super.setIntegerProperty("splashscreen", R.drawable.splash);
		// Set by <content src="index.html" /> in config.xml
		super.loadUrl(Config.getStartUrl(), 3000);
		super.setIntegerProperty("splashscreen", R.drawable.splash2);
	}

	public void checkPet() {
		petData.open();
		if (!petData.hasPet()) {
			setNames();
			SharedPreferences species_names = getSharedPreferences("names", 0);
			String names[] = species_names.getString(StuxPetDBHelper.TYPE_BABY, "null").split(",");
			petData.insertPet(names[(int) (Math.random() * names.length)],
					StuxPetDBHelper.TYPE_BABY, Calendar.getInstance()
							.getTimeInMillis());
		}
		Intent intent = new Intent(this, StatsTrackerService.class);
		startService(intent);
		Log.i("stuxpet", "stats:" + petData.getStatsJSON());

		petData.close();
	}

	public void setNames() {
		SharedPreferences species_names = getSharedPreferences("names", 0);
		SharedPreferences.Editor editor = species_names.edit();
		editor.putString(
				StuxPetDBHelper.TYPE_BABY,
				"baby,infant,cherub,bawler,puny thing,small fry,newborn,suckling,crawler,chick,bundle,nursling");
		editor.putString(
				StuxPetDBHelper.TYPE_CHILD,
				"child,kid,minor,adolescent,brat,cub,lamb,preteen,sprout,urchin,toddler,whippersnapper");
		editor.putString(
				StuxPetDBHelper.TYPE_TEEN1,
				"teen,good boy,guai kia,mama's boy,teacher's pet,well-behaved,youngster,lad,prefect,class monitor");
		editor.putString(
				StuxPetDBHelper.TYPE_TEEN2,
				"delinquent,troublemaker,inexperienced,unsophisticated,immature,unfledged,naughty,bad boy,vandal,pai kia,rebel");
		editor.putString(StuxPetDBHelper.TYPE_OTAKU,
				"otaku,geek,gamer,dweeb,weirdo,trekkie,dork,hacker,introvert,forever alone");
		editor.putString(
				StuxPetDBHelper.TYPE_ATHLETE,
				"athlete,sportsman,runner,jumper,tracker,jock,brawny,fit,muscled,insane bolt,maria");
		editor.putString(
				StuxPetDBHelper.TYPE_SOCIALITE,
				"socialite,clubber,player,social animal,suave,gentleman,playboy,well-connected,businessman,extrovert");
		editor.commit();
	}

}
