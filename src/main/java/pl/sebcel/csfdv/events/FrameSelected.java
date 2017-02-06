package pl.sebcel.csfdv.events;

/**
 * Fired when movie clip frame selection is changes, either by a user, or when a project is loaded or created
 */
public class FrameSelected {

    private int frameIdx;

    public FrameSelected(int frameIdx) {
        this.frameIdx = frameIdx;
    }

    public int getFrameIdx() {
        return frameIdx;
    }
}