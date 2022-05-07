package userinterface;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Worker;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.Properties;

public class AddWorkerView extends View{
    // GUI components
    protected GridPane grid = new GridPane();

    protected TextField bannerId;
    protected PasswordField password;
    protected TextField first;
    protected TextField last;
    protected TextField phone;
    protected TextField email;
    protected ComboBox cred;
    protected TextField dOLC;
    protected TextField doh;
    protected ComboBox status;

    protected DatePicker DOLC, DOH;

    protected Text alreadyDeleted;

    protected Button backButton;
    protected Button submitButton;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    LocalDate curr = LocalDate.parse(dtf.format(now));

    protected Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public AddWorkerView(IModel worker)
    {
        super(worker, "AddWorkerView");

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
        myModel.subscribe("TransactionError", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(setTitleText());
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    protected String setTitleText(){return "Add Worker";}

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);


        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text(setPrompt());
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text workerBannerId = new Text(" BannerId : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        workerBannerId.setFont(myFont);
        workerBannerId.setWrappingWidth(150);
        workerBannerId.setTextAlignment(TextAlignment.RIGHT);
        grid.add(workerBannerId, 0, 1);

        bannerId = new TextField();
        bannerId.setEditable(true);
        Worker.setTextLimit(bannerId, 9);
        Worker.numericOnly(bannerId);
        grid.add(bannerId, 1, 1);

        Text workerPass = new Text(" Password : ");
        workerPass.setFont(myFont);
        workerPass.setWrappingWidth(150);
        workerPass.setTextAlignment(TextAlignment.RIGHT);
        grid.add(workerPass, 0, 2);

        password = new PasswordField();
        password.setEditable(true);
        grid.add(password, 1, 2);

        Text wFirst = new Text("  First Name : ");
        wFirst.setFont(myFont);
        wFirst.setWrappingWidth(150);
        wFirst.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wFirst, 0, 3);

        first = new TextField();
        first.setEditable(true);
        grid.add(first, 1, 3);


        Text wLast = new Text(" Last Name : ");
        wLast.setFont(myFont);
        wLast.setWrappingWidth(150);
        wLast.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wLast, 0, 4);

        last = new TextField();
        last.setEditable(true);
        grid.add(last, 1, 4);

