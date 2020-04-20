package waterSystem;

import waterSystem.operationController.calculationModule.FinalDevice;
import waterSystem.operationController.calculationModule.PassDirection;
import waterSystem.models.ModelsLists;
import waterSystem.models.SprinklerList;

public class Sprinkler extends NetworkElement {

    public String getName() {
        return this.model.getName();
    }

    public void changeModelTo(SprinklerList model) {
        String oldSprinklerName = getName();
        setModelParameters(model);
        System.out.println("Sprinkler changed " + oldSprinklerName + " --> " + getName() + "\n--------------------------\n");
    }

    public void changeQuantityTo(int quantity){
        int oldQuantity=(int) this.multiplier;
        setMultiplier(quantity);
        System.out.println("Sprinkler "+getName()+" quantity changed " + oldQuantity + " --> " + this.multiplier + "\n--------------------------\n");
    }

    @Override
    public void sendMessage() {
        final String HELLO_SPRINKLER_FORMAT ="sprinkler %s x%d reporting:\nMy ID: %d\nWater conditions: %s\n";
        System.out.printf(HELLO_SPRINKLER_FORMAT,getName(),(int) multiplier ,getIDNumber(),waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    protected void setModelParameters(ModelsLists model) {
        this.model = model;
        this.waterCurve= this.model.getCurve();
        setCalculationParameters(new FinalDevice<>(multiplier,waterCurve),new PassDirection<>());
    }
}
