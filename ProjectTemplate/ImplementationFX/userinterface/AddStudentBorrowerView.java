


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

// The class containing the Account View  for the ATM application
//==============================================================
public class AddStudentBorrowerView extends View
{

    // GUI components

    protected TextField BannerId;
    protected TextField FirstName;
    protected TextField LastName;
    protected TextField ContactPhone;
    protected TextField Email;
    protected TextField DateOfLatestBorrowerStatus;
    protected TextField DateOfRegistration;
    protected TextField Notes;

    protected DatePicker DOLBS, DOR;

    protected Text alreadyDeleted;

    protected Button backButton;
    protected Button doneButton;
    protected ComboBox statusBox;
    protected ComboBox borrStatBox;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public AddStudentBorrowerView(IModel StudentBorrower)
    {
        super(StudentBorrower, "AddStudentBorrowerView");

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


        myModel.subscribe("TransactionError", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library System");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    protected VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text(setPrompt());
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        /*
        Text accNumLabel = new Text(" Book ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        accNumLabel.setFont(myFont);
        accNumLabel.setWrappingWidth(150);
        accNumLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(accNumLabel, 0, 1);
         */
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);


        Text Label = new Text(" BannerId : ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 1);

        BannerId = new TextField();
        BannerId.setEditable(true);
        grid.add(BannerId, 1, 1);


        Text Label1 = new Text(" First Name : ");
        Label1.setFont(myFont);
        Label1.setWrappingWidth(150);
        Label1.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label1, 0, 2);

        FirstName = new TextField();
        FirstName.setEditable(true);
        grid.add(FirstName, 1, 2);


        Label = new Text(" Last Name : ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 3);

        LastName = new TextField();
        LastName.setEditable(true);
        grid.add(LastName, 1, 3);

        Label = new Text(" Contact Phone : ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 4);

        ContactPhone = new TextField();
        ContactPhone.setEditable(true);
        grid.add(ContactPhone, 1, 4);

        Label = new Text(" Email : ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 5);

        Email = new TextField();
        Email.setEditable(true);
        grid.add(Email, 1, 5);

        Label = new Text("  Date Of Latest Borrower Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 6);

        /**
        DateOfLatestBorrowerStatus = new TextField();
        DateOfLatestBorrowerStatus.setEditable(true);
        grid.add(DateOfLatestBorrowerStatus, 1, 6);
         **/

        DOLBS = new DatePicker();
        DOLBS.setEditable(true);
        grid.add(DOLBS, 1, 6);

        Label = new Text("  Date Of Registration: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 7);

        /**
        DateOfRegistration = new TextField();
        DateOfRegistration.setEditable(true);
        grid.add(DateOfRegistration, 1, 7);
         **/

        DOR = new DatePicker();
        DOR.setEditable(true);
        grid.add(DOR, 1, 7);

        Label = new Text("  Notes: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 8);

        Notes = new TextField();
        Notes.setEditable(true);
        grid.add(Notes, 1, 8);

        Label = new Text("  Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 9);

        statusBox = new ComboBox();
        statusBox.getItems().addAll(setStatusBoxFields());
        statusBox.getSelectionModel().selectFirst();;
        grid.add(statusBox,1,9);

        Label = new Text("  Borrower Status: ");
        Label.setFont(myFont);
        Label.setWrappingWidth(150);
        Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(Label, 0, 10);

        borrStatBox= new ComboBox();
        borrStatBox.getItems().addAll(setBorrowerStatusBoxFields());
        borrStatBox.getSelectionModel().selectFirst();;
        grid.add(borrStatBox,1,10);

        alreadyDeleted=new Text();
        alreadyDeleted.setText("");
        alreadyDeleted.setFill(Color.RED);
        grid.add(alreadyDeleted,0,11);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        doneCont.getChildren().add(backButton);

        doneButton = new Button(setSubmitButtonLabel());
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    protected void setFieldsEditable(Boolean option){
        BannerId.setEditable(option);
        FirstName.setEditable(option);
        LastName.setEditable(option);
        ContactPhone.setEditable(option);
        Email.setEditable(option);
        DateOfLatestBorrowerStatus.setEditable(option);
        DateOfRegistration.setEditable(option);
        Notes.setEditable(option);
        statusBox.setEditable(option);
        borrStatBox.setEditable(option);

    }

    protected String setViewTitle(){return "Add A Student Borrower";}

    protected String setPrompt(){return "STUDENT BORROWER INFORMATION";    }

    protected String setSubmitButtonLabel(){
        return "Submit";
    }

    protected String[] setStatusBoxFields(){return new String[]{"Active"};}

    protected String[] setBorrowerStatusBoxFields(){return new String[]{"Good Standing"};}


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

        /*if (key.equals("TransactionErrorMessage") == true)
        {
            String val = (String)value;
            //serviceCharge.setText(val);
            if (val.startsWith("ERR"))
                displayErrorMessage(val);
            else
                displayMessage(val);
        }*/
        System.out.println("Error Message: "+(String)value);
        if (key.equals("TransactionError") == true)
        {
            String val = (String)value;
            if (val.startsWith("Err") || (val.startsWith("ERR")))
                displayErrorMessage( val);
            else
                displayMessage(val);
        }
    }
    private void processAction(ActionEvent e) {

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

        myModel.stateChangeRequest("AddStudentBorrower", p);


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
        if(message.equals("StudentBorrower data updated successfully in database!")){
            clearText();
            BannerId.clear();
            FirstName.clear();
            LastName.clear();
            ContactPhone.clear();
            ContactPhone.clear();
            Email.clear();
            DateOfLatestBorrowerStatus.clear();
            DateOfRegistration.clear();
            Notes.clear();
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

    /**
     * Clear text
     */
    //----------------------------------------------------------
    public void clearText()
    {
        /*
        bookId.clear();
        bookTitle.clear();
        pubYear.clear();
        author.clear();
        statusBox.valueProperty().set("Active");

         */
    }

}

//---------------------------------------------------------------
//	Revision History:
//


