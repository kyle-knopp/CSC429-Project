//DeleteStudentBorrowerView

// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class DeleteStudentBorrowerView extends View
{

    // GUI components
    protected TextField SearchPatrons;

    protected Button searchButton;

    protected Button backButton;
    protected Button submitButton;
    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public DeleteStudentBorrowerView(IModel StudentBorrower)
    {
        super(StudentBorrower, "DeleteStudentBorrowerView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text searchLabel = new Text(" Do you want to Delete: ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        searchLabel.setFont(myFont);
        searchLabel.setWrappingWidth(150);
        searchLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(searchLabel, 0, 1);

        SearchPatrons = new TextField();
        SearchPatrons.setEditable(true);
        //grid.add(SearchPatrons, 1, 1);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        backButton = new Button("No");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("StudentBorrowerCollectionDeleteViewNo", null);
            }
        });
        doneCont.getChildren().add(backButton);

        submitButton = new Button("Yes");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                clearText();

            }
        });
        doneCont.getChildren().add(submitButton);


        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }



    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        //accountNumber.setText((String)myModel.getState("AccountNumber"));
        //acctType.setText((String)myModel.getState("Type"));
        //balance.setText((String)myModel.getState("Balance"));
        //serviceCharge.setText((String)myModel.getState("ServiceCharge"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("ServiceCharge") == true)
        {
            String val = (String)value;
            //serviceCharge.setText(val);
            displayMessage("Service Charge Imposed: $ " + val);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    /**
     * Clear text
     */
    //----------------------------------------------------------
    public void clearText()
    {
        SearchPatrons.clear();
    }
}

//---------------------------------------------------------------
//	Revision History:
//


