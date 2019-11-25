package com.example.smssendreceive2019;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        System.out.println("Received SMS");
        Toast.makeText(context, "SMS Message Received", Toast.LENGTH_SHORT).show();
        SmsMessage[] recMsg = null;
        String str = "", rPhoneNo = "", rAnswer = "";
        if (bundle != null) {
            //---Access the received SMS message ---
            Object[] pdus = (Object[]) bundle.get("pdus");
            recMsg = new SmsMessage[pdus.length];
            String correctAnswerFeedback = "Message received. You won!";
            String wrongAnswerFeedback = "Message received. That is not the correct answer.";
            for (int i = 0; i < recMsg.length; ++i){
                String format = bundle.getString("format");
                recMsg[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                rPhoneNo = recMsg[i].getOriginatingAddress();
                rAnswer = recMsg[i].getMessageBody();
                if(rAnswer.equals(correctAnswerFeedback) || rAnswer.equals(wrongAnswerFeedback)) {
                    rPhoneNo = "";
                    break;
                }
                str += "SMS Message received from: " + rPhoneNo;
                str += " -> ";
                str += "The answer is '" + rAnswer + "'.";
                str += "\n";
            }
            //---display the SMS message received---
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();

            if(rPhoneNo.length() > 0) {
                String answer = "HONDA";
                String sms = "";
                if(answer.toLowerCase().equals(rAnswer.toLowerCase())) {
                    sms = wrongAnswerFeedback;
                } else {
                    sms = correctAnswerFeedback;
                }

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(rPhoneNo, null, sms, null, null);
                    Toast.makeText(context,
                            "SMS Message Successfully Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(context,
                            "SMS Send Failed, error occurred!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
}
