package waterSystem.calculationStrategies;

import waterSystem.NetworkElement;
import waterSystem.WaterConditions;

import java.util.List;

public final class FinalDevice implements CalculateStrategy {
    NetworkElement networkElement;

    public FinalDevice(NetworkElement networkElement) {
        this.networkElement = networkElement;
    }

    @Override
    public void calculate() {
        List<WaterConditions> inWaters = networkElement.getConnections().getAllWaterConditions();
    }
}
