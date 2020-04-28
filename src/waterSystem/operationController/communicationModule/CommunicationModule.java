package waterSystem.operationController.communicationModule;

import waterSystem.Communication;
import waterSystem.FlowDirection;
import waterSystem.ValueObservable;
import waterSystem.operationController.OperationController;


import java.util.*;
import java.util.stream.Collectors;

import static waterSystem.FlowDirection.*;

public class CommunicationModule<E> implements Linked<E>, Communication<E>, ValueObservable<List<E>> {

    private FlowDirection flowDirection;
    private final Map<CommunicationModule<E>, NumberedUpdate<E>> connectionsAfter = new HashMap<>();
    private final Map<CommunicationModule<E>, NumberedUpdate<E>> connectionsBefore = new HashMap<>();
    private int counterAfter = 0;
    private int counterBefore = 0;
    private OperationController<E> owner;

    public CommunicationModule(OperationController<E> owner) {
        this.flowDirection = DIRECT;
        this.owner=owner;
    }

    public FlowDirection getDirection() {
        return flowDirection;
    }


    public void addConnectionBefore(CommunicationModule<E> existingElement) {
        this.connectionsBefore.put(existingElement, null);
    }

    public void addConnectionAfter(CommunicationModule<E> existingElement){
        existingElement.connectionsAfter.put(this,null);
    }

    @Override
    public void addConnectionTo(CommunicationModule<E> existingElement) {
        addConnectionBefore(existingElement);
        addConnectionAfter(existingElement);
    }

    @Override
    public void removeConnectionTo(CommunicationModule<E> newElement, CommunicationModule<E> existingElement) {

    }

    @Override
    public void update(CommunicationModule<E> sender, NumberedUpdate<E> upd) {
        this.getConnectionsByDirectionOpposite().put(sender, upd);
        updateOppositeCounter(upd.getUpdateNumber());

    }

    @Override
    public void sendUpdate(E upd) {
        int number = connectionsBefore.isEmpty()? ++counterBefore:getNumberByDirectionOpposite();
        NumberedUpdate<E> numberedUpdate = new NumberedUpdate<>(number,upd);
        getConnectionsByDirection().keySet().forEach(x->x.update(this, numberedUpdate));
    }

    @Override
    public void sendTransfer() {
        List<E> list= new ArrayList<>();
        if(isUpdateFinished()){
            list= getConnectionsByDirectionOpposite().values().stream()
                    .map(NumberedUpdate::getValue)
                    .collect(Collectors.toList());
        }
        owner.transfer(list);
    }

    public void updateDirection(FlowDirection direction){
        this.flowDirection=direction;
    }

    private Map<CommunicationModule<E>, NumberedUpdate<E>> getConnectionsByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.connectionsAfter : this.connectionsBefore;
    }

    private Map<CommunicationModule<E>, NumberedUpdate<E>> getConnectionsByDirectionOpposite() {
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
