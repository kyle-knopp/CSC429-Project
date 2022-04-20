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
public class ModifyWorkerView extends AddWorkerView
{

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyWorkerView(IModel worker)
    {
        super(worker);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        populateFields();

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("TransactionError", this);
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

    protected String setTitleText(){return "Modify Worker";}

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    @Override
    protected String[] setStatusBoxFields() {
        return new String[]{"Active","Inactive"};
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        String bannerID = (String) myModel.getState("bannerID");
        System.out.println("My Model banner id: " +bannerID);
        String firstName = (String) myModel.getState("firstName");
        String lastName = (String) myModel.getState("lastName");
        String contactPhone = (String) myModel.getState("phone");
        String em = (String) myModel.getState("email");
        String dolc = (String) myModel.getState("dateOfLatestCredentials");
        String stat= (String)myModel.getState("status");
        String dOH = (String) myModel.getState("dateOfHire");
        String credential = (String) myModel.getState("credentials");
        String pass = (String) myModel.getState("password");

        LocalDate dolc_ld = LocalDate.parse(dolc);
        LocalDate doh_ld = LocalDate.parse(dOH);

        bannerId.setText(bannerID);
        bannerId.setEditable(false);
        password.setText(pass);
        first.setText(firstName);
        last.setText(lastName);
        phone.setText(contactPhone);
        email.setText(em);
        //dOLC.setText(dolc);
        //doh.setText(dOH);
        DOLC.setValue(dolc_ld);
        DOH.setValue(doh_ld);
        status.setValue(stat);
        cred.setValue(credential);
        alreadyDeleted.setText("");
    }

    private void processAction(ActionEvent e) {
        clearErrorMessage();

        Properties p = new Properties();

        p.put("bannerID", bannerId.getText());
        p.put("firstName", first.getText());
        p.put("lastName", last.getText());
        p.put("phone", phone.getText());
        p.put("email", email.getText());
        //p.put("dateOfLatestCredentials", dOLC.getText());
        p.put("dateOfLatestCredentials", DOLC.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        p.put("password", password.getText());
        //p.put("dateOfHire", doh.getText());
        p.put("dateOfHire", DOH.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        p.put("credentials",cred.getValue());
        p.put("status", status.getValue());

        myModel.stateChangeRequest("UpdateWorker", p);
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
    /*public void clearText()
    {
        SearchPatrons.clear();
    }*/
}

//---------------------------------------------------------------
//	Revision History:
//

