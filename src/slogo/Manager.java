package slogo;

import javafx.stage.Stage;
import slogo.logicalcontroller.LogicalController;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelCollection;
import slogo.model.ModelTurtle;
import slogo.view.windows.SlogoView;
import slogo.visualcontroller.VisualController;
import java.io.IOException;

/**
 * Purpose of this class is to manage the controllers and the model.
 * NOTE: Not complete and will only be continued to work on if we have time by basic implementation due date.
 */
public class Manager {
    private String myDefaultLang;

    private static final int ANIMATION_RATE = 3;

    private ModelCollection myModelCollection;
    private VariableList myVariables;

    private SlogoView mySlogoView;
    private VisualController myVisualController;
    private LogicalController myLogicalController;

    public Manager(String defaultLang) throws IOException {
        myDefaultLang = defaultLang;
        createModel();
        createVisualController();
        createLogicalController();
        createSlogoView();
        setViewControllerView();
        startView();
    }

    private void startView() throws IOException {
        mySlogoView.start(new Stage());
    }

    private void setViewControllerView() {
        myVisualController.setSlogoView(mySlogoView);
    }

    private void createSlogoView() {
        mySlogoView = new SlogoView(myLogicalController, myVisualController);
    }

    private void createLogicalController() {
        myLogicalController = new LogicalController(myModelCollection, myVisualController, myVariables);
        myLogicalController.setLanguage(myDefaultLang);
    }

    private void createVisualController() {
        myVisualController = new VisualController();
    }

    private void createModel() {
        myModelCollection = new ModelCollection();
        myModelCollection.append(new ModelTurtle());
        myVariables = new VariableList();
    }

}
