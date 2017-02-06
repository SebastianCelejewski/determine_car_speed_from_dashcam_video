package pl.sebcel.csfdv.events;

import pl.sebcel.csfdv.domain.SpeedDataRow;

import java.awt.*;
import java.util.List;

public class ResultsRecalculated {

    private List<SpeedDataRow> data;

    public ResultsRecalculated(List<SpeedDataRow> data) {
        this.data = data;
    }

    public List<SpeedDataRow> getData() {
        return data;
    }
}
