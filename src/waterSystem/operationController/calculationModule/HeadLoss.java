package waterSystem.operationController.calculationModule;

import waterSystem.WaterConditions;

import java.util.List;

public final class HeadLoss<E extends WaterConditions> implements CalculationModule<E> {
    private List<WaterConditions> inputData;
    private double internalDiameter;
    private double length;
    private double headLoss;
    private E calculatedValue;

    public HeadLoss(double internalDiameter, double length) {
        this.internalDiameter=internalDiameter;
        this.length=length;
}

    @Override
    public void importData(List<E> data) {
        this.inputData= (List<WaterConditions>) data;
    }

    @Override
    public void makeCalculation() {
        WaterConditions wc = inputData.get(0);
        headLoss=(Math.min(headLossByColebrooke(wc.getFlow()),headLossByHazen(wc.getFlow())));
        calculatedValue.setFlowAndPressure(wc.getFlow(),wc.getPressure()+headLoss);
    }

    @Override
    public E exportData() {
        return calculatedValue;
    }

    @Override
    public Object exportSecond() {
        return headLoss;
    }

    private double headLossByHazen(double flow){
        return -(8614910.22*Math.pow(flow, 1.76)*Math.pow(internalDiameter, -4.76)*length/1000);
    }

    private double headLossByColebrooke(double flow){
        return -(Math.pow(10,9)* Math.pow(internalDiameter, -4.87) * 1.131 * Math.pow(flow/135, 1.852)*length/10);
    }
}
