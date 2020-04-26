package waterSystem;


import waterSystem.operationController.calculationModule.LastDevice;
import waterSystem.operationController.calculationModule.PassDirection;
import waterSystem.models.ModelsLists;


public class Pump extends NetworkElement {

    public String getName() {
        return this.model.getName();
    }

    public void changeModelTo(ModelsLists model) {
        String oldPumpName = getName();
        setModelParameters(model);
        System.out.println("Pump changed " + oldPumpName + " --> " + getName() + "\n--------------------------\n");
    }

    public void start() {
        double flow = 43;
        setWaterConditions(flow, getPressureOnFlow(flow));
        System.out.println("pump " + getName() + " started at: " + waterConditions.view());
        System.out.println("-------------------");
        sendFlowUpdate(getWaterConditions());
    }

    private double getPressureOnFlow(double flow) {
        return waterCurve.getPressureOnFlow(flow);
    }

    @Override
    protected void setModelParameters(ModelsLists model) {
        this.model = model;
        this.waterCurve = this.model.getCurve();
        this.setMultiplier(1);
        setCalculationParameters(new LastDevice<>(multiplier,waterCurve),new PassDirection<>());
    }

    @Override
    public void sendMessage() {
        final String HELLO_PUMP_FORMAT = "pump %s reporting:\nMy ID: %d\nWater conditions: %s\n";
        System.out.printf(HELLO_PUMP_FORMAT, getName(), getIDNumber(), waterConditions.view());
        System.out.println("-------------------");
    }
}

