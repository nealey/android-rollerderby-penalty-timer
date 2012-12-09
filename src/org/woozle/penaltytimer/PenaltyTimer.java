package org.woozle.penaltytimer;

import android.app.Activity;
import android.view.*;
import android.widget.*;
import android.os.*;
import java.lang.Math;
import java.util.*;


public class PenaltyTimer extends Activity
{
    private Handler mHandler = new Handler();
    private JammerButton[] jbs = new JammerButton[2];
    private TimerButton[] tbs = new TimerButton[8];
    private boolean paused = false;

    private int[] btns = {
        R.id.jl, R.id.jr,
        R.id.b1l, R.id.b1r,
        R.id.b2l, R.id.b2r,
        R.id.b3l, R.id.b3r,
    };

    
    private Runnable pulse = new Runnable() {
        public void run() {
            long now = SystemClock.uptimeMillis();
            pulse(now);

            mHandler.postAtTime(this, now + 100);
        }
    };

    class Persistence {
        public boolean paused = false;
        public long[] startTime = new long[8];
        public long[] duration = new long[8];
        public boolean[] running = new boolean[8];
        public boolean[] penalized = new boolean[2];
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Persistence p = (Persistence)getLastNonConfigurationInstance();
        long now = SystemClock.uptimeMillis();
        LinearLayout top;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 0, (float)2.0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        top = (LinearLayout)findViewById(R.id.main);

        // Set up all the buttons
        for (int i = 0; i < 8; i += 1) {
            Button btn = (Button)findViewById(btns[i]);
            TimerButton b;
            TextView v;

            if (i < 2) {
                JammerButton jb = new JammerButton(this, btn, now);
                if (p != null) {
                    jb.penalized = p.penalized[i];
                }
                jbs[i] = jb;
                b = jb;
            } else {
                b = new TimerButton(this, btn, now);
            }

            if (p != null) {
                b.startTime = p.startTime[i];
                b.duration = p.duration[i];
                b.running = p.running[i];
            }

            tbs[i] = b;
        }


        // Invert value, then simulate a button press
        if (p != null) {
            paused = ! p.paused;
        } else {
            paused = true;
        }
        pauseButton();

        jbs[0].setOther(jbs[1]);
        jbs[1].setOther(jbs[0]);

        mHandler.postAtTime(pulse, 100);

    }



    public Object onRetainNonConfigurationInstance() {
        Persistence p = new Persistence();
        int i;

        for (i = 0; i < 8; i += 1) {
            TimerButton b = tbs[i];

            p.paused = paused;
            p.startTime[i] = b.startTime;
            p.duration[i] = b.duration;
            p.running[i] = b.running;
            if (i < 2) {
                JammerButton j = (JammerButton)b;

                p.penalized[i] = j.penalized;
            }
        }

        return p;
    }

    private void pulse(long now) {
        for (int i = 0; i < 8; i += 1) {
            tbs[i].pulse(now);
        }
    }


    public void pauseButton() {
        Button e = (Button) findViewById(R.id.pause);

        paused = !paused;
        for (int i = 0; i < 8; i += 1) {
            if (paused) {
                tbs[i].pause();
            } else {
                tbs[i].resume();
            }
        }
        if (paused) {
            e.setText(R.string.jam_begin);
        } else {
            e.setText(R.string.jam_end);
        }
    }

    public void pauseButton(View v) {
        pauseButton();
    }
}

