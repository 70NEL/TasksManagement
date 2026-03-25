package datamodel;

import static java.lang.Math.abs;

public non-sealed class SimpleTask extends Task{
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
        if(startHour == endHour) {
            return 0;
        }else if(startHour < endHour) {
            return getEndHour() - getStartHour();
        }else {
            return (24-getStartHour()) + getEndHour();
        }
    }
}
