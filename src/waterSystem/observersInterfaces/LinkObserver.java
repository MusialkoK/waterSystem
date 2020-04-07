package waterSystem.observersInterfaces;

import waterSystem.WaterConditions;

import java.util.Map;

public interface LinkObserver {

    void update(LinkObservable observable, WaterConditions waterConditions);
}
