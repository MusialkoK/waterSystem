package waterSystem;


import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.models.PumpsList;

public class Pump extends NetworkElement {
    private PumpsList pumpModel;
    private Curve waterCurve;


    public String getName() {
        return this.pumpModel.getName();
    }

    public void changeModelTo(ModelsLists model) {
        setParameters(model);
        sendMessage();
    }

    public void start(){
        double flow=20;
        setWaterConditions(flow,waterCurve.getPressureOnFlow(flow));
    }

    @Override
    protected void setParameters(ModelsLists model) {
        this.pumpModel = (PumpsList) model;
        this.waterCurve=pumpModel.getCurve();
    }

    @Override
    public void calculate() {

    }

    @Override
    public void sendMessage() {
        final String HELLO_PUMP_FORMAT ="pump %s reporting:\nMy ID: %d\nMy message: %s\n-------------------\n";
        System.out.printf(HELLO_PUMP_FORMAT,getName(),getIDNumber(),this.testField);

    }

}

