package waterSystem.operationController.communicationModule;

import waterSystem.Updates;
import waterSystem.FlowDirection;
import waterSystem.NetworkElement;
import waterSystem.WaterConditions;


import java.util.*;
import java.util.stream.Collectors;

import static waterSystem.FlowDirection.*;

public class CommunicationModule<E> implements Linked, Updates<E> {

    private FlowDirection flowDirection;
    private final Map<NetworkElement, NumberedUpdate<E>> connectionsAfter = new HashMap<>();
    private final Map<NetworkElement, NumberedUpdate<E>> connectionsBefore = new HashMap<>();
    private int counterAfter = 0;
    private int counterBefore = 0;

    public CommunicationModule() {
        this.flowDirection = DIRECT;
    }

    public FlowDirection getDirection() {
        return flowDirection;
    }


    public void addConnectionBefore(NetworkElement existingElement) {
        this.connectionsBefore.put(existingElement, null);
    }

    public void addConnectionAfter(NetworkElement newElement){
        this.connectionsAfter.put(newElement,null);
    }

    @Override
    public void addConnectionTo(NetworkElement newElement, NetworkElement existingElement) {
        addConnectionBefore(existingElement);
        addConnectionAfter(newElement);
    }

    @Override
    public void removeConnectionTo(NetworkElement newElement, NetworkElement existingElement) {

    }

    @Override
    public void update(NetworkElement sender, NumberedUpdate<E> upd) {
        this.getConnectionsByDirectionOpposite().put(sender, upd);
        updateOppositeCounter(upd.getUpdateNumber());
    }

    @Override
    public void sendUpdate(NetworkElement sender, E upd) {
        int number = connectionsBefore.isEmpty()? ++counterBefore:getNumberByDirectionOpposite();
        NumberedUpdate<E> numberedUpdate = new NumberedUpdate<>(number,upd);
        getConnectionsByDirection().keySet().forEach(x->x.update(sender, numberedUpdate));
    }

    //@Override
    public List<E> getBeforeValuesByDirection() {
       if(isUpdateFinished()){
           return getConnectionsByDirectionOpposite().values().stream()
                   .map(NumberedUpdate::getValue)
                   .collect(Collectors.toList());
       }else{
           return new ArrayList<>();
       }
    }

    public void updateDirection(FlowDirection direction){
        this.flowDirection=direction;
    }

    private Map<NetworkElement, NumberedUpdate<E>> getConnectionsByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.connectionsAfter : this.connectionsBefore;
    }

    private Map<NetworkElement, NumberedUpdate<E>> getConnectionsByDirectionOpposite() {
        return (REVERSE.equals(this.flowDirection)) ? this.connectionsAfter : this.connectionsBefore;
    }

    private int getNumberByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.counterAfter : this.counterBefore;
    }

    private int getNumberByDirectionOpposite() {
        return (REVERSE.equals(this.flowDirection)) ? this.counterAfter : this.counterBefore;
    }

    private void setNumberByDirection(int counter) {
        if (DIRECT.equals(this.flowDirection)) {
            counterAfter = counter;
        } else {
            counterBefore = counter;
        }
    }

    private void setNumberByDirectionOpposite(int counter) {
        if (REVERSE.equals(this.flowDirection)) {
            counterAfter = counter;
        } else {
            counterBefore = counter;
        }
    }

    private void updateCounter(int counter) {
        if (this.getNumberByDirection() < counter) this.setNumberByDirection(counter);
    }

    private void updateOppositeCounter(int counter) {
        if (this.getNumberByDirectionOpposite() < counter) this.setNumberByDirectionOpposite(counter);
    }

    private boolean isUpdateFinished(){
        int minUpdateCounter = getConnectionsByDirectionOpposite().values().stream()
                .mapToInt(NumberedUpdate::getUpdateNumber)
                .min()
                .getAsInt();
        return getNumberByDirectionOpposite()==minUpdateCounter;
    }
}
