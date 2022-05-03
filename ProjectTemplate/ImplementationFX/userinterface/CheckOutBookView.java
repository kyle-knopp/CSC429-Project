package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;
import userinterface.MessageView;
import userinterface.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Properties;

public class CheckOutBookView extends View {
    // GUI components
    protected TextField barcode;
    protected TextField dueDate;

    protected DatePicker dateDue;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    public CheckOutBookView(IModel model) {
        super(model, "CheckOutBookView");


        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());
        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);



        myModel.subscribe("TransactionError", this);
    }

    private VBox createFormContent(){

        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Enter Book BarCode");
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
        barcode.setEditable(true);
        Book.setTextLimit(barcode, 9);
        Book.numericOnly(barcode);
        grid.add(barcode, 1, 1);

        Text duedate = new Text(" Enter Due Date: ");
        duedate.setFont(myFont);
        duedate.setWrappingWidth(150);
        duedate.setTextAlignment(TextAlignment.RIGHT);
        grid.add(duedate, 0, 2);

        //dueDate = new TextField();
        //dueDate.setEditable(true);
        //grid.add(dueDate, 1, 2);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now().plusDays(7);
        LocalDate curr = LocalDate.parse(dtf.format(now));

        dateDue = new DatePicker(curr);
        dateDue.setEditable(true);
        grid.add(dateDue, 1, 2);

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

    protected void processAction(Event event) {
        Properties props = new Properties();
        props.setProperty("BookId",barcode.getText());
        //props.setProperty("DueDate",dueDate.getText());
        props.setProperty("DueDate", dateDue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        Object sender = event.getSource();
        if (sender == cancelButton) {
            myModel.stateChangeRequest("Cancel", "");
        }
        else if (sender == submitButton) {
            clearErrorMessage();
            myModel.stateChangeRequest("CheckOutBook", props);
            // myModel.stateChangeRequest("SubmitBarcode", null);
            //barcode.clear();

        }
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }
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

    @Override
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("PopulateBarCodeMessage") == true)
        {
            displayMessage((String)value);
        }else if (key.equals("TransactionError") == true) {
            String val = (String) value;
            if (val.startsWith("Err") || (val.startsWith("ERR")))
                displayErrorMessage(val);
            else {
                barcode.clear();
                displayMessage((String) value);
            }

        }
    }

}