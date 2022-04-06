package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;


public class ConfirmDeleteBookView extends View {
    // GUI components
    protected TextField barcode;

    protected Button noButton;
    protected Button yesButton;

    // For showing error message
    protected MessageView statusLog;

    public ConfirmDeleteBookView(IModel model) {
        super(model, "ConfirmDeleteBookView");


        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());


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

        Text prompt = new Text("The following book has been SUCCESSFULLY DELETED");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text question = new Text(" Would you like to delete another? ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        question.setFont(myFont);
        question.setWrappingWidth(150);
        question.setTextAlignment(TextAlignment.RIGHT);
        grid.add(question, 0, 0, 2, 2);

        yesButton = new Button("Yes");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        noButton = new Button("No");
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        // consider using GridPane.setHgap(10); instead of label space
        HBox buttonCont = new HBox(10);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(yesButton);
        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(yesButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }

    protected void processAction(Event event) {
        Properties props = new Properties();
        //props.setProperty("barcode",barcode.getText());


        Object sender = event.getSource();
        if (sender == noButton) {
            myModel.stateChangeRequest("Cancel", "");
        }
        else if (sender == yesButton) {
            if (true) {
                myModel.stateChangeRequest("BarcodeView", null);
                //myModel.stateChangeRequest("SubmitBarcode", props);
            }
        }
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
        }else if (key.equals("TransactionError") == true)
        {
            displayMessage((String)value);
        }
    }

}