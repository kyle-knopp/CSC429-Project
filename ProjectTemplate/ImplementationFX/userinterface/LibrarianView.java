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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class LibrarianView extends View
{

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components

    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBookButton;
    private Button searchpatronButton;
    private Button doneButton;



    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView(IModel librarian)
    {
        super(librarian, "Librarian View");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // how do you add white space?
        container.getChildren().add(new Label(" "));

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    private VBox createTitle()
    {
        VBox container = new VBox(10);
        Text titleText = new Text("       Library System          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        /*String accountHolderGreetingName = (String)myModel.getState("Name");
        Text welcomeText = new Text("Welcome, " + accountHolderGreetingName + "!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeText.setWrappingWidth(300);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setFill(Color.DARKGREEN);
        container.getChildren().add(welcomeText);

        Text inquiryText = new Text("What do you wish to do today?");
        inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        inquiryText.setWrappingWidth(300);
        inquiryText.setTextAlignment(TextAlignment.CENTER);
        inquiryText.setFill(Color.BLACK);
        container.getChildren().add(inquiryText);*/

        return container;
    }


    // Create the navigation buttons
    //-------------------------------------------------------------
    private VBox createFormContents()
    {

        VBox container = new VBox(15);

        // create the buttons, listen for events, add them to the container
        HBox dCont = new HBox(10);
        dCont.setAlignment(Pos.CENTER);
        insertBookButton = new Button("Insert New Book");
        insertBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        insertBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Insert New Book", null);
            }
        });
        dCont.getChildren().add(insertBookButton);

        container.getChildren().add(dCont);

        HBox wCont = new HBox(10);
        wCont.setAlignment(Pos.CENTER);
        insertPatronButton = new Button("Insert New Patron");
        insertPatronButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        insertPatronButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Insert Patron", null);
            }
        });
        wCont.getChildren().add(insertPatronButton);

        container.getChildren().add(wCont);

        HBox tCont = new HBox(10);
        tCont.setAlignment(Pos.CENTER);
        searchBookButton = new Button("Search Books");
        searchBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Books", null);
            }
        });
        tCont.getChildren().add(searchBookButton);

        container.getChildren().add(tCont);

        HBox biCont = new HBox(10);
        biCont.setAlignment(Pos.CENTER);
        searchpatronButton = new Button("Search Patrons");
        searchpatronButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchpatronButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Patrons", null);
            }
        });
        biCont.getChildren().add(searchpatronButton);

        container.getChildren().add(biCont);

       /* HBox iscCont = new HBox(10);
        iscCont.setAlignment(Pos.CENTER);
        imposeServiceChargeButton = new Button("Impose Service Charge");
        imposeServiceChargeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        imposeServiceChargeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("ImposeServiceCharge", null);
            }
        });
        iscCont.getChildren().add(imposeServiceChargeButton);

        container.getChildren().add(iscCont);*/

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
        doneCont.getChildren().add(doneButton);

        container.getChildren().add(doneCont);

        return container;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {

    }


    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
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
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}

