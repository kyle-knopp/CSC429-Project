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
public class ModifyWorkerView extends AddWorkerView
{

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyWorkerView(IModel worker)
    {
        super(worker);

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
        String bannerID = (String) myModel.getState("bannerId");
        System.out.println("My Model banner id: " +bannerID);
        String firstName = (String) myModel.getState("firstName");
        String lastName = (String) myModel.getState("lastName");
        String contactPhone = (String) myModel.getState("phone");
        String em = (String) myModel.getState("email");
        String dolc = (String) myModel.getState("dateOfLatestCredentials");
        //String stat= (String)myModel.getState("status");
        String dOH = (String) myModel.getState("dateOfHire");
        String credential = (String) myModel.getState("credentials");
        String pass = (String) myModel.getState("password");


        bannerId.setText(bannerID);
        bannerId.setEditable(false);
        password.setText(pass);
        first.setText(firstName);
        last.setText(lastName);
        phone.setText(contactPhone);
        email.setText(em);
        dOLC.setText(dolc);
        doh.setText(dOH);
        //status.setValue(stat);
        cred.setValue(credential);
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
    /*public void clearText()
    {
        SearchPatrons.clear();
    }*/
}

//---------------------------------------------------------------
//	Revision History:
//

