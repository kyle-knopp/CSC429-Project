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

import java.time.LocalDate;
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

        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
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
        //DEBUG System.out.println("My Model banner id: " +bannerID);
        String firstName = (String) myModel.getState("FirstName");
        String lastName = (String) myModel.getState("LastName");
        String contactPhone = (String) myModel.getState("ContactPhone");
        String email = (String) myModel.getState("Email");
        String dolbs = (String) myModel.getState("DateOfLatestBorrowerStatus");
        String dor = (String) myModel.getState("DateOfRegistration");
        String notes = (String) myModel.getState("Notes");
        String status = (String) myModel.getState("status");

        LocalDate dolbs_ld = LocalDate.parse(dolbs);
        LocalDate dor_ld = LocalDate.parse(dor);

        BannerId.setText(bannerID);
        BannerId.setEditable(false);
        FirstName.setText(firstName);
        LastName.setText(lastName);
        ContactPhone.setText(contactPhone);
        Email.setText(email);
        DoLBS.setValue(dolbs_ld);
        DoR.setValue(dor_ld);
        Notes.setText(notes);
        statusBox.setValue(status);

    }

    private void processAction(ActionEvent e){
        clearErrorMessage();

        Properties p = new Properties();

        p.put("BannerId", BannerId.getText());
        p.put("FirstName", FirstName.getText());
        p.put("LastName", LastName.getText());
        p.put("ContactPhone", ContactPhone.getText());
        p.put("Email", Email.getText());
        p.put("DateOfLatestBorrowerStatus", DoLBS_Selected);
        p.put("DateOfRegistration", DoR_Selected);
        p.put("Notes", Notes.getText());
        p.put("status",statusBox.getValue());

        myModel.stateChangeRequest("UpdateStudentBorrower", p);

        /*clearText();
        BannerId.clear();
        FirstName.clear();
        LastName.clear();
        ContactPhone.clear();
        ContactPhone.clear();
        Email.clear();
        DateOfLatestBorrowerStatus.clear();
        DateOfRegistration.clear();
        Notes.clear();*/
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

