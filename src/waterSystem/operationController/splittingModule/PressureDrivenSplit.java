package waterSystem.operationController.splittingModule;

import waterSystem.WaterConditions;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

import java.util.HashMap;
import java.util.Map;

public class PressureDrivenSplit<E extends WaterConditions> implements SplittingModule<E> {

    private Map<CommunicationModule<E>, NumberedUpdate<E>> connectionList;

    @Override
    public void setConnectionList(Map<CommunicationModule<E>, NumberedUpdate<E>> connectionList) {
        this.connectionList = connectionList;
    }

    @Override
    public Map<CommunicationModule<E>, E> split(E upd) {
        Map<CommunicationModule<E>, E> result = new HashMap<>();
        if(connectionList.containsValue(null)){
            connectionList.forEach((k, v) -> result.put(k, upd));
            return result;
        }

        connectionList.forEach((k, v) -> result.put(k, v.getValue()));

        double pressureSum = result.values().stream()
                .map(WaterConditions::getPressure)
                .mapToDouble(Double::doubleValue)
                .sum();

        result.forEach((k, v) -> {
            v.setFlow(v.getPressure() / pressureSum * upd.getFlow());
            v.setPressure(upd.getPressure());
        });

        return result;
    }


}
