package jp.xxxxxxxx.kanalab.enstar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private Switch startSwitch;
    private TextView timerView;
    private int mintime =25;
    private int setTime = mintime * 60000 + 7200000;
    private MyCountDownTimer cdt = new MyCountDownTimer(setTime, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerView = (TextView) findViewById(R.id.textView2);
        startSwitch= (Switch) findViewById(R.id.switch1);
        startSwitch.setOnCheckedChangeListener(this);

     }
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            timerView.setText("0:0:00");
            Toast.makeText(getApplicationContext(), "LPがたまりました", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            timerView.setText(Long.toString(millisUntilFinished/1000/60/60) + ":" +Long.toString(millisUntilFinished/1000/60%60) + ":" + Long.toString(millisUntilFinished/1000%60));
        }
    }
    //トグルスイッチの状態
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked == true) {
            //タイマーの起動
             cdt.start();
             NotificationCompat.Builder mBuilder =
                     (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                             .setSmallIcon(R.drawable.notification_icon)
                             .setContentTitle("あんスタタイマー")
                             .setContentText("タイマー起動中です");
            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());



        } else {
            //タイマーの停止
            cdt.cancel();
            timerView.setText("2:"+mintime+":00");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
