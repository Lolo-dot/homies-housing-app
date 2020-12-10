package humber.college.homies;
//Team Name: Homies
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Message_page extends Fragment {
    public String num;
    public String usr_msg;
    public CardView send_msg_btn,call_btn;
    public EditText get_msg;
    public TextView disp_num;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);

        // Intializing the buttons and textfield
        send_msg_btn = (CardView) view.findViewById(R.id.send_msg);
        call_btn = (CardView) view.findViewById(R.id.call_btn);
        get_msg = (EditText) view.findViewById(R.id.msg_box);
        disp_num = (TextView) view.findViewById(R.id.dis_msg_num);

        // Code for updating buttons text
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        num = sharedPreferences.getString(getString(R.string.number_key_pref), null);

        if(num==null){
            disp_num.setText("Select Contact");
            send_msg_btn.setEnabled(false);
        }else {
            // Update Text for user
            update_num();
        }


        // Code for sending message
        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_msg = get_msg.getText().toString();
                // Message to send message through button
                if(validations(usr_msg)) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSMS(num, get_msg.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), get_msg.getText().toString(), Toast.LENGTH_LONG).show();
                        Snackbar.make(getView().findViewById(R.id.msg_layout),R.string.msg_fail, Snackbar.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
                        }
                    }
                }
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + num));
                    startActivity(intent);
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
                    }
                }
            }
        });
        return view;
    }//end of oncreate



    // Code for sending message
    public boolean validations(String msg){
        if(msg.length()==0)
        {
            get_msg.requestFocus();
            get_msg.setError("FIELD CANNOT BE EMPTY");
            return false;
        }else if(msg.length()>160) {
            get_msg.requestFocus();
            get_msg.setError("Max Length 160");
            return false;
        }
        return true;
    }

    public void update_num(){
        // Setting up num for user

        String number = getResources().getString(R.string.to)+num;
        disp_num.setText(number);
        //Toast.makeText(getActivity(),number, Toast.LENGTH_SHORT).show();
    }

    protected void sendSMS(String phoneNo, String message) {
        // TODO Auto-generated method stub
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        getActivity().registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_success, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_gen_fail, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_no_service, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_null, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_radio_off, Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        getActivity().registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.msg_delv, Snackbar.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Snackbar.make(getView().findViewById(R.id.msg_layout), R.string.sms_not_del, Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
    }

}//end of code