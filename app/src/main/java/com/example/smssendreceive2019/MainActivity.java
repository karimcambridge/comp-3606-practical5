package com.example.smssendreceive2019;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends Activity {
    private Button sendButton;
    private EditText mPhoneNo;
    private EditText mSMSText;
    private TextView timeView;
    private Button buttonWithBg;
    private String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    private int allBackgroundColor = Color.argb(255, 255, 255, 255);
    private boolean button4BackgroundState = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = (Button) findViewById(R.id.sendButton);
        mPhoneNo = (EditText) findViewById(R.id.mPhoneNo);
        mSMSText = (EditText) findViewById(R.id.mSMSText);
        timeView = (TextView) findViewById(R.id.systemTimeView);
        buttonWithBg = (Button) findViewById(R.id.button4);
        buttonWithBg.setBackgroundColor(getResources().getColor(R.color.colorRed));

        SmsReceiver SMSr = new SmsReceiver();
        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String phoneNo = mPhoneNo.getText().toString();
                String sms = mSMSText.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(),
                            "SMS Message Successfully Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS Send Failed, error occurred!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        timeView.setText(currentDateTimeString);
        //SmsReceiver SMSr = new SmsReceiver();
    }

    public void randomiseBackgroundColour(View view) {
        LinearLayout bgElement = (LinearLayout) findViewById(R.id.linearLayout1);

        Random rnd = new Random();
        allBackgroundColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        bgElement.setBackgroundColor(allBackgroundColor);
    }

    public void openGoogle(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com")));
    }

    public void toggleBackgroundImage(View view) {
        button4BackgroundState = !button4BackgroundState;
        if(button4BackgroundState) {
            buttonWithBg.setBackgroundResource(R.drawable.button_bg);
        } else {
            buttonWithBg.setBackgroundResource(0);
            buttonWithBg.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
    }

    public void exitApp(View view) {
        finish();
    }
}
