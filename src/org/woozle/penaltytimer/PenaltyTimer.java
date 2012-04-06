package org.woozle.penaltytimer;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import android.widget.*;
import android.os.*;
import java.lang.Math;
import java.util.*;


public class PenaltyTimer extends Activity
{
    private Handler mHandler = new Handler();
    private JammerButton[] jbs = new JammerButton[2];
    private TimerButton[] tbs = new TimerButton[8];
    
    private Runnable pulse = new Runnable() {
        public void run() {
            long now = SystemClock.uptimeMillis();
            pulse(now);

            mHandler.postAtTime(this, now + 100);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Persistence p = (Persistence)getLastNonConfigurationInstance();
        TableLayout tl;
        View top;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tl = (TableLayout)findViewById(R.id.mainTable);
        top = findViewById(R.id.main);

        for (int i = 0; i < 4; i += 1) {
            TableRow tr = new TableRow(this);

            tl.addView(tr, i);

            for (int j = 0; j < 2; j += 1) {
                TimerButton b;
                TextView v;

                if (i == 0) {
                    JammerButton jb = new JammerButton(this);

                    if (p != null) {
                        jb.penalized = p.penalized[j];
                    }
                    jbs[j] = jb;
                    b = jb;
                } else {
                    b = new TimerButton(this);
                }

                if (p != null) {
                    b.set(p.remain[i*2 + j]);
                    if (p.paused) {
                        b.pause();
                    } else {
                        b.resume();
                    }
                }

                v = b.getButton();
                tr.addView(v);

                tbs[i*2 + j] = b;
            }
        }

        jbs[0].setOther(jbs[1]);
        jbs[1].setOther(jbs[0]);

        mHandler.postAtTime(pulse, 100);
    }

    class Persistence {
        public long[] remain = new long[8];
        public boolean paused = false;
        public boolean[] penalized = new boolean[2];
    }


    public Object onRetainNonConfigurationInstance() {
        Persistence p = new Persistence();
        int i;

        for (i = 0; i < 8; i += 1) {
            TimerButton b = tbs[i];

            if (b.paused) {
                p.paused = true;
            }
            p.remain[i] = b.remaining();
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


    public void pauseButton(View v) {
        for (int i = 0; i < 8; i += 1) {
            tbs[i].pause();
        }
    }

    public void resumeButton(View v) {
        for (int i = 0; i < 8; i += 1) {
            tbs[i].resume();
        }
    }
}

