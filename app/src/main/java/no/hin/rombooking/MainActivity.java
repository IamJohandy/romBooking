package no.hin.rombooking;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class MainActivity extends AppCompatActivity
{
    private static String url_login = "http://localhost:63343/Android/login.php";
    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    private String myusername;
    private String mypassword;

    private ResponseMessage resMessage;

    private static final String URL_LOGIN = "https://kark.hin.no/~540195/android/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aktiver cookies:
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    public void logIn(View view)
    {
        EditText username = (EditText)findViewById(R.id.userName);
        EditText password = (EditText)findViewById(R.id.userPass);
        Log.e("Message:", "Username: " + username.getText() + " Password:" + password.getText());

        myusername = username.getText().toString();
        mypassword = password.getText().toString();

        new logIn().execute();
    }

    public void setMessage(String message)
    {
        TextView userMessage = (TextView) findViewById(R.id.userMessage);
        userMessage.setText(message);
    }

    class logIn extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Logging in...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected String doInBackground(String... args)
        {
            Gson gson = new Gson();

            String params = "username=" + myusername + "&password=" + mypassword;

            JSONObject response = jsonParser.makeHttpsRequest(URL_LOGIN, params);

            String temp = response.toString();

            final ResponseMessage message = gson.fromJson(temp, ResponseMessage.class);

            /*try {
                JSONObject jObj = new JSONObject(response);
                int i = jObj.getInt("success");
                String s = jObj.getString("message");
                JSONArray jsonArray = jObj.getJSONArray("array");
                jsonArray.toString();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
*/
            Log.e("Response", message.getMessage());

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    setMessage(message.getSuccess() + " " + message.getMessage());
                }
            });

            return message.getMessage();
        }

        @Override
        protected void onPostExecute(String url)
        {
            //super.onPostExecute(o);
            progressDialog.dismiss();
        }
    }
}


