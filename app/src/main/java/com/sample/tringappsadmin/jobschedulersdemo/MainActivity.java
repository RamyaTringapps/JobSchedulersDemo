package com.sample.tringappsadmin.jobschedulersdemo;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ComponentName myServiceComponent;
    MyService myService;
    Button buttonScheduleJob;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            myService = (MyService) msg.obj;
            myService.setUICallback(MainActivity.this);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myServiceComponent = new ComponentName(this, MyService.class);
        Intent myServiceIntent = new Intent(this, MyService.class);
        myServiceIntent.putExtra("messenger", new Messenger(myHandler));
        startService(myServiceIntent);

        buttonScheduleJob=(Button) findViewById(R.id.buttonScheduleJob);
        buttonScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
                builder.setRequiresCharging(true);
                /*builder.setRequiresDeviceIdle(true);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);*/
                myService.scheduleJob(builder.build());
            }
        });
    }

}
