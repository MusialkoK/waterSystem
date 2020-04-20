package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;

public class FlowLowPressureHigh extends CurveStrategy {


    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.getStartPoint();
        //possible to differ if curve is increasing
    }
}
