package com.example.prrateekk.dialogflow_integration;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;

public class MainActivity extends AppCompatActivity {
    AIService aiService;
    AIListener aiListener;
    TextView botTextView, senderTextView;
    Context context;
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

        senderTextView = (TextView)findViewById(R.id.textView2);
        botTextView = (TextView)findViewById(R.id.textView3);
        context = this.getApplicationContext();

        aiListener = new AIListener(botTextView, senderTextView, context);
        final AIConfiguration config = new AIConfiguration(Constants.CLIENT_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(aiListener);

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    public void startListening(View view) {
        Log.i(TAG,"Listening Speech");
        aiService.startListening();

    }

    public void stopListening(View view) {
        Log.i(TAG, "Stopped Listening");
        aiService.stopListening();
    }
}
