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
public class ModifyStudentBorrowerView extends AddStudentBorrowerView
{
    protected ComboBox status;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyStudentBorrowerView(IModel StudentBorrower)
    {
        super(StudentBorrower);


        populateFields();

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
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
        String bannerID = (String) myModel.getState("BannerId");
        System.out.println("My Model banner id: " +bannerID);
        String firstName = (String) myModel.getState("FirstName");
        String lastName = (String) myModel.getState("LastName");
        String contactPhone = (String) myModel.getState("ContactPhone");
        String email = (String) myModel.getState("Email");
        String dolbs = (String) myModel.getState("DateOfLatestBorrowerStatus");
        String dor = (String) myModel.getState("DateOfRegistration");
        String notes = (String) myModel.getState("Notes");
        String status = (String) myModel.getState("status");


        BannerId.setText(bannerID);
        BannerId.setEditable(false);
        FirstName.setText(firstName);
        LastName.setText(lastName);
        ContactPhone.setText(contactPhone);
        Email.setText(email);
        DateOfLatestBorrowerStatus.setText(dolbs);
        DateOfRegistration.setText(dor);
        Notes.setText(notes);
        statusBox.setValue(status);

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

    //----------------------------------------------------------
    public void clearText()
    {
        SearchPatrons.clear();
    }*/
}

//---------------------------------------------------------------
//	Revision History:
//

