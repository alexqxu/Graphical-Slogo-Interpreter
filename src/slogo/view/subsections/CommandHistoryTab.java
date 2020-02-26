package slogo.view.subsections;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.visualcontroller.VisualCommand;

public class CommandHistoryTab implements SubTab {

  private List<VisualCommand> myCommands;

  private VBox myVBox;

  public CommandHistoryTab() {
    myCommands = new ArrayList<>();
    myVBox = new VBox();
  }

  public void addCommand(VisualCommand command) {
    myCommands.add(command);
  }

  public void updateTab(){
    for (VisualCommand command: myCommands){
      myVBox.getChildren().add(getVisualizedCommand(command));
    }
  }

  private Text getVisualizedCommand(VisualCommand command) {
    return new Text(command.getString());
  }

  @Override
  public Tab getTab() {
    updateTab();
    return new Tab("Command History", myVBox);
  }
}
