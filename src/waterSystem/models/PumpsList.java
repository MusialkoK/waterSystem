package waterSystem.models;


import waterSystem.WaterConditions;
import waterSystem.curve.Curve;

import java.util.ArrayList;
import java.util.List;

public enum PumpsList implements ModelsLists {
    SHE40_200_55("SHE40-200/55", 18, 4.64, 24, 4.38, 27, 4.2, 36, 3.62, 42, 3.1, 48, 2.5),
    SHE40_200_75("SHE40-200/75", 18, 5.51, 24, 5.23, 27, 5.08, 36, 4.5, 42, 4, 48, 3,45),
    NULL_PUMP("Null Pump");


    private String name;
    private double[] curvePointValues;

    PumpsList(String name, double... workingConditions) {
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
