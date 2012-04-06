package org.woozle.penaltytimer;

import android.content.Context;
import android.view.View;
import android.view.View.*;

public class JammerButton extends TimerButton
    implements OnClickListener, OnLongClickListener
{
    public boolean penalized = false;
    private JammerButton peer;

    public JammerButton(Context context) {
        super(context);
    }

    public String str(long remain, boolean tenths) {
        return bstr(remain, tenths) + " J";
    }

    public void setOther(JammerButton other) {
        peer = other;
    }

    public void expireHook() {
        penalized = true;
    }

    public void onClick(View v) {
        if (! paused) {
            long orem = peer.remaining();

            if ((! running) && (! penalized) && (orem > 0)) {
                orem -= 60000;
                if (orem < 0) {
                    set(-orem);
                    start();
                    peer.set(0);
                    peer.stop();
                    peer.penalized = true;
                } else {
                    peer.set(orem);
                }
            } else {
                super.onClick(v);
            }
        }
    }

    public void pause() {
        super.pause();
        penalized = false;
    }

    public void pulse(long now) {
        if ((! running) && (! peer.running)) {
            penalized = false;
        }
        super.pulse(now);
    }
}
