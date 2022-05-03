package userinterface;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Book;

import java.util.EventObject;
import java.util.Properties;

public class AddBookView extends View{
    // GUI components
    protected TextField barcode;
    protected TextField title;
    protected TextField author1;
    protected TextField author2;
    protected TextField author3;
    protected TextField author4;
    protected TextField publisher;
    protected TextField yearOfPublication;
    protected TextField ISBN;
    protected TextField suggestedPrice;
    protected TextField notes;
    protected TextField prefix;

    protected ComboBox discipline;
    protected ComboBox quality;
    protected ComboBox status;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public AddBookView(IModel book)
    {
        super(book, "AddBookView");

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

        // myModel.subscribe("ServiceCharge", this);
        //myModel.subscribe("AddBookErrorMessage", this);
        myModel.subscribe("TransactionError",this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Add New Book ");
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

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bcode = new Text(" Book's Barcode : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        bcode.setFont(myFont);
        bcode.setWrappingWidth(150);
        bcode.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bcode, 0, 1);

        barcode = new TextField();
        Book.numericOnly(barcode);
        Book.setTextLimit(barcode, 9);
        barcode.setEditable(true);
        grid.add(barcode, 1, 1);

        Text tit = new Text(" Title : ");
        tit.setFont(myFont);
        tit.setWrappingWidth(150);
        tit.setTextAlignment(TextAlignment.RIGHT);
        grid.add(tit, 0, 2);

        title = new TextField();
        title.setEditable(true);
        grid.add(title, 1, 2);


        Text auth1 = new Text(" Author 1 : ");
        auth1.setFont(myFont);
        auth1.setWrappingWidth(150);
        auth1.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth1, 0, 4);

        author1 = new TextField();
        author1.setEditable(true);
        grid.add(author1, 1, 4);

        Text auth2 = new Text(" Author 2 : ");
        auth2.setFont(myFont);
        auth2.setWrappingWidth(150);
        auth2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth2, 0, 5);

        author2 = new TextField();
        author2.setEditable(true);
        grid.add(author2, 1, 5);

