package waterSystem.operationController.calculationModule.inCurveStrategies;


import waterSystem.WaterConditions;

public class FlowHighPressureInRange extends CurveStrategy {

    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.setWaterConditionsByPressure(waterConditions.getPressure());
    }
}
