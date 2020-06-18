package utility;

import java.beans.PropertyChangeListener;

public interface ObserverSubject
{
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
