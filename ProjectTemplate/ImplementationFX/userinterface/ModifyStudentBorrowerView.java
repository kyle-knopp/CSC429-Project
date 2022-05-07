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
public class ModifyStudentBorrowerView extends AddStudentBorrowerView
{
    protected Text Label;
    protected ComboBox status;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyStudentBorrowerView(IModel StudentBorrower)
    {
        super(StudentBorrower);

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Label = new Text("  Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 7);

        statusBox = new ComboBox();
        statusBox.getItems().addAll(setStatusBoxFields());
        statusBox.getSelectionModel().selectFirst();;
        grid.add(statusBox,1,7);

        Label = new Text("  Borrower Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 8);

        borrStatBox= new ComboBox();
        borrStatBox.getItems().addAll(setBorrowerStatusBoxFields());
        borrStatBox.getSelectionModel().selectFirst();;
        grid.add(borrStatBox,1,8);

        Label = new Text("  Date Of Registration: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 9);
        grid.add(DOR,1,9);

        Label = new Text("  Date Of Latest Borrower Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 10);
        grid.add(DOLBS,1,10);

        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("TransactionError", this);
        populateFields();

    }

    @Override
    protected String[] setStatusBoxFields() {
        return new String[]{"Active","Inactive"};
    }

    @Override
    protected String[] setBorrowerStatusBoxFields() {
        return new String[]{"Good Standing","Delinquent"};
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

        //LocalDate dolbs_ld = LocalDate.parse(dolbs);
        //LocalDate dor_ld = LocalDate.parse(dor);

        BannerId.setText(bannerID);
        BannerId.setEditable(false);
        FirstName.setText(firstName);
        LastName.setText(lastName);
        ContactPhone.setText(contactPhone);
        Email.setText(email);
        //DateOfLatestBorrowerStatus.setText(dolbs);
        //DateOfRegistration.setText(dor);
        DOLBS.setValue(LocalDate.parse(dolbs));
        DOR.setValue(LocalDate.parse(dor));
        Notes.setText(notes);
        statusBox.setValue(status);
        borrStatBox.setValue(borrStat);

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
        p.put("status",statusBox.getValue());
        p.put("BorrowerStatus",borrStatBox.getValue());

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

    //----------------------------------------------------------
    public void clearText()
    {
        SearchPatrons.clear();
    }*/
}

//---------------------------------------------------------------
//	Revision History:
//

