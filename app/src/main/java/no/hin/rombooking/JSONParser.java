package no.hin.rombooking;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Johan on 08.05.2016.
 */

public class JSONParser
{
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public String url_login = "https://kark.hin.no/~540195/android/login.php";

    public JSONParser()
    {

    }

    public String makeHttpsRequest(String... args)
    {
        try
        {
            URL url = new URL(url_login);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String params = "username=" + args[0] + "&password=" + args[1];

            writer.write(params);
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();

            is = (InputStream)connection.getContent();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        }
        catch (Exception e)
        {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return json;
        /*try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }*/
    }
}
