package org.woozle.penaltytimer;

import android.content.Context;
import android.widget.*;
import android.view.View;
import android.view.View.*;
import android.view.Gravity;
import android.graphics.ColorFilter;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.os.Vibrator;
import java.util.*;

public class TimerButton
    implements OnClickListener, OnLongClickListener
{
    Button b;
    Timer      updateTimer;
    public long startTime = 0;
    public long duration = 0;
    public boolean running = false;
    boolean shook = false;
    Vibrator vibr;
    long now;

    static ColorFilter releaseColor = null;
    static ColorFilter standColor   = new PorterDuffColorFilter(0xffffff88, PorterDuff.Mode.MULTIPLY);
    static ColorFilter normalColor  = new PorterDuffColorFilter(0xffff8888, PorterDuff.Mode.MULTIPLY);

    public TimerButton(Context context, Button btn, long now) {
        vibr = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        b = btn;
        b.setText("--:--");
        updateTimer = new Timer();
        b.setOnClickListener(this);
        b.setOnLongClickListener(this);

        this.now = now;
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
        return bstr(remain, tenths);
    }

    public void set(long t) {
        shook = false;
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
        ColorFilter cf;

        if ((duration > 0) && (r <= 0)) {
            duration = 0;
            running = false;
            shook = false;
            expireHook();
        }

        if (r <= 0) {
            cf = releaseColor;
        } else if (r < 10000) {
            if (running && ! shook) {
                vibr.vibrate(200);
                shook = true;
            }
            cf = standColor;
        } else {
            cf = normalColor;
        }
        b.getBackground().setColorFilter(cf);
        b.setText(str(r, true));
    }

    public void pulse(long now) {
        this.now = now;
        update();
    }

    public void onClick(View v) {
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
        stop();
        b.setEnabled(false);
    }

    public void resume() {
        b.setEnabled(true);
        if (remaining() > 0) {
            start();
        }
    }
}

