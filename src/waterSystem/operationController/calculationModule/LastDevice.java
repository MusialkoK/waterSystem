package waterSystem.operationController.calculationModule;

import waterSystem.WaterConditions;
import waterSystem.operationController.calculationModule.inCurveStrategies.CurveStrategy;
import waterSystem.curve.Curve;
import waterSystem.curve.CurveZones;
import waterSystem.operationController.communicationModule.Transfer;


import java.util.List;
import java.util.stream.Collectors;

public final class LastDevice implements CalculationModule {
    private List<WaterConditions> inputData;
    private final double multiplier;
    private final Curve waterCurve;
    private final WaterConditions calculatedValue = new WaterConditions();

    public LastDevice(double multiplier, Curve waterCurve) {
        this.multiplier=multiplier;
        this.waterCurve=waterCurve;
    }

    @Override
    public void calculate(List<Transfer> data) {
        importData(data);
        makeCalculation();
    }

    @Override
    public Transfer exportData() {
        Transfer waterConditionTransfer = new Transfer();
        waterConditionTransfer.setWaterConditions(calculatedValue);
        waterConditionTransfer.setChangeDirection(true);
        return waterConditionTransfer;
    }

    private void makeCalculation() {
        double sumFlow = getFlowSum(inputData);
        double sumFlowPerElement = sumFlow/multiplier;
        double averagePressure = getAveragePressure(inputData);
        CurveZones zone = waterCurve.getZoneOf(sumFlowPerElement,averagePressure);
        CurveStrategy curveStrategy= (CurveStrategy) zone.getStrategy();
        curveStrategy.setWaterCurve(waterCurve);
        WaterConditions waterConditions = curveStrategy.getWaterConditions(sumFlowPerElement, averagePressure);
        calculatedValue.setFlowAndPressure(waterConditions.getFlow()*multiplier,waterConditions.getPressure());
    }

    private double getFlowSum(List<WaterConditions> list) {
        return list.stream().mapToDouble(WaterConditions::getFlow).sum();
    }

    private double getAveragePressure(List<WaterConditions> list) {
        return list.size() > 0 ? list.stream().mapToDouble(WaterConditions::getPressure).sum() / list.size() : 0;
    }

    private void importData(List<Transfer> transfer) {
            this.inputData = transfer.stream()
            .map(Transfer::getWaterConditions)
            .collect(Collectors.toList());
    }
}
