package waterSystem.operationController.communicationModule;

import waterSystem.FlowDirection;

import java.util.*;
import java.util.stream.Collectors;

import static waterSystem.FlowDirection.*;

public class CommunicationModule<E> implements Communication<E> {

    private FlowDirection flowDirection;
    private final Map<Communication<E>, NumberedUpdate<E>> connectionsAfter = new HashMap<>();
    private final Map<Communication<E>, NumberedUpdate<E>> connectionsBefore = new HashMap<>();
    private int counterAfter = 0;
    private int counterBefore = 0;

    public CommunicationModule() {
        this.flowDirection = DIRECT;
    }

    public FlowDirection getDirection() {
        return flowDirection;
    }


    public void addConnectionTo(Communication<E> existingModule) {
        this.connectionsBefore.put(existingModule, null);
        CommunicationModule<E> existingModuleImpl = (CommunicationModule<E>) existingModule;
        existingModuleImpl.connectionsAfter.put(this, null);
    }

    @Override
    public void removeConnectionTo(Communication<E> existingModule) {
        this.connectionsBefore.remove(existingModule, null);
        CommunicationModule<E> existingModuleImpl = (CommunicationModule<E>) existingModule;
        existingModuleImpl.connectionsAfter.remove(this, null);
    }

    @Override
    public void update(Communication<E> sender, NumberedUpdate<E> upd) {
        this.getConnectionsByDirectionOpposite().put(sender, upd);
        updateOppositeCounter(upd.getUpdateNumber());
    }

    @Override
    public void sendUpdate(NumberedUpdate<E> upd) {
        getConnectionsByDirection().keySet().forEach(x->x.update(this,upd));
    }
    @Override
    public void addConnectionTo(List<Communication<E>> existingModules) {
        existingModules.forEach(this::addConnectionTo);
    }
    @Override
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

    private Map<Communication<E>, NumberedUpdate<E>> getConnectionsByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.connectionsAfter : this.connectionsBefore;
    }

    private Map<Communication<E>, NumberedUpdate<E>> getConnectionsByDirectionOpposite() {
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
