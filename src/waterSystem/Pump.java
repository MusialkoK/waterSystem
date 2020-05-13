package waterSystem;


import waterSystem.operationController.calculationModule.LastDevice;
import waterSystem.operationController.calculationModule.PassDirection;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.splittingModule.PressureDrivenSplit;
import waterSystem.operationController.splittingModule.SameToAll;


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
        setCalculationParameters(new LastDevice<>(multiplier,waterCurve),
                new PressureDrivenSplit<>(),
                new PassDirection<>());
    }

    @Override
    public void sendStatusMessage() {
        final String STATUS_PUMP_FORMAT = "pump %s reporting:\nMy ID: %d\nWater conditions: %s\n";
        System.out.printf(STATUS_PUMP_FORMAT, getName(), getIDNumber(), waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    public void sendHelloMessage() {
        final String HELLO_SPRINKLER_FORMAT ="ID:%d, %s\n";
        System.out.printf(HELLO_SPRINKLER_FORMAT,getIDNumber(), getName());
    }
}

