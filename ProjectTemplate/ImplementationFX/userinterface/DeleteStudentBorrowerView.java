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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class DeleteStudentBorrowerView extends AddStudentBorrowerView
{

    // GUI components
    protected TextField SearchPatrons;

    protected Button searchButton;

    protected Button backButton;
    //protected Button submitButton;
    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public DeleteStudentBorrowerView(IModel StudentBorrower)
    {
        super(StudentBorrower);
        populateFields();
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("TransactionError", this);
    }


    protected String setViewTitle(){
        return "Delete a Student Borrower";
    }

    protected String setPrompt(){
        return "WOULD YOU LIKE TO DELETE THIS STUDENT BORROWER?";
    }

    protected String setSubmitButtonLabel(){
        return "Delete";
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
        String borrStat = (String) myModel.getState("BorrowerStatus");

        LocalDate dolbs_ld = LocalDate.parse(dolbs);
        LocalDate dor_ld = LocalDate.parse(dor);

        BannerId.setText(bannerID);
        FirstName.setText(firstName);
        LastName.setText(lastName);
        ContactPhone.setText(contactPhone);
        Email.setText(email);
        //DateOfLatestBorrowerStatus.setText(dolbs);
        //DateOfRegistration.setText(dor);
        DOLBS.setValue(dolbs_ld);
        DOR.setValue(dor_ld);
        Notes.setText(notes);
        statusBox.setValue(status);
        borrStatBox.setValue(borrStat);

        BannerId.setEditable(false);
        BannerId.setStyle("-fx-background-color: -fx-control-inner-background;");
        FirstName.setEditable(false);
        FirstName.setStyle("-fx-background-color: -fx-control-inner-background;");
        LastName.setEditable(false);
        LastName.setStyle("-fx-background-color: -fx-control-inner-background;");
        ContactPhone.setDisable(true);
        ContactPhone.setStyle("-fx-background-color: -fx-control-inner-background;");
        Email.setDisable(true);
        Email.setStyle("-fx-background-color: -fx-control-inner-background;");
        //DateOfLatestBorrowerStatus.setText(dolbs);
        //DateOfRegistration.setText(dor);
        DOLBS.setDisable(true);
        DOLBS.setStyle("-fx-background-color: -fx-control-inner-background;");
        DOR.setDisable(true);
        DOR.setStyle("-fx-background-color: -fx-control-inner-background;");
        Notes.setDisable(true);
        Notes.setStyle("-fx-background-color: -fx-control-inner-background;");
        statusBox.setDisable(true);
        borrStatBox.setDisable(true);

        if(status.equals("Inactive"))
        {
            //Debug: System.out.println("Book is Inactive");
            alreadyDeleted.setText("NOTE: " + firstName + " " + lastName + " is already INACTIVE!");
            doneButton.setDisable(true);
        }
        else
        {
            System.out.println("Book is Active");
            doneButton.setDisable(false);
        }
    }

    private void processAction(ActionEvent e){
        clearErrorMessage();

        Properties p = new Properties();

        p.put("BannerId", BannerId.getText());
        p.put("FirstName", FirstName.getText());
        p.put("LastName", LastName.getText());
        p.put("ContactPhone", ContactPhone.getText());
        p.put("Email", Email.getText());
        //p.put("DateOfLatestBorrowerStatus", DateOfLatestBorrowerStatus.getText());
        //p.put("DateOfRegistration", DateOfRegistration.getText());
        p.put("DateOfLatestBorrowerStatus", DOLBS.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        p.put("DateOfRegistration", DOR.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        p.put("Notes", Notes.getText());
        p.put("status","Inactive");
        p.put("BorrowerStatus",borrStatBox.getValue());


        myModel.stateChangeRequest("DeleteStudentBorrower", p);

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
        }else if (key.equals("TransactionError") == true)
        {
            String val = (String)value;
            if (val.startsWith("Err") || (val.startsWith("ERR")))
                displayErrorMessage( val);
            else
                displayMessage(val);
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


