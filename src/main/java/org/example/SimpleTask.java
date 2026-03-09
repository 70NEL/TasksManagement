package org.example;

import java.io.Serializable;

public non-sealed class SimpleTask extends Task implements Serializable {
    private int startHour;
    private int endHour;

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setStartHour(int desiredStartHour) {
        this.startHour = desiredStartHour;
    }

    public void setEndHour(int desiredEndHour) {
        this.endHour = desiredEndHour;
    }

    @Override
    public int estimateDuration() {
        return getEndHour() - getStartHour();
    }
}
