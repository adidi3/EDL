package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
/**
 * @author Adi Eisenberg
 * the first activity, passes after 5 seconds
 */
public class MainActivity extends AppCompatActivity {
    Timer timer;
    @Override
    /**
     * the function passes after 5 seconds to the login\register activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, start.class );
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
