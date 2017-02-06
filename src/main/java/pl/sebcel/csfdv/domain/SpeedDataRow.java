package pl.sebcel.csfdv.domain;

/**
 * Represents a single data point in calculated car speed results.
 */
public class SpeedDataRow {

    private int frameIdx;
    private double time;
    private double speed;

    public SpeedDataRow(int frameIdx, double time, double speed) {
        this.frameIdx = frameIdx;
        this.time = time;
        this.speed = speed;
    }

    public int getFrameIdx() {
        return frameIdx;
    }

    public double getTime() {
        return time;
    }

    public double getSpeed() {
        return speed;
    }
}