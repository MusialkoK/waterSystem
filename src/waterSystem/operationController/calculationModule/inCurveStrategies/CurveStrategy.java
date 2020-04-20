package waterSystem.operationController.calculationModule.inCurveStrategies;

import waterSystem.WaterConditions;
import waterSystem.curve.Curve;

public abstract class CurveStrategy {
    protected Curve waterCurve;

    public abstract WaterConditions getWaterConditions(WaterConditions waterConditions);

    public WaterConditions getWaterConditions(double flow,double pressure){
        return getWaterConditions(new WaterConditions(flow,pressure));
    }

    public void setWaterCurve(Curve waterCurve){
        this.waterCurve=waterCurve;
    }
}
