package no.hin.rombooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logIn(View view)
    {
        EditText username = (EditText)findViewById(R.id.userName);
        EditText password = (EditText)findViewById(R.id.userPass);
        Log.e("Message:", "Successful. Username: " + username.getText() + " Password:" + password.getText());
    }
}
