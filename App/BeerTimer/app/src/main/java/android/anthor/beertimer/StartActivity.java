package android.anthor.beertimer;

import android.anthor.beertimer.http.NetworkManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


public class StartActivity extends Activity implements NetworkManager.NetworkManagerListener {

    private EditText text_field;
    private Button btnOK;

    private NetworkManager client = new NetworkManager(this);
    private final String BASE_URL = "http://beertimer.site90.net/register.php?name=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final SharedPreferences prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        String name = prefs.getString("name", "_anonymus_");
        if(name.equals("_anonymus_")) {
            text_field = (EditText) findViewById(R.id.editText);
            btnOK = (Button) findViewById(R.id.btnOK);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = text_field.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(getApplicationContext(), "Without a username your scores will be saved as _anonymus_", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else if(name.length() > 10)
                        Toast.makeText(getApplicationContext(), "username length max 10 character!", Toast.LENGTH_LONG).show();
                    else if(!name.matches("[a-zA-Z]*"))
                        Toast.makeText(getApplicationContext(), "username can not contain special character!", Toast.LENGTH_LONG).show();
                    else {
                        if(isConnectionEnabled()) {
                            Registrate(name);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("name", name);
                            editor.commit();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(i);
            this.finish();
        }
    }

    @Override
    public void responseArrived(final String aResponse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(aResponse.startsWith("ERROR"))
                    Toast.makeText(getApplicationContext(), "ERROR, try again!", Toast.LENGTH_LONG).show();
                else if(aResponse.equals("taken"))
                {
                    Toast.makeText(getApplicationContext(), "This username already taken", Toast.LENGTH_LONG).show();
                }
                else if(aResponse.equals("ok"))
                {
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    @Override
    public void errorOccured(String aError) {
        Toast.makeText(getApplicationContext(), "ERROR, try again!", Toast.LENGTH_LONG).show();
    }

    public void Registrate(String name)
    {
        final StringBuilder regname = new StringBuilder(BASE_URL);
        regname.append(URLEncoder.encode(name));

        new Thread() {
            public void run()
            {
                client.execute(regname.toString());
            }
        }.start();
    }

    public boolean isConnectionEnabled() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity.getActiveNetworkInfo() != null)
            if (connectivity.getActiveNetworkInfo().isConnected()) return true;
        return false;
    }
}
