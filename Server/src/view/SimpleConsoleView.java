package view;

import model.ServerModel;
import model.UserType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

public class SimpleConsoleView implements PropertyChangeListener, Runnable
{
  private ServerModel model;
  public SimpleConsoleView(ServerModel model)
  {
    model.addListener(this);
    this.model = model;
  }

  @Override public void propertyChange(PropertyChangeEvent propertyChangeEvent)
  {
    System.out.println("server>> " + propertyChangeEvent.getNewValue());
  }

  @Override
  public void run() {
    Scanner kb = new Scanner(System.in); String readLine = "";
    while (!(readLine=kb.nextLine()).equalsIgnoreCase("exit")){
      String[] arguments = readLine.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
      try{switch (arguments[0].toLowerCase()){
        case "add":
          model.addUser(arguments[1], arguments[2].replaceAll("\"", ""), UserType.values()[Integer.parseInt(arguments[3])], arguments[4]);break;
        case "remove":
          model.removeUser(arguments[1]);break;
        case "help":
          System.out.println("list of commands:\nadd [username] [full name] [userType] [password]\nremove [username]");break;
        default:
          System.out.println("invalid command, type help for list of commands");break;
      }}catch (Exception e){ System.out.println("error");}
    }
  }
}
