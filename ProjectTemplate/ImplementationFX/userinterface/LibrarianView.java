// specify the package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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

    private Button addBookButton;
    private Button deleteBookButton;
    private Button modifyBookButton;
    private Button addStudentBorrowerButton;
    private Button deleteStudentBorrowerButton;
    private Button modifyStudentBorrowerButton;
    private Button addWorkerButton;
    private Button deleteWorkerButton;
    private Button modifyWorkerButton;
    private Button DelinquencyCheckButton;

    private Button checkInBookButton;
    private Button checkOutBookButton;
    private Button delinquencyCheckButton;

    private Button ListAllBooksCheckedOutButton;
    private Button ListStudentBorrowersWithBooksCheckedOutButton;

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
        Text titleText = new Text(" Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.setAlignment(Pos.CENTER);
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
        HBox aCont = new HBox(10);
        aCont.setAlignment(Pos.CENTER);
        addBookButton = new Button("Add a Book");
        addBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("AddBook", null);
            }
        });
        aCont.getChildren().add(addBookButton);


        aCont.setAlignment(Pos.CENTER);
        modifyBookButton = new Button("Modify a Book");
        modifyBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        modifyBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("ModifyBook", null);
            }
        });
        aCont.getChildren().add(modifyBookButton);


        aCont.setAlignment(Pos.CENTER);
        deleteBookButton = new Button("Delete a Book");
        deleteBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        deleteBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("DeleteBook", null);
            }
        });
        aCont.getChildren().add(deleteBookButton);

        container.getChildren().add(aCont);

        HBox bCont = new HBox(10);
        bCont.setAlignment(Pos.CENTER);
        addStudentBorrowerButton = new Button("Add a Student Borrower");
        addStudentBorrowerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addStudentBorrowerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("AddStudentBorrower", null);
            }
        });
        bCont.getChildren().add(addStudentBorrowerButton);


        bCont.setAlignment(Pos.CENTER);
        modifyStudentBorrowerButton = new Button("Modify a Student Borrower");
        modifyStudentBorrowerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        modifyStudentBorrowerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchStudentBorrowerViewM", null);
            }
        });
        bCont.getChildren().add(modifyStudentBorrowerButton);


        bCont.setAlignment(Pos.CENTER);
        deleteStudentBorrowerButton = new Button("Delete a Student Borrower");
        deleteStudentBorrowerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        deleteStudentBorrowerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchStudentBorrowerView", null);
            }
        });
        bCont.getChildren().add(deleteStudentBorrowerButton);

        container.getChildren().add(bCont);

        HBox cCont = new HBox(10);
        cCont.setAlignment(Pos.CENTER);
        addWorkerButton = new Button("Add a Worker");
        addWorkerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addWorkerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("AddWorker", null);
            }
        });
        cCont.getChildren().add(addWorkerButton);


        cCont.setAlignment(Pos.CENTER);
        modifyWorkerButton = new Button("Modify a Worker");
        modifyWorkerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        modifyWorkerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchWorkerViewM", null);
            }
        });
        cCont.getChildren().add(modifyWorkerButton);


        cCont.setAlignment(Pos.CENTER);
        deleteWorkerButton = new Button("Delete a Worker");
        deleteWorkerButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        deleteWorkerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchWorkerViewD", null);
            }
        });
        cCont.getChildren().add(deleteWorkerButton);

        container.getChildren().add(cCont);

        HBox dCont = new HBox(10);
        dCont.setAlignment(Pos.CENTER);
        checkInBookButton = new Button("Check In a Book");
        checkInBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        checkInBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CheckIn", null);
            }
        });
        dCont.getChildren().add(checkInBookButton);


        dCont.setAlignment(Pos.CENTER);
        checkOutBookButton = new Button("Check Out a Book");
        checkOutBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        checkOutBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CheckOut", null);
            }
        });
        dCont.getChildren().add(checkOutBookButton);


        dCont.setAlignment(Pos.CENTER);
        delinquencyCheckButton = new Button("Run Delinquency Check");
        delinquencyCheckButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        delinquencyCheckButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) { //FIX LATER
                myModel.stateChangeRequest("DelinquencyCheck", null);
            }
        });
        dCont.getChildren().add(delinquencyCheckButton);

        container.getChildren().add(dCont);

        HBox eCont = new HBox(10);
        eCont.setAlignment(Pos.CENTER);
        ListAllBooksCheckedOutButton = new Button("List All Books Checked Out");
        ListAllBooksCheckedOutButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ListAllBooksCheckedOutButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("BookCollectionView", null);
            }
        });
        eCont.getChildren().add(ListAllBooksCheckedOutButton);

        eCont.setAlignment(Pos.CENTER);
        ListStudentBorrowersWithBooksCheckedOutButton = new Button("List Student Borrowers With Books Checked Out");
        ListStudentBorrowersWithBooksCheckedOutButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ListStudentBorrowersWithBooksCheckedOutButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("StudentBorrowerCollectionView", null);
            }
        });
        eCont.getChildren().add(ListStudentBorrowersWithBooksCheckedOutButton);

        container.getChildren().add(eCont);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("Log Out");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("LoginView", null);
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

