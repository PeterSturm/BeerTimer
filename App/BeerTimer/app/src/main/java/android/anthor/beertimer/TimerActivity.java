package android.anthor.beertimer;

import android.anthor.beertimer.http.NetworkManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;


public class TimerActivity extends Activity implements NetworkManager.NetworkManagerListener, SensorEventListener {

    private Button btnStartStop;
    private Button btnAgain;
    private TextView timer_text;
    private TextView sensorState;
    private boolean isSave;

    private long timeStart;
    private String time;
    private boolean isStarted;

    private NetworkManager client = new NetworkManager(this);
    private final String BASE_URL = "http://beertimer.16mb.com/addscore.php?name=";

    private SensorManager sensormanager;
    private Sensor accelerometer;
    private boolean isenable;
    private double sensitivity;
    private long waitingtime;

    private SharedPreferences prefs;

    private final Runnable timer = new Runnable() {
        @Override
        public void run() {
            long time_temp = System.currentTimeMillis() - timeStart;
            long min = (time_temp / 1000) / 60;
            long sec = (time_temp / 1000) % 60;
            long msec = (time_temp - (time_temp / 1000)) % 1000 ;
            time = String.format("%02d:%02d:%03d", min, sec, msec);
            timer_text.setText(time);
            timerHandler.post(timer);
        }
    };

    private Handler timerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerHandler = new Handler();

        isStarted = false;
        isSave = false;


        timer_text = (TextView) findViewById(R.id.tv_Timer);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        sensitivity = (double) prefs.getFloat("sensitivity", 2.0f);
        isenable = prefs.getBoolean("sensor", true);

        btnStartStop = (Button) findViewById(R.id.btnStartStop);
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSave)
                {
                    if(!isStarted)
                        Start();
                    else
                        Stop();
                }
                else
                {
                    if(isConnectionEnabled()) {
                        SendTime(time);
                        Reset();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAgain = (Button) findViewById(R.id.btnAgain);
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

        sensorState = (TextView) findViewById(R.id.tv_sensor);
        if(isenable)
            sensorState.setText(R.string.txt_senon);
        else
               sensorState.setText(R.string.txt_senoff);

        if(isenable) {
            sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isenable)
            sensormanager.registerListener(this, accelerometer, sensormanager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isenable)
            sensormanager.unregisterListener(this);
    }

    public void Start()
    {
        isStarted = true;
        timeStart = System.currentTimeMillis();
        btnStartStop.setText(R.string.btnStop);
        timerHandler.post(timer);
    }

    public void Stop()
    {
        isStarted = false;
        timerHandler.removeCallbacks(timer);

        btnStartStop.setText(R.string.btnSave);
        btnAgain.setVisibility(View.VISIBLE);
        btnAgain.setEnabled(true);
        isSave = true;

    }

    public void Reset()
    {
        timeStart = 0;
        btnStartStop.setText(R.string.btnStart);
        btnAgain.setEnabled(false);
        btnAgain.setVisibility(View.INVISIBLE);
        timer_text.setText(R.string.timer_default);
        isSave = false;
    }

    @Override
    public void responseArrived(String aResponse) {

    }

    @Override
    public void errorOccured(String aError) {

    }

    public void SendTime(String time)
    {
        String name = prefs.getString("name", "_anonymus_");
        final StringBuilder score = new StringBuilder(BASE_URL);
        score.append(URLEncoder.encode(name));
        score.append("&time=");
        score.append(time);

        new Thread() {
            public void run()
            {
                client.execute(score.toString());
            }
        }.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

        acceleration = Math.abs(acceleration - sensormanager.STANDARD_GRAVITY);

        if (acceleration >= sensitivity && (System.currentTimeMillis() - waitingtime) > 200)
        {
            if(!isSave)
            {
                if (isStarted)
                    Stop();
                else
                    Start();
            }
            waitingtime = System.currentTimeMillis();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isConnectionEnabled() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity.getActiveNetworkInfo() != null)
            if (connectivity.getActiveNetworkInfo().isConnected()) return true;
        return false;
    }
}
