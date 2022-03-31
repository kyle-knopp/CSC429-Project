package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.*;
import model.StudentBorrower;

import java.util.Enumeration;
import java.util.Vector;

public class WorkerCollectionModifyView extends View{
    // GUI components
    protected TableView<WorkerTableModel> tableOfWorkers;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public WorkerCollectionModifyView(IModel worker)
    {
        super(worker, "WorkerCollectionModifyView");

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
    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues()
    {
        System.out.println("Getting here 1");
        ObservableList<WorkerTableModel> tableData = FXCollections.observableArrayList();
        System.out.println("1");
        try
        {
            System.out.println("2");
            WorkerCollection workerCollection = (WorkerCollection)myModel.getState("WorkerList");
            System.out.println("3");
            Vector entryList = (Vector)workerCollection.getState("Workers");
            System.out.println("4");
            Enumeration entries = entryList.elements();
            System.out.println("5");
            System.out.println(entryList.isEmpty());

            while (entries.hasMoreElements() == true)
            {
                System.out.println("loop");
                Worker nextWorker = (Worker)entries.nextElement();
                System.out.println("Next Worker for table: " + nextWorker);
                Vector<String> view = nextWorker.getEntryListView();

                // add this list entry to the list
                WorkerTableModel nextTableRowData = new WorkerTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfWorkers.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
            System.out.println(e);
            e.printStackTrace();
        }
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

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("List of Workers To Modify");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfWorkers = new TableView<WorkerTableModel>();
        tableOfWorkers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        TableColumn BannerIdColumn = new TableColumn("Banner Id") ;
        BannerIdColumn.setMinWidth(100);
        BannerIdColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("bannerId"));

        TableColumn FirstNameColumn = new TableColumn("First Name") ;
        FirstNameColumn.setMinWidth(100);
        FirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("firstName"));

        TableColumn LastNameColumn = new TableColumn("Last Name") ;
        LastNameColumn.setMinWidth(100);
        LastNameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("lastName"));

        TableColumn PhoneColumn = new TableColumn("Phone Number") ;
        PhoneColumn.setMinWidth(100);
        PhoneColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("phone"));

        TableColumn EmailColumn = new TableColumn("Email") ;
        EmailColumn.setMinWidth(100);
        EmailColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("email"));

        TableColumn CredentialsColumn = new TableColumn("Credentials") ;
        CredentialsColumn.setMinWidth(100);
        CredentialsColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("credentials"));

        TableColumn DateOfLatestCredentialsStatusColumn = new TableColumn("Date Of Latest Credentials Status") ;
        DateOfLatestCredentialsStatusColumn.setMinWidth(100);
        DateOfLatestCredentialsStatusColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("dateOfLatestCredentials"));

        TableColumn DateOfHireColumn = new TableColumn("Date Of Hire") ;
        DateOfHireColumn.setMinWidth(100);
        DateOfHireColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("DateOfHire"));


        tableOfWorkers.getColumns().addAll(BannerIdColumn,
                FirstNameColumn, LastNameColumn, PhoneColumn,
                EmailColumn, CredentialsColumn, DateOfLatestCredentialsStatusColumn, DateOfHireColumn);

        tableOfWorkers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    clearErrorMessage();
                    myModel.stateChangeRequest("ModifyWorkerView", null);
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfWorkers);


        //TODO need to look into switching this
        doneButton = new Button("Back");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
                //ModifyWorkerView CancelTransaction
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    /*protected void processAccountSelected()
    {
        PatronTableModel selectedItem = tableOfPatrons.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedAcctNumber = selectedItem.getAccountNumber();

            myModel.stateChangeRequest("AccountSelected", selectedAcctNumber);
        }
    }*/

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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


}


