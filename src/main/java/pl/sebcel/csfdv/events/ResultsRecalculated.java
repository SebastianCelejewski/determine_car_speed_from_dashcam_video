package pl.sebcel.csfdv.events;

import pl.sebcel.csfdv.domain.SpeedDataRow;

import java.util.List;

/**
 * Fired when car speed calculations are complete to notify any components that present the results
 */
public class ResultsRecalculated {

    private List<SpeedDataRow> data;

    public ResultsRecalculated(List<SpeedDataRow> data) {
        this.data = data;
    }

    public List<SpeedDataRow> getData() {
        return data;
    }
}
