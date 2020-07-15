package waterSystem.operationController.communicationModule;

public interface Communication {
    void addConnectionTo(CommunicationModule existingElement);
    void removeConnectionTo(CommunicationModule newElement, CommunicationModule existingElement);
    void transfer(CommunicationModule sender, TransferBox transfer);
    void sendTransfer(TransferBox transfer);
}
