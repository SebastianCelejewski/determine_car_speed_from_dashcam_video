package pl.sebcel.csfdv.domain;

/**
 * Represents a single data point in calculated car speed results.
 */
public class SpeedDataRow {

    private int frameIdx;
    private double time;
    private double speed;

    private double averagedSpeed;

    public SpeedDataRow(int frameIdx, double time, double speed, double averagedSpeed) {
        this.frameIdx = frameIdx;
        this.time = time;
        this.speed = speed;
        this.averagedSpeed = averagedSpeed;
    }

    public void setAveragedSpeed(double averagedSpeed) {
        this.averagedSpeed = averagedSpeed;
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

    public double getAveragedSpeed() {
        return averagedSpeed;
    }
}