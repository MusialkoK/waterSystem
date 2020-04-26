package waterSystem;

import java.util.List;

public interface ValueRetrieval<E> {

    List<E> getBeforeValuesByDirection();
    Object getSecondValue();
}
