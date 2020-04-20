package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;


public class FlowInRangePressureInRange extends CurveStrategy {

    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.getClosestConditionsTo(waterConditions);
    }
}
