package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.Transfer;

import java.util.HashMap;
import java.util.Map;

public class SameToAll implements SplittingModule {


    private Map<CommunicationModule, Transfer> connectionList;

    @Override
    public void setConnectionList(Map<CommunicationModule, Transfer> connectionList) {
        this.connectionList = connectionList;
    }

    @Override
    public Map<CommunicationModule, Transfer> split(Transfer transfer) {
        Map<CommunicationModule, Transfer> result = new HashMap<>();
        connectionList.forEach((k, v) -> result.put(k, transfer));
        return result;
    }
}
