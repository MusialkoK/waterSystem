package waterSystem.models;


import waterSystem.curve.Curve;

public enum SprinklerList implements ModelsLists {
    R_VAN_SST_1_5x9_1("R-VAN-SST 1,5x9,1m", 0.082,2.1,0.1,2.4,0.104,2.8,0.109,3.1,0.113,3.4,0.127,3.8),
    NULL_SPRINKLER("Null Sprinkler");


    private String name;
    private double[] curvePointValues;

    SprinklerList(String name, double... workingConditions) {
        this.name = name;
        this.curvePointValues = workingConditions;

    }

    public String getName() {
        return name;
    }

    public Curve getCurve() {
        return new Curve(curvePointValues);
    }
}
