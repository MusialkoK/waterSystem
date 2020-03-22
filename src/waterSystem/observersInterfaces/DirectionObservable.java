package waterSystem.observersInterfaces;

public interface DirectionObservable {

    void addObserver(DirectionObserver o);
    void deleteObserver(DirectionObserver o);
    void sendUpdate();
}
