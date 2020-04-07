package waterSystem.calculationStrategies;

import waterSystem.Pipeline;
import waterSystem.WaterConditions;

public final class HeadLoss implements CalculateStrategy {
    Pipeline pipeline;

    public HeadLoss(Pipeline pipeline) {
        this.pipeline = pipeline;
}

    @Override
    public void calculate() {
        WaterConditions waterConditions = pipeline.getConnections().getWaterConditionsOnIndex(0);
        double headLoss = Math.min(headLossByColebrooke(waterConditions.getFlow()),headLossByHazen(waterConditions.getFlow()));
        pipeline.getWaterConditions().setFlowAndPressure(waterConditions.getFlow(),waterConditions.getPressure()+headLoss);
    }

    private double headLossByHazen(double flow){
        return -(8614910.22*Math.pow(flow, 1.76)*Math.pow(pipeline.getPipelineModel().getInternalDiameter(), -4.76)*pipeline.getLength()/1000);
    }

    private double headLossByColebrooke(double flow){
        return -(Math.pow(10,9)* Math.pow(pipeline.getPipelineModel().getInternalDiameter(), -4.87) * 1.131 * Math.pow(flow/135, 1.852)*pipeline.getLength()/10);
    }
}
