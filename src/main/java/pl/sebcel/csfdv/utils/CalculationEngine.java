package pl.sebcel.csfdv.utils;

import pl.sebcel.csfdv.domain.Project;
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
        List<Point> data = new ArrayList<Point>();
        System.out.println("B " + project);
        if (project != null && project.getSelectedFrames() != null && project.getSelectedFrames().size() > 1 && project.getFps() != null && project.getReferencePointDistance() != null) {
            System.out.println("C");
            project.getSelectedFrames().sort((x, y) -> (x - y));
            for (int i = 1; i < project.getSelectedFrames().size(); i++) {
                int startIdx = project.getSelectedFrames().get(i - 1);
                int endIdx = project.getSelectedFrames().get(i);
                int timeInFrames = endIdx - startIdx;
                double timeInSeconds = (double) timeInFrames / project.getFps();
                double speedInMetersPerSecond = project.getReferencePointDistance() / timeInSeconds;
                int speedInKPH = (int) (speedInMetersPerSecond * 3.6);
                data.add(new Point(endIdx, speedInKPH));
                System.out.println(endIdx + ";" + speedInKPH);
            }
        }

        resultsRecalculatedEvent.fire(new ResultsRecalculated(data));
    }
}
