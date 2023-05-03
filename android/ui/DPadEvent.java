package io.github.maku77.widget;

/**
 * Software DPad event.
 */
public class DPadEvent {
    public static final int ACTION_DOWN = 0x0001;
    public static final int ACTION_UP = 0x0002;
    public static final int DIR_LEFT = 0x0100;
    public static final int DIR_UP = 0x0200;
    public static final int DIR_RIGHT = 0x0400;
    public static final int DIR_DOWN = 0x0800;

    private final int mAction;
    private final int mDirection;

    public DPadEvent(int action, int direction) {
        mAction = action;
        mDirection = direction;
    }

    public int getAction() {
        return mAction;
    }

    public int getDirection() {
        return mDirection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DPadEvent ");
        switch (mDirection) {
            case DIR_LEFT: sb.append("{dir:LEFT, "); break;
            case DIR_UP: sb.append("{dir:UP, "); break;
            case DIR_RIGHT: sb.append("{dir:RIGHT, "); break;
            case DIR_DOWN: sb.append("{dir:BOTTOM, "); break;
            default: throw new RuntimeException("DPadEvent: Invalid Direction");
        }
        switch (mAction) {
            case ACTION_DOWN: sb.append("action:DOWN}"); break;
            case ACTION_UP: sb.append("action:UP}"); break;
            default: throw new RuntimeException("DPadEvent: Invalid Action");
        }
        return sb.toString();
    }
}
