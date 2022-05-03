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

public class DelinquencyCheckView extends View {
    // GUI components

    protected Button backButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    public DelinquencyCheckView(IModel model) {
        super(model, "DelinquencyCheckView");


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
    //-------------------------------------------------------------
    private VBox createTitle()
    {
        VBox container = new VBox(10);
        Text titleText = new Text(" Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);;

        return container;
    }

    private VBox createFormContent() {

        VBox vbox = new VBox(10);

        Text blankText = new Text("  ");
        blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText.setWrappingWidth(350);
        blankText.setTextAlignment(TextAlignment.CENTER);
        blankText.setFill(Color.WHITE);
        vbox.getChildren().add(blankText);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 100, 10, 100));


        Text prompt = new Text(" Run Delinquency Check ");
        Font myFont = Font.font("Arial", FontWeight.BOLD, 18);
        prompt.setFont(myFont);
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        //
        vbox.getChildren().add(prompt);
        vbox.setAlignment(Pos.CENTER);



        submitButton = new Button("Run Delinquency Check");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                Properties props = new Properties();
                myModel.stateChangeRequest("Delinquency",props);
                //processAction(e);
            }
        });

        backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        // consider using GridPane.setHgap(10); instead of label space
        HBox buttonCont = new HBox(10);
        buttonCont.setPadding(new Insets(10, 100, 25, 100));
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

    protected void processAction(Event event) {
        Properties props = new Properties();
        //props.setProperty("barcode", barcode.getText());

        Object sender = event.getSource();
        if (sender == backButton) {
            myModel.stateChangeRequest("Cancel", "");
        } else if (sender == submitButton) {
            clearErrorMessage();
            myModel.stateChangeRequest("SubmitDone", props);
        }
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

    @Override
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("TransactionError") == true) {
            String val = (String) value;
            if (val.startsWith("Err") || (val.startsWith("ERR")))
                displayErrorMessage(val);
            else
                displayMessage(val);
        }
    }

}
