package android.anthor.beertimer.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by anthor on 2015.05.13..
 */
public class NetworkManager
{
    public interface NetworkManagerListener {
        public void responseArrived(String aResponse);
        public void errorOccured(String aError);
    }

    private NetworkManagerListener listener;

    public NetworkManager(NetworkManagerListener aListener)
    {
        listener = aListener;
    }

    public void execute(String url)
    {
        org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
        InputStream is = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() ==
                    HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String resp = reader.readLine();
                    listener.responseArrived(resp);
                }
                else
                    listener.errorOccured("HttpEntity is empty");
            }
        } catch (Exception e) {
            listener.errorOccured(e.getMessage());
        } finally {
            if (is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
