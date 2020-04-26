package waterSystem;

import waterSystem.operationController.communicationModule.NumberedUpdate;

public interface Updates<E> {
    void update (NetworkElement sender, NumberedUpdate<E> upd);
    void sendUpdate(NetworkElement sender, E value);
}
