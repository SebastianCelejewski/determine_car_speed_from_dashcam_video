package pl.sebcel.csfdv.events;

import java.awt.*;
import java.util.List;

public class ResultsRecalculated {

    private List<Point> data;

    public ResultsRecalculated(List<Point> data) {
        this.data = data;
    }

    public List<Point> getData() {
        return data;
    }
}
