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

        Text wStatus = new Text(" Status : ");
        wStatus.setFont(myFont);
        wStatus.setWrappingWidth(150);
        wStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wStatus, 0, 8);

        status = new ComboBox();
        status.getItems().addAll(
                setStatusBoxFields()
        );

        grid.add(status, 1, 8);


        Text wDOLC = new Text(" Date Of Latest Credentials : ");
        wDOLC.setFont(myFont);
        wDOLC.setWrappingWidth(150);
        wDOLC.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wDOLC, 0, 9);

        grid.add(DOLC, 1, 9);

        Text wDOH = new Text(" Date of Hire : ");
        wDOH.setFont(myFont);
        wDOH.setWrappingWidth(150);
        wDOH.setTextAlignment(TextAlignment.RIGHT);

        grid.add(wDOH, 0, 10);
        grid.add(DOH, 1, 10);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });



        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("TransactionError", this);
        populateFields();
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

       // LocalDate dolc_ld = LocalDate.parse(dolc);
        //LocalDate doh_ld = LocalDate.parse(dOH);


        bannerId.setText(bannerID);
        bannerId.setEditable(false);
        password.setText(pass);
        first.setText(firstName);
        last.setText(lastName);
        phone.setText(contactPhone);
        email.setText(em);
        //dOLC.setText(dolc);
        //doh.setText(dOH);
        DOLC.setValue(LocalDate.parse(dolc));
        DOH.setValue(LocalDate.parse(dOH));
        status.setValue(stat);
        cred.setValue(credential);
        alreadyDeleted.setText("");
    }

    private void processAction(ActionEvent e) {
        clearErrorMessage();

        String ban = bannerId.getText();
        String pass = password.getText();
        String fName = first.getText();
        String lName = last.getText();
        String pho = phone.getText();
        String eml = email.getText(); //
        String credentials = (String) cred.getValue();
        String latestCred = DOLC.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateHire = DOH.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String stat = (String) status.getValue();
        String checkPrefix = "800";
        Properties p1 = new Properties();


        if ((ban.length() == 9) && bannerId.getText().startsWith("800")) {
            p1.setProperty("bannerID", ban);
            if (pass.length() != 0) {
                p1.setProperty("password", pass);
                if (fName.length() != 0) {
                    p1.setProperty("firstName", fName);
                    if (lName.length() != 0) {
                        p1.setProperty("lastName", lName);
                        if (((pho.length() != 10)||(pho.length() != 11)) && (pho.matches("[0-9]+"))) {
                            p1.setProperty("phone", pho);
                            if (eml.length() != 0) {
                                p1.setProperty("email", eml);
                                p1.setProperty("credentials", credentials);
                                p1.setProperty("dateOfLatestCredentials", latestCred);
                                p1.setProperty("dateOfHire", dateHire);
                                p1.setProperty("status", stat);
                                myModel.stateChangeRequest("UpdateWorker", p1);
                            } else {
                                displayErrorMessage("Error: Email must have an entry!");
                            }
                        } else {
                            displayErrorMessage("Error: PhoneNumber must have 10 or 11 digits and be composed of numbers!");
                        }
                    } else {
                        displayErrorMessage("Error: LastName must have an entry!");
                    }
                } else {
                    displayErrorMessage("Error: FirstName must have an entry!");
                }
            } else {
                displayErrorMessage("Error: Password must have an entry!");
            }
        } else {
            displayErrorMessage("Error: BannerID must be exactly nine digits and start with 800");

        }



//        Properties p = new Properties();
//
//        p.put("bannerID", bannerId.getText());
//        p.put("firstName", first.getText());
//        p.put("lastName", last.getText());
//        p.put("phone", phone.getText());
//        p.put("email", email.getText());
//        //p.put("dateOfLatestCredentials", dOLC.getText());
//        p.put("dateOfLatestCredentials", DOLC.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        p.put("password", password.getText());
//        //p.put("dateOfHire", doh.getText());
//        p.put("dateOfHire", DOH.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        p.put("credentials",cred.getValue());
//        p.put("status", status.getValue());
//
//        myModel.stateChangeRequest("UpdateWorker", p);
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

