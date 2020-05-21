package waterSystem.operationController.communicationModule;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

public interface Communication<E> {
    void addConnectionTo(CommunicationModule<E> existingElement);
    void removeConnectionTo(CommunicationModule<E> newElement, CommunicationModule<E> existingElement);
    void transfer(CommunicationModule<E> sender, NumberedUpdate<E> upd);
    void sendTransfer(E value);
}
