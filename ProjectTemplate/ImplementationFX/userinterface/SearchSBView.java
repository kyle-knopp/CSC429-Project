package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

public class SearchSBView extends View
{

    // GUI components
    protected TextField SearchStudentBorrower;
    protected TextField SearchBannerId;

    protected Button doneButton;
    protected Button submitButton;
    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchSBView(IModel StudentBorrower)
    {
        super(StudentBorrower, "SearchSBView");

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
        //myModel.subscribe("TransactionError", this);
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
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 10, 25));

        Text searchLabel = new Text(" Enter Student Borrowers First and/or Last Name: ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        searchLabel.setFont(myFont);
        searchLabel.setWrappingWidth(150);
        searchLabel.setTextAlignment(TextAlignment.RIGHT);
        grid1.add(searchLabel, 0, 1);

        SearchStudentBorrower = new TextField();
        SearchStudentBorrower.setEditable(true);
        grid1.add(SearchStudentBorrower, 1, 1);

        SearchStudentBorrower.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SearchBannerId.clear();;
            }
        });

        HBox OR =new HBox();
        OR.setAlignment(Pos.CENTER);
        Text or = new Text(" -----------------or----------------- ");
        or.setFont(myFont);
        or.setFill(Color.GRAY);
        or.setWrappingWidth(200);
        OR.getChildren().add(or);

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(5);
        grid2.setPadding(new Insets(0, 25, 25, 25));

        Text banner = new Text(" Enter Student Borrowers Banner Id: ");
        banner.setFont(myFont);
        banner.setWrappingWidth(150);
        banner.setTextAlignment(TextAlignment.RIGHT);
        grid2.add(banner, 0, 3);

        SearchBannerId = new TextField();
        SearchBannerId.setEditable(true);
        grid2.add(SearchBannerId, 1, 3);

        SearchBannerId.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SearchStudentBorrower.clear();;
            }
        });

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        doneButton = new Button("Back");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        doneCont.getChildren().add(doneButton);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processSubmit();
            }
        });
        doneCont.getChildren().add(submitButton);


        vbox.getChildren().add(grid1);
        vbox.getChildren().add(OR);
        vbox.getChildren().add(grid2);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    public void processSubmit(){

        //DEBUG System.out.println("Student Borrower Name Text: " +SearchStudentBorrower.getText());
        //DEBUG System.out.println("Student Borrower Banner Text: " +SearchBannerId.getText());

       if(!(SearchStudentBorrower.getText().isEmpty())) {
           String input = SearchStudentBorrower.getText();
           Properties p = new Properties();
           p.setProperty("FirstName", input);
           clearText();
           //System.out.println("Properties in SearchSB: "+p);
           myModel.stateChangeRequest("StudentBorrowerCollectionView", p);
       }else if(!(SearchBannerId.getText().isEmpty())){
           String input= SearchBannerId.getText();
           Properties p = new Properties();
           p.setProperty("BannerId",input);
           clearText();
           myModel.stateChangeRequest("StudentBorrowerCollectionBannerId",p);

       }else{
           displayErrorMessage("Please Enter Student Borrower Name or Banner Id");
       }
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
        }else if (key.equals("TransactionError") == true)
        {
            displayMessage((String)value);
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
        SearchStudentBorrower.clear();
    }
}