        Text auth3 = new Text(" Author 3 : ");
        auth3.setFont(myFont);
        auth3.setWrappingWidth(150);
        auth3.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth3, 0, 6);

        author3 = new TextField();
        author3.setEditable(true);
        grid.add(author3, 1, 6);

        Text auth4 = new Text(" Author 4 : ");
        auth4.setFont(myFont);
        auth4.setWrappingWidth(150);
        auth4.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth4, 0, 7);

        author4 = new TextField();
        author4.setEditable(true);
        grid.add(author4, 1, 7);

        Text pub = new Text(" Publisher : ");
        pub.setFont(myFont);
        pub.setWrappingWidth(150);
        pub.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pub, 0, 8);

        publisher = new TextField();
        publisher.setEditable(true);
        grid.add(publisher, 1, 8);

        Text yOf = new Text(" Year of Publication : ");
        yOf.setFont(myFont);
        yOf.setWrappingWidth(150);
        yOf.setTextAlignment(TextAlignment.RIGHT);
        grid.add(yOf, 0, 9);

        yearOfPublication = new TextField();
        yearOfPublication.setEditable(true);
        Book.setTextLimit(yearOfPublication, 4);
        Book.numericOnly(yearOfPublication);
        yearOfPublication.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yearOfPublication.clear();;
            }
        });
        grid.add(yearOfPublication, 1, 9);

        Text iS = new Text(" ISBN : ");
        iS.setFont(myFont);
        iS.setWrappingWidth(150);
        iS.setTextAlignment(TextAlignment.RIGHT);
        grid.add(iS, 0, 10);

        ISBN = new TextField();
        ISBN.setEditable(true);
        Book.numericOnly(ISBN);
        grid.add(ISBN, 1, 10);

        Text sug = new Text(" Suggested Price : ");
        sug.setFont(myFont);
        sug.setWrappingWidth(150);
        sug.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sug, 0, 11);

        suggestedPrice = new TextField();
        suggestedPrice.setText("0.00");
        suggestedPrice.setEditable(true);
        grid.add(suggestedPrice, 1, 11);

        Text not = new Text(" Notes : ");
        not.setFont(myFont);
        not.setWrappingWidth(150);
        not.setTextAlignment(TextAlignment.RIGHT);
        grid.add(not, 0, 12);

        notes = new TextField();
        notes.setEditable(true);
        grid.add(notes, 1, 12);


        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        // consider using GridPane.setHgap(10); instead of label space
        HBox buttonCont = new HBox(10);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(submitButton);

        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);

        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(cancelButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }

    protected String[] setStatusBoxFields(){return new String[]{"Active"};}

    private void processAction(ActionEvent e) {

        clearErrorMessage();

        String bar = barcode.getText();
        String titl = title.getText();
        String disi = " ";
        String au1 = author1.getText();
        String au2 = author2.getText();
        String au3 = author3.getText();
        String au4 = author4.getText();
        String publi = publisher.getText();
        String yeaO = yearOfPublication.getText();
        String isb = ISBN.getText();
        String condi = (String) quality.getValue();
        String disc = (String) quality.getValue();
        String pref = prefix.getText();
        String sugPric = suggestedPrice.getText();
        String no = notes.getText();
        String sta = (String) status.getValue();


        Properties p2 = new Properties();
        if(bar.length()>3 && bar.length()<10 && bar.matches("[0-9]+")) {
            p2.setProperty("barcode", bar);
            if(titl.length()!=0) {
                p2.setProperty("title", titl);
                if(au1.length()!=0) {
                    p2.setProperty("author1", au1);
                    if(yeaO.length()==4 && yeaO.matches("[0-9]+")) {
                        p2.setProperty("yearOfPublication", yeaO);
                        if(isb.length()==9) {
                            p2.setProperty("author2", au2);
                            p2.setProperty("author3", au3);
                            p2.setProperty("author4", au4);
                            p2.setProperty("publisher", publi);

                            p2.setProperty("ISBN", isb);
                            p2.setProperty("suggestedPrice", sugPric);
                            p2.setProperty("notes", no);
                            p2.setProperty("bookCondition", condi);
                            //p2.setProperty("bookDiscipline", disc);
                            p2.setProperty("Status", sta);
                            myModel.stateChangeRequest("AddBook", p2);
                        }else{
                            displayErrorMessage("Error: ISBN must be 9 digits");
                        }
                    }else{
                        displayErrorMessage("Error: Publication year must be 4 digits");
                    }
                }else {
                    displayErrorMessage("Error: Please enter at least 1st Author");
                }
            }else{
                displayErrorMessage("Error: Please enter a title");
            }
        }else{
            displayErrorMessage("Error: Barcode must be between 3 and 10 digits");
        }

        /*if(bar.length() >= 3) {
            p2.setProperty("prefix", getBookPrefix(bar));
        }
        else{
            System.out.println("Barcode in incorrect format");
        }

        if (yeaO == null || yeaO == "" || yeaO.length() == 0 || yeaO.length() > 4 ){
            databaseErrorYear();
        }else {
            System.out.println(p2);
            myModel.stateChangeRequest("AddBook", p2);

        }*/



    }



    public String getBookPrefix(String barccode){

        String prefix = barccode.substring(0, 3);
        return prefix;
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
        yearOfPublication.setText("(Must be Four Digits)");
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();
        //  DEBUG System.out.println(key);

        System.out.println("Error Message key: "+ key);
        System.out.println("Error Message: "+value);

        if (key.equals("AddBookErrorMessage") == true)
        {
            displayErrorMessage((String)value);
        }else if(key.equals("TransactionError")==true)
        {
            //  DEBUG System.out.println((String)value);
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
        //  DEBUG System.out.println("Message: "+message);
        if(message.equals("Book data for new book installed successfully in database!")){
            barcode.clear();
            title.clear();
            author1.clear();
            author2.clear();
            author3.clear();
            author4.clear();
            publisher.clear();
            yearOfPublication.clear();
            ISBN.clear();
            suggestedPrice.clear();
            notes.clear();

            quality.setValue("Good");
            status.setValue("Active");
            suggestedPrice.setText("0.00");
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
        alert.setHeaderText("Book Added to Database");

        alert.showAndWait();
    }

    public void databaseErrorYear(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Cannot add to database. Check year/barcode.");

        alert.showAndWait();
    }

    //@Override
    protected void processAction(EventObject evt) {

    }
}
