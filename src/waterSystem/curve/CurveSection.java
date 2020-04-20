package waterSystem.curve;

import waterSystem.WaterConditions;

public class CurveSection {

    private double coefficientA, coefficientB;
    private WaterConditions lowLimit, highLimit;

    public CurveSection(double startFlow, double startPressure, double endFlow, double endPressure) {
        this.lowLimit = new WaterConditions(startFlow, startPressure);
        this.highLimit = new WaterConditions(endFlow, endPressure);
        this.coefficientA = (endPressure - startPressure) / (endFlow - startFlow);
        this.coefficientB = startPressure - coefficientA * startFlow;
    }

    public CurveSection(WaterConditions point1, WaterConditions point2) {
        this.lowLimit = point1;
        this.highLimit = point2;
    }

    public double getCoefficientA() {
        return coefficientA;
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

    public boolean isFlowInRange(double flow) {
        return (flow >= lowLimit.getFlow() && flow <= highLimit.getFlow());
    }

    public boolean isPressureInRange(double pressure) {
        return (pressure >= highLimit.getPressure() && pressure <= lowLimit.getPressure());
    }

    public double getDistance(WaterConditions waterConditions) {
        return (-coefficientA * waterConditions.getFlow() + waterConditions.getPressure() - coefficientB) / (Math.pow(Math.pow(coefficientA, 2) + 1, 0.5));
    }

    public WaterConditions getClosestPoint(WaterConditions waterConditions) {
        double perpendicularCoefficientA = -1 / coefficientA;
        double perpendicularCoefficientB = waterConditions.getPressure() + waterConditions.getFlow() / coefficientA;
        double flow = (perpendicularCoefficientB - coefficientB) / (coefficientA - perpendicularCoefficientA);
        if(!isFlowInRange(flow)){
            flow = Math.abs(flow-getMinFlow())>Math.abs(flow-getMaxFlow())? getMaxFlow():getMinFlow();
        }
        double pressure = perpendicularCoefficientA * flow + perpendicularCoefficientB;
        return new WaterConditions(flow, pressure);
    }
}
