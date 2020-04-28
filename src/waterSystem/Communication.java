package waterSystem;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

public interface Communication<E> {
    void update (CommunicationModule<E> sender, NumberedUpdate<E> upd);
    void sendUpdate(E value);
}
