package waterSystem.observersInterfaces;

public interface LinkObservable {

    void addObserver(LinkObserver o);
    void deleteObserver(LinkObserver o);
    void sendUpdate();
}