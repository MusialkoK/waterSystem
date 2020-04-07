package waterSystem;

import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.models.SprinklerList;

import java.util.ArrayList;

public class Sprinkler extends NetworkElement {
    private int quantity;
    private SprinklerList sprinklerModel;
    private Curve waterCurve;


    public String getName() {
        return this.sprinklerModel.getName();
    }

    public void changeModelTo(SprinklerList model) {
        String oldSprinklerName = getName();
        setParameters(model);
        System.out.println("Sprinkler changed " + oldSprinklerName + " --> " + getName() + "\n--------------------------\n");
    }

    public void changeQuantityTo(int quantity){
        int oldQuantity=this.quantity;
        setQuantity(quantity);
        System.out.println("Sprinkler "+getName()+" quantity changed " + oldQuantity + " --> " + this.quantity + "\n--------------------------\n");
    }

    @Override
    public void sendMessage() {
        final String HELLO_SPRINKLER_FORMAT ="sprinkler %s x%d reporting:\nMy ID: %d\nWater conditions: %s\n";
        System.out.printf(HELLO_SPRINKLER_FORMAT,getName(),quantity ,getIDNumber(),waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    protected void setParameters(ModelsLists model) {
        this.sprinklerModel = (SprinklerList) model;
        this.waterCurve=sprinklerModel.getCurve();
    }

    @Override
    public void calculate() {
        ArrayList<WaterConditions> temp = new ArrayList(connections.getObservables().values());
    }

    @Override
    protected void setQuantity(int quantity) {
        this.quantity=quantity;
    }
}
