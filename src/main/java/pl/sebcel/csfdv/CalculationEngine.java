package pl.sebcel.csfdv;

import pl.sebcel.csfdv.domain.Project;
import pl.sebcel.csfdv.domain.SpeedDataRow;
import pl.sebcel.csfdv.events.ProjectChanged;
import pl.sebcel.csfdv.events.ProjectOpened;
import pl.sebcel.csfdv.events.ResultsRecalculated;

import javax.enterprise.event.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.enterprise.event.Event;

/**
 * Recalculates car speed every time a project is loaded or modified
 */
@Singleton
public class CalculationEngine {

    @Inject
    private Event<ResultsRecalculated> resultsRecalculatedEvent;

    public void onProjectOpened(@Observes ProjectOpened projectOpened) {
        recalculate(projectOpened.getProject());
    }

    public void onProjectChanged(@Observes ProjectChanged projectChanged) {
        recalculate(projectChanged.getProject());
    }

    private void recalculate(Project project) {
        List<SpeedDataRow> data = new ArrayList<>();
        if (project != null && project.getSelectedFrames() != null && project.getSelectedFrames().size() > 1 && project.getFps() != null && project.getReferencePointDistance() != null) {
            project.getSelectedFrames().sort((x, y) -> (x - y));
            Queue<Integer> averagedSpeedBuffer = new ArrayDeque<Integer>();
            for (int i = 1; i < project.getSelectedFrames().size(); i++) {
                int startIdx = project.getSelectedFrames().get(i - 1);
                int endIdx = project.getSelectedFrames().get(i);
                int timeInFrames = endIdx - startIdx;
                double timeInSeconds = (double) endIdx / project.getFps();
                double durationInSeconds = (double) timeInFrames / project.getFps();
                double speedInMetersPerSecond = project.getReferencePointDistance() / durationInSeconds;
                int speedInKPH = (int) (speedInMetersPerSecond * 3.6);
                averagedSpeedBuffer.add(speedInKPH);
                if (averagedSpeedBuffer.size() > 5) {
                    averagedSpeedBuffer.poll();
                }
                double averagedSpeedInKPH = averagedSpeedBuffer.stream().collect(Collectors.averagingInt(x -> x.intValue()));
                data.add(new SpeedDataRow(endIdx, timeInSeconds, speedInKPH, averagedSpeedInKPH));
            }
        }

        resultsRecalculatedEvent.fire(new ResultsRecalculated(data));
    }
}