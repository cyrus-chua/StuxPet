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

import android.content.SharedPreferences;
import android.os.Bundle;

public class StuxPet extends DroidGap {
	StuxPetDB petData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setIntegerProperty("splashscreen", R.drawable.splash);

		petData = new StuxPetDB(this);
		checkPet();

		// Set by <content src="index.html" /> in config.xml
		super.loadUrl(Config.getStartUrl(), 10000);
		//TODO: set up steps counter
		/*Intent intent = new Intent(this, StatsTrackerService.class);
		startService(intent);*/
	}

	public void checkPet(){
		petData.open();
    	if (petData.hasPet()){
    		
    	} else {
    		setNames();
    		SharedPreferences species_names = getSharedPreferences("names", 0);
    		String names[] = species_names.getString("baby", "null").split(",");
    		petData.insertPet(names[(int) (Math.random() * names.length)], Calendar.getInstance().getTimeInMillis());
    	}
    }
	
	public void setNames(){
		SharedPreferences species_names = getSharedPreferences("names", 0);
		SharedPreferences.Editor editor = species_names.edit();
		editor.putString("baby", "baby,infant,cherub,bawler,puny thing,small fry,newborn,suckling,crawler,chick,bundle,nursling");
		editor.putString("child", "child,kid,minor,adolescent,brat,cub,lamb,preteen,sprout,urchin,toddler,whippersnapper");
		editor.putString("teen", "teen,good boy,guai kia,mama's boy,teacher's pet,well-behaved,youngster,lad,prefect,class monitor");
		editor.putString("juvenile", "juvenile,troublemaker,inexperienced,unsophisticated,immature,unfledged,naughty,bad boy,vandal,pai kia,rebel");
		editor.putString("nerd", "otaku,geek,gamer,dweeb,weirdo,trekkie,dork,hacker,introvert,forever alone");
		editor.putString("athlete", "athlete,sportsman,runner,jumper,tracker,jock,brawny,fit,muscled,insane bolt,maria");
		editor.putString("socialite", "socialite,clubber,player,social animal,suave,gentleman,playboy,well-connected,businessman,extrovert");
		editor.commit();
	}
}
