package com.example.prrateekk.dialogflow_integration;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * Created by prrateekk on 18/1/18.
 */

public class AIListener implements ai.api.AIListener {
    TextView botTextView, senderTextView;
    Context mainActivityContext;
    public static final String TAG = AIListener.class.getName();

    AIListener(TextView bot, TextView sender, Context context) {
        botTextView = bot;
        senderTextView = sender;
        mainActivityContext = context;
    }

    @Override
    public void onResult(AIResponse result) {
        Result res = result.getResult();
        String intentID = res.getMetadata().getIntentId();

        senderTextView.setText(res.getResolvedQuery());
        botTextView.setText(res.getFulfillment().getSpeech());


        Log.i(TAG,"IntentID: " + intentID);

        try {
            if (intentID.equals(Constants.LEARNING_APP_INTENT)) {
                Log.i(TAG,"Opening Test Activity");
                Intent intent = new Intent(mainActivityContext, TestActivity.class);
                mainActivityContext.startActivity(intent);
            }
        }
        catch (Exception e) {
            Log.i(TAG, "Intent ID Found NULL!");
        }

    }

    @Override
    public void onError(AIError error) {
        Log.i(TAG,"RESULT ERROR: " + error.getMessage());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        Log.i(TAG, "Started");
    }

    @Override
    public void onListeningCanceled() {
        Log.i(TAG, "Cancelled");
    }

    @Override
    public void onListeningFinished() {

    }
}
