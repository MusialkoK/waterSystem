package waterSystem.operationController.calculationModule;

import waterSystem.WaterConditions;
import waterSystem.operationController.calculationModule.inCurveStrategies.CurveStrategy;
import waterSystem.curve.Curve;
import waterSystem.curve.CurveZones;
import java.util.List;

public final class LastDevice<E extends WaterConditions> implements CalculationModule<E> {
    List<WaterConditions> inputData;
    double multiplier;
    Curve waterCurve;
    E calculatedValue;

    public LastDevice(double multiplier, Curve waterCurve) {
        this.multiplier=multiplier;
        this.waterCurve=waterCurve;
    }

    @Override
    public void importData(List<E> data) {
        this.inputData= (List<WaterConditions>) data;
    }

    @Override
    public void makeCalculation() {
        double sumFlow = getFlowSum(inputData);
        double sumFlowPerElement = sumFlow/multiplier;
        double averagePressure = getAveragePressure(inputData);
        CurveZones zone = waterCurve.getZoneOf(sumFlowPerElement,averagePressure);
        CurveStrategy curveStrategy= (CurveStrategy) zone.getStrategy();
        curveStrategy.setWaterCurve(waterCurve);
        WaterConditions waterConditions = curveStrategy.getWaterConditions(sumFlowPerElement, averagePressure);
        calculatedValue.setFlowAndPressure(waterConditions.getFlow()*multiplier,waterConditions.getPressure());
    }

    @Override
    public E exportData() {
        return calculatedValue;
    }

    @Override
    public Object exportSecond() {
        return null;
    }

    private double getFlowSum(List<WaterConditions> list) {
        return list.stream().mapToDouble(WaterConditions::getFlow).sum();
    }

    private double getAveragePressure(List<WaterConditions> list) {
        return list.size() > 0 ? list.stream().mapToDouble(WaterConditions::getPressure).sum() / list.size() : 0;
    }
}
