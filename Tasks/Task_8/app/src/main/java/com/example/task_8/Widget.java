package com.example.task_8;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import static com.example.task_8.MainActivity.URI_KEY;

/**
 * Created by Lysen on 2018-01-01.
 */

public class Widget extends AppWidgetProvider {
    private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        remoteViews.setTextViewText(R.id.tv, sharedPref.getString(URI_KEY, ""));
        Log.d("PREFERENCES", sharedPref.getString(URI_KEY, ""));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.d("onUpdate", "onUpdate");

        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        watchWidget = new ComponentName(context, Widget.class);

        remoteViews.setOnClickPendingIntent(R.id.tv, getPendingSelfIntent(context, SYNC_CLICKED));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        remoteViews.setTextViewText(R.id.tv, sharedPref.getString(URI_KEY, ""));
        Log.d("PREFERENCES", sharedPref.getString(URI_KEY, ""));

        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (SYNC_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            watchWidget = new ComponentName(context, Widget.class);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String url = sharedPref.getString(URI_KEY, "");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

}
