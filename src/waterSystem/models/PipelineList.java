package waterSystem.models;

import waterSystem.curve.Curve;

public enum PipelineList implements ModelsLists {

    PE_50("PE50",50,40.8),
    PE_90("PE90",90,73.4);

    private String name;
    private double externalDiameter;
    private double internalDiameter;

    PipelineList(String name, double externalDiameter, double internalDiameter) {
        this.name = name;
        this.externalDiameter = externalDiameter;
        this.internalDiameter = internalDiameter;
    }

    public String getName() {
        return name;
    }

    @Override
    public Curve getCurve() {
        return null;
    }

    public double getExternalDiameter() {
        return externalDiameter;
    }

    public double getInternalDiameter() {
        return internalDiameter;
    }

    public double getArea(){
        return Math.PI*Math.pow(internalDiameter,2)/4;
    }
}
