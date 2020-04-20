package waterSystem.operationController.communicationModule;

import java.util.List;

public interface Communication<E> {
    void addConnectionTo(List<Communication<E>> existingModules);
    void removeConnectionTo(Communication<E> existingModule);
    void sendUpdate(NumberedUpdate<E> upd);
    void update(Communication<E> sender, NumberedUpdate<E> upd);
    List<E> getBeforeValuesByDirection();
}

