package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

import java.util.HashMap;
import java.util.Map;

public class SameToAll<E> implements SplittingModule<E> {


    private Map<CommunicationModule<E>, NumberedUpdate<E>> connectionList;

    @Override
    public void setConnectionList(Map<CommunicationModule<E>, NumberedUpdate<E>> connectionList) {
        this.connectionList = connectionList;
    }

    @Override
    public Map<CommunicationModule<E>, E> split(E upd) {
        Map<CommunicationModule<E>, E> result = new HashMap<>();
        connectionList.forEach((k, v) -> result.put(k, upd));
        return result;
    }
}
