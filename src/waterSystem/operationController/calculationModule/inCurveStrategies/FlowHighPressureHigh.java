package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;

public class FlowHighPressureHigh extends CurveStrategy {


    @Override
    public WaterConditions getWaterConditions(WaterConditions waterConditions) {
        return waterCurve.getEndPoint();
        //possible to differ if curve is increasing
    }
}
