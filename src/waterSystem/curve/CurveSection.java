package waterSystem.curve;

import waterSystem.WaterConditions;

public class CurveSection {

    private double coefficientA, coefficientB;
    private WaterConditions lowLimit, highLimit;

    public CurveSection(double minFlow, double maxPressure, double maxFlow, double minPressure) {
        this.lowLimit=new WaterConditions(minFlow,maxPressure);
        this.highLimit=new WaterConditions(maxFlow,minPressure);
        this.coefficientA = (minPressure - maxPressure) / (maxFlow - minFlow);
        this.coefficientB = maxPressure - coefficientA * minFlow;
    }

    public CurveSection(WaterConditions point1, WaterConditions point2) {
        this.lowLimit=point1;
        this.highLimit= point2;
    }

    public double getMinFlow() {
        return lowLimit.getFlow();
    }

    public double getMaxPressure() {
        return lowLimit.getPressure();
    }

    public double getMaxFlow() {
        return highLimit.getFlow();
    }

    public double getMinPressure() {
        return highLimit.getPressure();
    }

    public double getFlowOnPressure(double pressure) {
        return (pressure - coefficientB) / coefficientA;
    }

    public double getPressureOnFlow(double flow) {
        return coefficientA * flow + coefficientB;
    }

    public boolean isFlowInRange(double flow){
        return (flow>=lowLimit.getFlow() && flow<=highLimit.getFlow());
    }

    public boolean isPressureInRange(double pressure){
        return (pressure>=highLimit.getPressure() && pressure<=lowLimit.getPressure());
    }
}
