package pl.sebcel.csfdv;

import pl.sebcel.csfdv.domain.Project;
import pl.sebcel.csfdv.domain.SpeedDataRow;
import pl.sebcel.csfdv.events.ProjectChanged;
import pl.sebcel.csfdv.events.ProjectOpened;
import pl.sebcel.csfdv.events.ResultsRecalculated;

import javax.enterprise.event.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.event.Event;

@Singleton
public class CalculationEngine {

    private Project project;

    @Inject
    private Event<ResultsRecalculated> resultsRecalculatedEvent;

    public void recalculate(@Observes ProjectOpened projectOpened) {
        this.project = projectOpened.getProject();
        recalculate();
    }

    public void recalculate(@Observes ProjectChanged projectChanged) {
        recalculate();
    }

    private void recalculate() {
        List<SpeedDataRow> data = new ArrayList<SpeedDataRow>();
        if (project != null && project.getSelectedFrames() != null && project.getSelectedFrames().size() > 1 && project.getFps() != null && project.getReferencePointDistance() != null) {
            project.getSelectedFrames().sort((x, y) -> (x - y));
            for (int i = 1; i < project.getSelectedFrames().size(); i++) {
                int startIdx = project.getSelectedFrames().get(i - 1);
                int endIdx = project.getSelectedFrames().get(i);
                int timeInFrames = endIdx - startIdx;
                double timeInSeconds = (double) endIdx / project.getFps();
                double durationInSeconds = (double) timeInFrames / project.getFps();
                double speedInMetersPerSecond = project.getReferencePointDistance() / durationInSeconds;
                int speedInKPH = (int) (speedInMetersPerSecond * 3.6);
                data.add(new SpeedDataRow(endIdx, timeInSeconds, speedInKPH));
            }
        }

        resultsRecalculatedEvent.fire(new ResultsRecalculated(data));
    }
}
