package waterSystem;

import waterSystem.observersInterfaces.DirectionObserver;
import waterSystem.observersInterfaces.LinkObservable;
import waterSystem.observersInterfaces.LinkObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static waterSystem.FlowDirection.*;

public class Connections implements DirectionObserver {

    private FlowDirection flowDirection;
    private List<LinkObserver> directLinkObservers;
    private List<LinkObserver> reverseLinkObservers;
    protected Map<LinkObservable,WaterConditions> directObservables;
    protected Map<LinkObservable,WaterConditions> reverseObservables;

    public Connections() {
        this.directLinkObservers = new ArrayList<>();
        this.reverseLinkObservers = new ArrayList<>();
        this.directObservables = new HashMap<>();
        this.reverseObservables = new HashMap<>();
        this.flowDirection=DIRECT;
    }

    @Override
    public void update(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }

    public List<LinkObserver> getLinkObservers() {
        return (DIRECT.equals(this.flowDirection)) ? this.directLinkObservers : this.reverseLinkObservers;
    }

    public List<LinkObserver> getReverseLinkObservers(){
        return this.reverseLinkObservers;
    }

    public Map<LinkObservable,WaterConditions> getObservables(){
        return (DIRECT.equals(this.flowDirection)) ? this.directObservables : this.reverseObservables;
    }

    public void setObservables(LinkObservable observable, WaterConditions waterConditions) {
        Map<LinkObservable,WaterConditions> map=(DIRECT.equals(this.flowDirection)) ? this.directObservables : this.reverseObservables;
        map.put(observable, waterConditions);
    }

    public WaterConditions getWaterConditionsOnIndex(int index){
        ArrayList<WaterConditions> list = new ArrayList(getObservables().values());
        return list.get(index);
    }

    public double getFlowSum(){
        ArrayList<WaterConditions> list = new ArrayList(getObservables().values());
        return list.stream().mapToDouble(WaterConditions::getFlow).sum();
    }

    public List<WaterConditions> getAllWaterConditions(){
        return new ArrayList(getObservables().values());
    }
}
