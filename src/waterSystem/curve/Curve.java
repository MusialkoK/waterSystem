package waterSystem.curve;

import waterSystem.FlowPressure;
import waterSystem.WaterConditions;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowPressure.*;

public class Curve {
    private List<CurveSection> waterCurve;
    private double minFlow, maxPressure, maxFlow, minPressure;

    public Curve(double[] values) {
        this.waterCurve = new ArrayList<>();
        this.minFlow = values[0];
        this.maxPressure = values[1];
        this.maxFlow = values[values.length - 2];
        this.minPressure = values[values.length - 1];
        for (int i = 0; i < values.length - 3; i += 2) {
            this.waterCurve.add(new CurveSection(values[i], values[i + 1], values[i + 2], values[i + 3]));
        }
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

    private int inRange(double value, double minRange, double maxRange) {
        if (value < minRange) {
            return -1;
        } else if (value > maxRange) {
            return 1;
        } else {
            return 0;
        }
    }
}
