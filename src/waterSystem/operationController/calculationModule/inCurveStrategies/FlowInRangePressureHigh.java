package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;

public class FlowInRangePressureHigh extends CurveStrategy {

    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.setWaterConditionsByFlow(waterConditions.getFlow());
    }
}
