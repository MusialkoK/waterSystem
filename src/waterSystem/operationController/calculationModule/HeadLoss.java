package waterSystem.operationController.calculationModule;

import waterSystem.WaterConditions;

import java.util.List;

public final class HeadLoss<E extends WaterConditions> implements CalculationModule<E> {
    private List<WaterConditions> inputData;
    private final double internalDiameter;
    private final double length;
    private double headLoss;
    private final E calculatedValue = (E) new WaterConditions();

    public HeadLoss(double internalDiameter, double length) {
        this.internalDiameter=internalDiameter;
        this.length=length;
}

    @Override
    public void calculate(List<E> data) {
        this.inputData= (List<WaterConditions>) data;
        makeCalculation();
    }

    public void makeCalculation() {
        WaterConditions wc = inputData.get(0);
        headLoss=(Math.min(headLossByColebrooke(wc.getFlow()),headLossByHazen(wc.getFlow())));
        calculatedValue.setFlowAndPressure(wc.getFlow(),wc.getPressure()+headLoss);
    }

    @Override
    public TransferObj<E> exportData() {
        return new TransferObj<>(calculatedValue,headLoss);
    }

    private double headLossByHazen(double flow){
        return -(8614910.22*Math.pow(flow, 1.76)*Math.pow(internalDiameter, -4.76)*length/1000);
    }

    private double headLossByColebrooke(double flow){
        return -(Math.pow(10,9)* Math.pow(internalDiameter, -4.87) * 1.131 * Math.pow(flow/135, 1.852)*length/10);
    }
}
