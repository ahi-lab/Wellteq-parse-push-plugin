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

package au.com.nib.wellwithnib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParsePushBroadcastReceiver;

import org.apache.cordova.*;

import github.taivo.parsepushplugin.ParsePushPluginReceiver;

public class MainActivity extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        Log.d("MainActivity", String.valueOf(extras));
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }
        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        Log.d("MainActivity ONResume", String.valueOf(extras));
        if (extras != null && extras.getString("action") != null && extras.getString("action").equalsIgnoreCase("openPN")) {
            Intent intent = new Intent(ParsePushBroadcastReceiver.ACTION_PUSH_OPEN);
            intent.putExtras(extras);
            Log.d("MainActivity ONResume", extras.getString("action"));
            // Set the package name to keep this intent within the given package.
            Context context = Parse.getApplicationContext();
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
