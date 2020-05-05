package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

import java.util.Map;

public interface SplittingModule<E> {
    public void setConnectionList(Map<CommunicationModule<E>, NumberedUpdate<E>> connectionList);
    Map<CommunicationModule<E>, E> split(E upd);
}