        Text wPhone = new Text(" Phone Number : ");
        wPhone.setFont(myFont);
        wPhone.setWrappingWidth(150);
        wPhone.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wPhone, 0, 5);

        phone = new TextField();
        phone.setEditable(true);
        grid.add(phone, 1, 5);

        Text wEmail = new Text(" Email : ");
        wEmail.setFont(myFont);
        wEmail.setWrappingWidth(150);
        wEmail.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wEmail, 0, 6);

        email = new TextField();
        email.setEditable(true);
        Worker.setTextLimit(email,30);
        grid.add(email, 1, 6);

        Text wCred = new Text(" Credentials : ");
        wCred.setFont(myFont);
        wCred.setWrappingWidth(150);
        wCred.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wCred, 0, 7);

        cred = new ComboBox();
        cred.getItems().addAll(
                "Ordinary",
                "Administrator"
        );

        cred.setValue("Ordinary");
        grid.add(cred, 1, 7);

        Text wDOLC = new Text(" Date Of Latest Credentials : ");
        wDOLC.setFont(myFont);
        wCred.setWrappingWidth(150);
        wCred.setTextAlignment(TextAlignment.RIGHT);
        //grid.add(wDOLC, 0, 8);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        LocalDate curr = LocalDate.parse(dtf.format(now));


        //dOLC = new TextField();
        //dOLC.setEditable(false);
        //dOLC.setText(dtf.format(now));
        //grid.add(dOLC, 1, 8);

        DOLC = new DatePicker(curr);
        //grid.add(DOLC, 1, 8);

        Text wDOH = new Text(" Date of Hire : ");
        wDOH.setFont(myFont);
        wDOH.setWrappingWidth(150);
        wDOH.setTextAlignment(TextAlignment.RIGHT);
        //grid.add(wDOH, 0, 9);

        //doh = new TextField();
        //doh.setEditable(true);
        //doh.setText(dtf.format(now));
        //grid.add(doh, 1, 9);

        DOH = new DatePicker(curr);
        //grid.add(DOH, 1, 9);

        Text wStatus = new Text(" Status : ");
        wStatus.setFont(myFont);
        wStatus.setWrappingWidth(150);
        wStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wStatus, 0, 8);

        status = new ComboBox();
        status.getItems().addAll(
                setStatusBoxFields()
        );

        status.setValue("Active");
        grid.add(status, 1, 8);

        alreadyDeleted=new Text();
        alreadyDeleted.setText("");
        alreadyDeleted.setFill(Color.RED);
        //grid.add(alreadyDeleted,0,9);

        backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        submitButton = new Button(setSubmitButtonLabel1());
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        HBox buttonCont = new HBox(5);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(submitButton);

        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);

        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(backButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }

    protected String setPrompt(){return "WORKER INFORMATION";    }

    protected String setSubmitButtonLabel1(){
        return "Submit";
    }

    protected String[] setStatusBoxFields(){return new String[]{"Active"};}

    protected void setFieldsEditable(Boolean option){
        bannerId.setEditable(option);
        password.setEditable(option);
        first.setEditable(option);
        last.setEditable(option);
        phone.setEditable(option);
        email.setEditable(option);
        cred.setEditable(option);
        dOLC.setEditable(option);
        doh.setEditable(option);
        status.setEditable(option);
    }



    private void processAction(ActionEvent e) {

        clearErrorMessage();

        String ban = bannerId.getText();
        String pass = password.getText();
        String fName = first.getText();
        String lName = last.getText();
        String pho = phone.getText();
        String eml = email.getText();
        String credentials = (String)cred.getValue();
        //String latestCred = dOLC.getText();
        //String dateHire = doh.getText();
        String latestCred = curr.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateHire = curr.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String stat = (String)status.getValue();
       // String checkPrefix = "800"; && (ban.substring(0,2)).equals("800")
        Properties p1 = new Properties();

        if((ban.length() == 9)){
            p1.setProperty("bannerID", ban);
            if(pass.length() != 0){
                p1.setProperty("password", pass);
                if(fName.length() != 0){
                    p1.setProperty("firstName", fName);
                    if(lName.length() != 0) {
                        p1.setProperty("lastName", lName);
                        if (((pho.length()) != 9) && (pho.matches("[0-9]+"))) {
                            p1.setProperty("phone", pho);
                            if(eml.length() != 0){
                                p1.setProperty("email", eml);
                                p1.setProperty("credentials", credentials);
                                p1.setProperty("dateOfLatestCredentials", latestCred);
                                p1.setProperty("dateOfHire", dateHire);
                                p1.setProperty("status",stat);
                                myModel.stateChangeRequest("AddWorker", p1);

                            }
                            else{
                                displayErrorMessage("Error: Email must have an entry!");
                            }
                        } else {
                            displayErrorMessage("Error: PhoneNumber must have nine digits and be composed of numbers!");
                        }
                    }
                    else{
                        displayErrorMessage("Error: LastName must have an entry!");
                    }
                }
                else{
                    displayErrorMessage("Error: FirstName must have an entry!");
                }
            }
            else{
                displayErrorMessage("Error: Password must have an entry!");
            }
        }
        else{
            displayErrorMessage("Error: BannerID must be exactly eight digits");
        }

//        Properties p1 = new Properties();
//        p1.setProperty("bannerID", ban);
//        p1.setProperty("password", pass);
//        p1.setProperty("firstName", fName);
//        p1.setProperty("lastName", lName);
//        p1.setProperty("phone", pho);
//        p1.setProperty("email", eml);
//        p1.setProperty("credentials", credentials);
//        p1.setProperty("dateOfLatestCredentials", latestCred);
//        p1.setProperty("dateOfHire", dateHire);
//        p1.setProperty("status",stat);
//
//        if (ban.length() < 3) {
//            databaseErrorBarcode();
//        }else {
//            myModel.stateChangeRequest("AddWorker", p1);
//        }



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
       /* accountNumber.setText((String)myModel.getState("AccountNumber"));
        acctType.setText((String)myModel.getState("Type"));
        balance.setText((String)myModel.getState("Balance"));
        serviceCharge.setText((String)myModel.getState("ServiceCharge"));
        */
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();
        System.out.println("Error Message key: "+ key);
        System.out.println("Error Message: "+value);
        if (key.equals("PopulatePatronMessage") == true)
        {
            displayMessage((String)value);
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

        if(message.equals("Worker data updated successfully in database!")){
            bannerId.clear();
            password.clear();
            first.clear();
            last.clear();
            phone.clear();
            email.clear();
            cred.setValue("Ordinary");
            status.setValue("Active");
        }
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

    public void databaseUpdated(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText(null);
        alert.setHeaderText("Worker Added to Database");

        alert.showAndWait();
    }

    public void databaseError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Please make sure all fields are filled out correctly.");

        alert.showAndWait();
    }

    public void databaseErrorBarcode(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Please make sure the bannerId is correct.");

        alert.showAndWait();
    }
}

//---------------------------------------------------------------
//	Revision History:
//
