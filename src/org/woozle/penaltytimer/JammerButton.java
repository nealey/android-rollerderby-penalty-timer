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
        if (! paused && peer.running) {
            penalized = true;

            if (peer.penalized) {
                // 7.4.1 -- Add a minute
            } else {
                // 7.4 -- Take a minute away from the other side
                // if it goes negative, that's our time.
                long orem = peer.remaining() - 60000;

                if (orem < 0) {
                    set(-orem);
                    start();
                    peer.set(0);
                    peer.stop();
                } else {
                    peer.set(orem);
                }
                return;
            }
        }
        super.onClick(v);
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
