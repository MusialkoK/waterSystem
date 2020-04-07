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
        String oldPumpName = getName();
        setParameters(model);
        System.out.println("Pump changed " + oldPumpName + " --> " + getName() + "\n--------------------------\n");
    }

    public void start(){
        double flow=43;
        setWaterConditions(flow, getPressureOnFlow(flow));
        System.out.println("pump "+getName()+" started at: "+waterConditions.view());
        System.out.println("-------------------");
        sendUpdate();
    }

    private double getPressureOnFlow(double flow){
        return waterCurve.getPressureOnFlow(flow);
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
        final String HELLO_PUMP_FORMAT ="pump %s reporting:\nMy ID: %d\nWater conditions: %s\n";
        System.out.printf(HELLO_PUMP_FORMAT,getName(),getIDNumber(),waterConditions.view());
        System.out.println("-------------------");
    }
}

