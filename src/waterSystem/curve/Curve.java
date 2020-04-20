package waterSystem.curve;

import waterSystem.FlowPressure;
import waterSystem.WaterConditions;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowPressure.*;
import static waterSystem.curve.CurveZones.*;

public class Curve {
    private List<CurveSection> waterCurve;
    private double minFlow, maxPressure, maxFlow, minPressure;
    private boolean isIncreasing;
    private WaterConditions startPoint, midPoint, endPoint;

    public Curve(double[] values) {
        this.waterCurve = new ArrayList<>();
        this.minFlow = values[0];
        this.maxPressure = Math.max(values[1],values[values.length - 1]);
        this.maxFlow = values[values.length - 2];
        this.minPressure = Math.min(values[1],values[values.length - 1]);
        for (int i = 0; i < values.length - 3; i += 2) {
            this.waterCurve.add(new CurveSection(values[i], values[i + 1], values[i + 2], values[i + 3]));
        }
        isIncreasing = waterCurve.get(0).getCoefficientA() > 0;
        setCharacteristicPoints();
    }

    public WaterConditions getStartPoint() {
        return startPoint;
    }

    public WaterConditions getMidPoint() {
        return midPoint;
    }

    public WaterConditions getEndPoint() {
        return endPoint;
    }

    public boolean isIncreasing() {
        return isIncreasing;
    }

    public double getFlowOnPressure(double pressure) {
        int inRange = inRange(pressure, minPressure, maxPressure);
        switch (inRange) {
            case 0:
                return findSection(PRESSURE, pressure).getFlowOnPressure(pressure);
            case 1:
                return minFlow;
            case -1:
                return maxFlow;
            default:
                return 0;
        }
    }

    public double getPressureOnFlow(double flow) {
        int inRange = inRange(flow, minFlow, maxFlow);
        switch (inRange) {
            case 0:
                return findSection(FLOW, flow).getPressureOnFlow(flow);
            case 1:
                return minFlow;
            case -1:
                return maxFlow;
            default:
                return 0;
        }
    }

    public WaterConditions setWaterConditionsByFlow(double flow) {
        return new WaterConditions(flow, getPressureOnFlow(flow));
    }

    public WaterConditions setWaterConditionsByPressure(double pressure) {
        return new WaterConditions(getFlowOnPressure(pressure), pressure);
    }

    public CurveZones getZoneOf(double flow, double pressure) {
        int flowRange = (inRange(flow, minFlow, maxFlow) * 3) + 3;
        int pressureRange = inRange(pressure, minPressure, maxPressure) + 1;
        int state = flowRange + pressureRange;
        switch (state) {
            case 0:
                return FLOW_LOW_PRESSURE_LOW;
            case 1:
                return FLOW_LOW_PRESSURE_IN_RANGE;
            case 2:
                return FLOW_LOW_PRESSURE_HIGH;
            case 3:
                return FLOW_IN_RANGE_PRESSURE_LOW;
            case 4:
                return FLOW_IN_RANGE_PRESSURE_IN_RANGE;
            case 5:
                return FLOW_IN_RANGE_PRESSURE_HIGH;
            case 6:
                return FLOW_HIGH_PRESSURE_LOW;
            case 7:
                return FLOW_HIGH_PRESSURE_IN_RANGE;
            case 8:
                return FLOW_HIGH_PRESSURE_HIGH;
            default:
                return null;
        }
    }

    public CurveZones getZoneOf(WaterConditions waterConditions) {
        return getZoneOf(waterConditions.getFlow(), waterConditions.getPressure());
    }

    private CurveSection findSection(FlowPressure flowPressure, double value) {
        int i = 0;
        if (FLOW.equals(flowPressure)) {
            do {
                if (waterCurve.get(i).isFlowInRange(value)) {
                    return waterCurve.get(i);
                } else i++;
            } while (i < waterCurve.size());
        } else {
            do {
                if (waterCurve.get(i).isPressureInRange(value)) {
                    return waterCurve.get(i);
                } else i++;
            } while (i < waterCurve.size());
        }
        return null;
    }

    public WaterConditions getClosestConditionsTo(WaterConditions waterConditions) {
        return findClosestCurveSection(waterConditions).getClosestPoint(waterConditions);
    }

    private int inRange(double value, double value1, double value2) {
        double minValue=Math.min(value1,value2);
        double maxValue=Math.max(value1,value2);
        if (value < minValue) {
            return -1;
        } else if (value > maxValue) {
            return 1;
        } else {
            return 0;
        }
    }

    private void setCharacteristicPoints() {
        startPoint = isIncreasing ? setWaterConditionsByFlow(minFlow) : setWaterConditionsByFlow(maxFlow);
        endPoint = !isIncreasing ? setWaterConditionsByFlow(minFlow) : setWaterConditionsByFlow(maxFlow);
        midPoint = setWaterConditionsByFlow((minFlow + maxFlow) / 2);
    }

    private CurveSection findClosestCurveSection(WaterConditions waterConditions){
        CurveSection currentClosestSection = waterCurve.get(0);
        double min=currentClosestSection.getDistance(waterConditions);
        for (CurveSection cs:waterCurve) {
            if(cs.getDistance(waterConditions)<min){
                currentClosestSection=cs;
                min=currentClosestSection.getDistance(waterConditions);
            }
        }
        return currentClosestSection;
    }

}
