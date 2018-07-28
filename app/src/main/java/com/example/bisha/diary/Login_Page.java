package com.example.bisha.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;

public class Login_Page extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    String final_pattern = "";
    PatternLockView mPatternLockView;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        final String save_Pattern = Paper.book().read(save_pattern_key);
        if(save_Pattern != null && !save_Pattern.equals("null"))
        {
            setContentView(R.layout.activity_screen_pattern);

            date = (TextView) findViewById(R.id.datePattern);
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            date.setText(currentDate);

            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {

                    final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                    if(final_pattern.equals(save_Pattern)){
                        Toast.makeText(Login_Page.this, "Correct Pattern", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login_Page.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(Login_Page.this, "Incorrect Pattern", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        } else {
            startActivity(new Intent(Login_Page.this,Start_Page.class));
            finish();
        }

    }
}
