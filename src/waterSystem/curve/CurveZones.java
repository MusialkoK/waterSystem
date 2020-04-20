package waterSystem.curve;

import waterSystem.operationController.calculationModule.inCurveStrategies.*;

public enum CurveZones {
    FLOW_LOW_PRESSURE_HIGH(new FlowLowPressureHigh()),
    FLOW_IN_RANGE_PRESSURE_HIGH(new FlowInRangePressureHigh()),
    FLOW_HIGH_PRESSURE_HIGH(new FlowHighPressureHigh()),
    FLOW_LOW_PRESSURE_IN_RANGE(new FlowLowPressureInRange()),
    FLOW_IN_RANGE_PRESSURE_IN_RANGE(new FlowInRangePressureInRange()),
    FLOW_HIGH_PRESSURE_IN_RANGE(new FlowHighPressureInRange()),
    FLOW_LOW_PRESSURE_LOW(new FlowLowPressureLow()),
    FLOW_IN_RANGE_PRESSURE_LOW(new FlowInRangePressureLow()),
    FLOW_HIGH_PRESSURE_LOW(new FlowHighPressureLow());

    private Object strategy;

    CurveZones(Object strategy) {
        this.strategy = strategy;
    }

    public Object getStrategy() {
        return strategy;
    }
}
