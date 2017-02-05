package pl.sebcel.csfdv.events;

public class FrameSelected {

    private int frameIdx;

    public FrameSelected(int frameIdx) {
        this.frameIdx = frameIdx;
    }

    public int getFrameIdx() {
        return frameIdx;
    }
}