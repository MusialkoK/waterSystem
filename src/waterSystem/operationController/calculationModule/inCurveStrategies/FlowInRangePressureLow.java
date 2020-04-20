package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;

public class FlowInRangePressureLow extends CurveStrategy {

    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.setWaterConditionsByFlow(waterConditions.getFlow());
    }
}
