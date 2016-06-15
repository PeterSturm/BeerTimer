package android.anthor.beertimer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;


import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends Activity{

    private SeekBar seekBar;
    private CheckBox checkbox;
    private EditText edittext;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        float sensitivity = prefs.getFloat("sensitivity", 2.0f);
        boolean isenable = prefs.getBoolean("sensor", true);

        seekBar = (SeekBar) findViewById(R.id.seeker);
        seekBar.setProgress(getProgress(sensitivity));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat("sensitivity", getSensitivity(progress));
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setChecked(isenable);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("sensor", isChecked);
                editor.commit();
            }
        });


        String currentname = prefs.getString("name", "anonymus");
        edittext = (EditText) findViewById(R.id.namefield);
        edittext.setText(currentname);
        edittext.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", edittext.getText().toString());
                editor.commit();
            }
        });
    }

    int getProgress(float value)
    {
        if(value == 0.25f)
            return 20;
        if(value == 0.3f)
            return 19;
        if(value == 0.35f)
            return 18;
        if(value == 0.4f)
            return 17;
        if(value == 0.45f)
            return 16;
        if(value == 0.5f)
            return 15;
        if(value== 0.75f)
            return 14;
        if(value == 1.0f)
            return 13;
        if(value == 1.5f)
            return 12;
        if(value == 2.0f)
            return 11;
        if(value == 2.5f)
            return 10;
        if(value == 3.0f)
            return 9;
        if(value == 3.5f)
            return 8;
        if(value == 4.5f)
            return 7;
        if(value == 5.5f)
            return 6;
        if(value == 6.5f)
            return 5;
        if(value == 7.5f)
            return 4;
        if(value == 8.5f)
            return 3;
        if(value == 9.5f)
            return 2;
        if(value == 10.5f)
            return 1;
        if(value == 12.0f)
            return 0;

        return 11;
    }

    float getSensitivity(int value)
    {
        if(value == 20)
            return 0.25f;
        if(value == 19)
            return 0.3f;
        if(value == 18)
            return 0.35f;
        if(value == 17)
            return 0.4f;
        if(value == 16)
            return 0.45f;
        if(value == 15)
            return 0.5f;
        if(value == 14)
            return 0.75f;
        if(value == 13)
            return 1.0f;
        if(value == 12)
            return 1.5f;
        if(value == 11)
            return 2.0f;
        if(value == 10)
            return 2.5f;
        if(value == 9)
            return 3.0f;
        if(value == 8)
            return 3.5f;
        if(value == 7)
            return 4.5f;
        if(value == 6)
            return 5.5f;
        if(value == 5)
            return 6.5f;
        if(value == 4)
            return 7.5f;
        if(value == 3)
            return 8.5f;
        if(value == 2)
            return 9.5f;
        if(value == 1)
            return 10.5f;
        if(value == 0)
            return 12.0f;

        return 2.0f;
    }
}
