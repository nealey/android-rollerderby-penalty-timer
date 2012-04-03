package org.woozle.penaltytimer;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;

public class PenaltyTimer extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onButtonClicked(View v) {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
    }
}
