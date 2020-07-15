package waterSystem.operationController.communicationModule;

import waterSystem.FlowDirection;
import waterSystem.operationController.OperationController;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.*;

import static waterSystem.FlowDirection.*;

public class CommunicationModule implements Communication {

    private FlowDirection flowDirection;
    private final Map<CommunicationModule, TransferBox> connectionsAfterElement = new HashMap<>();
    private final Map<CommunicationModule, TransferBox> connectionsBeforeElement = new HashMap<>();
    private int finishedReverseTransfers = 0;
    private int finishedDirectTransfers = 0;
    private int currentTransferNumber = 0;
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
        this.connectionsBeforeElement.put(existingElement, null);
    }

    public void addConnectionAfter(CommunicationModule existingElement) {
        existingElement.connectionsAfterElement.put(this, null);
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
    public void transfer(CommunicationModule sender, TransferBox transferBox) {
        this.getConnectionsByDirectionOpposite().put(sender, transferBox);
        updateOppositeCounter(transferBox.getId());
        sendUpdate();
    }

    @Override
    public void sendTransfer(TransferBox transferBox) {
        setCurrentTransferNumber();
        Map<CommunicationModule, TransferBox> transferMap = splittingModule.split(transferBox, getConnectionsByDirection());
        transferMap.forEach((k, v) -> {
            v.setId(currentTransferNumber);
            k.transfer(this, v);
        });
    }

    private void setCurrentTransferNumber() {
        switch (flowDirection) {
            case DIRECT:
                currentTransferNumber = connectionsBeforeElement.isEmpty() ? ++finishedDirectTransfers : finishedDirectTransfers;
                break;
            case REVERSE:
                currentTransferNumber = connectionsAfterElement.isEmpty() ? ++finishedReverseTransfers : finishedReverseTransfers;
                break;
        }
    }

    public void sendUpdate() {
        if (isUpdateFinished()) {
            List<TransferBox> list = new ArrayList<>(getConnectionsByDirectionOpposite().values());
            owner.update(list);
        }
    }

    public void setSplittingModule(SplittingModule splittingModule) {
        this.splittingModule = splittingModule;
    }

    public void updateDirection(FlowDirection direction) {
        this.flowDirection = direction;
    }

    private Map<CommunicationModule, TransferBox> getConnectionsByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.connectionsAfterElement : this.connectionsBeforeElement;
    }

    private Map<CommunicationModule, TransferBox> getConnectionsByDirectionOpposite() {
        return (REVERSE.equals(this.flowDirection)) ? this.connectionsAfterElement : this.connectionsBeforeElement;
    }

    private int getNumberByDirection() {
        return (DIRECT.equals(this.flowDirection)) ? this.finishedReverseTransfers : this.finishedDirectTransfers;
    }

    private int getNumberByDirectionOpposite() {
        return (REVERSE.equals(this.flowDirection)) ? this.finishedReverseTransfers : this.finishedDirectTransfers;
    }

    private void setNumberByDirection(int counter) {
        if (DIRECT.equals(this.flowDirection)) {
            finishedReverseTransfers = counter;
        } else {
            finishedDirectTransfers = counter;
        }
    }

    private void setNumberByDirectionOpposite(int counter) {
        if (REVERSE.equals(this.flowDirection)) {
            finishedReverseTransfers = counter;
        } else {
            finishedDirectTransfers = counter;
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
                .mapToInt(TransferBox::getId)
                .min()
                .getAsInt();
        return getNumberByDirectionOpposite() == minUpdateCounter;
    }
}
