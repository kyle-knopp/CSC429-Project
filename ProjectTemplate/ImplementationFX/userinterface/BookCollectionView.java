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
import userinterface.*;

import java.util.Enumeration;
import java.util.Vector;

public class BookCollectionView extends View {
    // GUI components
    protected TableView<BookTableModel> tableOfBooks;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookCollectionView(IModel book)
    {
        super(book, "BookCollectionView");

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
        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        System.out.println("1");
        try
        {
            System.out.println("2");
            StudentBorrowerCollection studentborrowerCollection = (StudentBorrowerCollection)myModel.getState("BookList");
            System.out.println("3");
            Vector entryList = (Vector)studentborrowerCollection.getState("Book");
            System.out.println("4");
            Enumeration entries = entryList.elements();
            System.out.println("5");
            System.out.println(entryList.isEmpty());

            while (entries.hasMoreElements() == true)
            {
                System.out.println("loop");
                StudentBorrower nextStudentBorrower = (StudentBorrower)entries.nextElement();
                System.out.println("Next Student Borrower for table: " + nextStudentBorrower);
                Vector<String> view = nextStudentBorrower.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfBooks.setItems(tableData);
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

        Text prompt = new Text("List of Books");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<BookTableModel>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        TableColumn barcodeColumn = new TableColumn("Barcode") ;
        barcodeColumn.setMinWidth(100);
        barcodeColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("barcode"));

        TableColumn titleColumn = new TableColumn("Title") ;
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("title"));

        TableColumn author1Column = new TableColumn("Author 1") ;
        author1Column.setMinWidth(100);
        author1Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("author1"));

        TableColumn author2Column = new TableColumn("Author 2") ;
        author2Column.setMinWidth(100);
        author2Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("author2"));

        TableColumn author3Column = new TableColumn("Author 3") ;
        author3Column.setMinWidth(100);
        author3Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("author3"));

        TableColumn author4Column = new TableColumn("Author 4") ;
        author4Column.setMinWidth(100);
        author4Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("author4"));

        TableColumn publisherColumn = new TableColumn("Publisher") ;
        publisherColumn.setMinWidth(100);
        publisherColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("publisher"));

        TableColumn yearOfPublicationColumn = new TableColumn("Year Of Publication") ;
        yearOfPublicationColumn.setMinWidth(100);
        yearOfPublicationColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("yearOfPublication"));

        TableColumn ISBNColumn = new TableColumn("ISBN") ;
        ISBNColumn.setMinWidth(100);
        ISBNColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("ISBN"));

        TableColumn suggestedPriceColumn = new TableColumn("Suggested Price") ;
        suggestedPriceColumn.setMinWidth(100);
        suggestedPriceColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("suggestedPrice"));

        TableColumn notesColumn = new TableColumn("Notes") ;
        notesColumn.setMinWidth(100);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("notes"));

        TableColumn bookConditionColumn = new TableColumn("Book Condition") ;
        bookConditionColumn.setMinWidth(100);
        bookConditionColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("bookCondition"));

        TableColumn StatusColumn = new TableColumn("Status") ;
        StatusColumn.setMinWidth(100);
        StatusColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("Status"));

        TableColumn prefixColumn = new TableColumn("Prefix") ;
        prefixColumn.setMinWidth(100);
        prefixColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("prefix"));

        tableOfBooks.getColumns().addAll(barcodeColumn, titleColumn,
                author1Column, author1Column, author2Column,
                author3Column, author4Column, publisherColumn,
                yearOfPublicationColumn, ISBNColumn, suggestedPriceColumn,
                notesColumn, bookConditionColumn, StatusColumn, prefixColumn);

        tableOfBooks.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    clearErrorMessage();
                    //processStudentBorrowerSelected();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfBooks);


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
                //ModifyStudentBorrowerView CancelTransaction
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
        if (key.equals("TransactionError") == true)
        {
            displayMessage((String)value);
        }
    }

    //--------------------------------------------------------------------------
    protected void processStudentBorrowerSelected()
    {
        BookTableModel selectedItem = tableOfBooks.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedAcctNumber = selectedItem.getBarcode();

            myModel.stateChangeRequest("ModifyStudentBorrowerView", selectedAcctNumber);
        }
    }

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