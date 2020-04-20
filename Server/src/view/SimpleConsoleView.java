package server.view;

import server.model.ServerModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SimpleConsoleView implements PropertyChangeListener
{
  public SimpleConsoleView(ServerModel model)
  {
    model.addListener(this);
  }

  @Override public void propertyChange(PropertyChangeEvent propertyChangeEvent)
  {
    System.out.println(">>>" + propertyChangeEvent.getNewValue());
  }
}
