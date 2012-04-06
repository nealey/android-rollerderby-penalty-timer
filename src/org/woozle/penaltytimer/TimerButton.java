package org.woozle.penaltytimer;

import android.content.Context;
import android.widget.*;
import android.view.View;
import android.view.View.*;
import android.view.Gravity;
import android.graphics.Color;
import android.os.SystemClock;
import java.util.*;

public class TimerButton
    implements OnClickListener, OnLongClickListener
{
    Button b;
    Timer      updateTimer;
    long startTime = 0;
    long duration = 0;
    public boolean running = false;
    public boolean paused = false;
    long now;

    static int releaseColor = Color.rgb(0, 127, 0);
    static int standColor = Color.rgb(127, 127, 0);
    static int normalColor = Color.BLACK;
    static int pauseColor = Color.GRAY;

    public TimerButton(Context context) {
        b = new Button(context);

        b.setText("--:--");
        b.setTextSize(24);
        b.setGravity(Gravity.CENTER);
        updateTimer = new Timer();
        b.setOnClickListener(this);
        b.setOnLongClickListener(this);
    }

    public long remaining() {
        if (running) {
            return duration - (now - startTime);
        } else {
            return duration;
        }
    }

    public String bstr(long remain, boolean tenths) {
        long   min    = Math.abs(remain / 60000);
        long   sec    = Math.abs(remain / 1000) % 60;
        String ret;

        // Has the timer run out?
        if (remain <= 0) {
            return "--:--";
        }


        ret = min + ":";
        if (sec < 10) {
            ret += "0";
        }
        ret += sec;
        if (tenths) {
            ret += ".";
            ret += Math.abs(remain / 100) % 10;
        }

        return ret;
    }

    public String str(long remain, boolean tenths) {
        return bstr(remain, tenths) + " B";
    }

    public void set(long t) {
        startTime = now;
        duration = t;
    }

    public void start() {
        if (! running) {
            startTime = now;
        }
        running = true;
    }

    public void stop() {
        if (running) {
            duration = remaining();
        }
        running = false;
    }

    public TextView getButton() {
        return b;
    }

    public void expireHook() {
    }

    public void update() {
        long r = remaining();

        if ((duration > 0) && (r <= 0)) {
            duration = 0;
            running = false;
            expireHook();
        }

        if (paused) {
            b.setTextColor(pauseColor);
        } else if (r <= 0) {
            b.setTextColor(releaseColor);
        } else if (r < 10000) {
            b.setTextColor(standColor);
        } else {
            b.setTextColor(normalColor);
        }
        b.setText(str(r, true));
    }

    public void pulse(long now) {
        this.now = now;
        update();
    }

    public void onClick(View v) {
        if (paused) {
            return;
        }
        set((remaining() + 60000) % (60 * 8 * 1000));
        start();
    }

    public boolean onLongClick(View v) {
        set(0);
        stop();
        expireHook();
        return true;
    }

    public void pause() {
        paused = true;
        stop();
    }

    public void resume() {
        paused = false;
        if (remaining() > 0) {
            start();
        }
    }
}

