package waterSystem.operationController.communicationModule;

import waterSystem.FlowDirection;
import waterSystem.operationController.OperationController;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.*;

import static waterSystem.FlowDirection.*;

public class CommunicationModule implements Communication {

    private FlowDirection flowDirection;
    private final Map<CommunicationModule, Transfer> connectionsAfter = new HashMap<>();
    private final Map<CommunicationModule, Transfer> connectionsBefore = new HashMap<>();
    private int counterAfter = 0;
    private int counterBefore = 0;
    private final OperationController owner;
    private SplittingModule splittingModule;

    public CommunicationModule(OperationController owner) {
        this.flowDirection = DIRECT;
        this.owner = owner;
    }

    public FlowDirection getDirection() {
        return flowDirection;
    }


    public void addConnectionBefore(CommunicationModule existingElement) {
        this.connectionsBefore.put(existingElement, null);
    }

    public void addConnectionAfter(CommunicationModule existingElement) {
        existingElement.connectionsAfter.put(this, null);
    }

    @Override
    public void addConnectionTo(CommunicationModule existingElement) {
        addConnectionBefore(existingElement);
        addConnectionAfter(existingElement);
    }

    @Override
    public void removeConnectionTo(CommunicationModule newElement, CommunicationModule existingElement) {

    }

    @Override
    public void transfer(CommunicationModule sender, Transfer transfer) {
        this.getConnectionsByDirectionOpposite().put(sender, transfer);
        updateOppositeCounter(transfer.getId());
        sendUpdate();
    }

    @Override
    public void sendTransfer(Transfer transfer) {
        int number = connectionsBefore.isEmpty() ? ++counterBefore : getNumberByDirectionOpposite();
        splittingModule.setConnectionList(getConnectionsByDirection());
        Map<CommunicationModule, Transfer> transferMap = splittingModule.split(transfer);
        transferMap.forEach((k, v) -> {
            v.setId(number);
            k.transfer(this, v);
        });
    }

    public void sendUpdate() {
        if (isUpdateFinished()) {
            List<Transfer> list = new ArrayList<>(getConnectionsByDirectionOpposite().values());
            owner.update(list);
        }
    }

    public void setSplittingModule(SplittingModule splittingModule) {
        this.splittingModule = splittingModule;
        this.splittingModule.setConnectionList(connectionsAfter);
    }

    public void updateDirection(FlowDirection direction) {
        this.flowDirection = direction;
    }

    private Map<CommunicationModule, Transfer> getConnectionsByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.connectionsAfter : this.connectionsBefore;
    }

    private Map<CommunicationModule, Transfer> getConnectionsByDirectionOpposite() {
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

    private boolean isUpdateFinished() {
        int minUpdateCounter = getConnectionsByDirectionOpposite().values().stream()
                .mapToInt(Transfer::getId)
                .min()
                .getAsInt();
        return getNumberByDirectionOpposite() == minUpdateCounter;
    }
}
