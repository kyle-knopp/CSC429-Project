package model;

import exception.InvalidPrimaryKeyException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

public class ModifyBookTransaction extends Transaction {
    protected String updateStatusMessage;
    protected Book selectedBook;

    public ModifyBookTransaction() {
        super();
    }

    @Override
    protected void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.put("SubmitBarcode", "TransactionError,UpdateStatusMessage,BookToDisplay");
        dependencies.put("ModifyBook", "TransactionError,UpdateStatusMessage");
        dependencies.put("Cancel", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    protected Scene createView() {
        Scene currentScene = myViews.get("EnterBookBarcodeView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("EnterBookBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("EnterBookBarcodeView", currentScene);
        }

        currentScene.getStylesheets().add("userinterface/style.css");

        return currentScene;
    }

    private void createAndShowTreeView() {
        Scene currentScene = (Scene) myViews.get("ModifyBookTransactionView");
        if (currentScene == null) {
            View newView = ViewFactory.createView("ModifyBookTransactionView", this);
            currentScene = new Scene(newView);

            currentScene.getStylesheets().add("userinterface/style.css");

            myViews.put("ModifyBookTransactionView", currentScene);
        }

        swapToView(currentScene);
    }

    @Override
    public Object getState(String key) {
        switch (key) {
            case "TransactionError":
                return transactionErrorMessage;

            case "UpdateStatusMessage":
                return updateStatusMessage;

            case "BookToDisplay":
                return selectedBook;

            default:
                return null;
        }
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        switch (key) {
            case "DoYourJob":
                doYourJob();
                break;

            case "SubmitBarcode":
                processBarcode((String) value);
                break;

            case "ModifyBook":
                processTransaction((Properties) value);
                break;

                /*
            case "Search":
                if (value != null){
                    persistentState = (Properties) value;
                    findBooksByBarCode(persistentState.getProperty("BarCode"));
                }
                else
                    findAllBooks();
                break;
            case "Modify":
                selectedBook = findBookByBarCode((String)value);
                createAndShowBookTransactionView();
                break;
            case "Cancel":
                swapToView(createView());
                break;
                */
        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void processBarcode(String bc) {
        try {
            selectedBook = new Book(bc);
            createAndShowTreeView();
        } catch (InvalidPrimaryKeyException ex) {
            transactionErrorMessage = myMessages.getString("BookNotFoundMsg");
        }
    }

    protected void processTransaction(Properties p) {
        if (!selectedBook.getState("Status").equals(p.getProperty("Status"))) {
            LocalDateTime currentDate = LocalDateTime.now();
            String dateLastUpdate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            selectedBook.stateChangeRequest("DateStatusUpdated", dateLastUpdate);
        }
        selectedBook.stateChangeRequest("Notes", p.getProperty("Notes"));

        selectedBook.update();
        updateStatusMessage = (String) selectedBook.getState("UpdateStatusMessage");
        transactionErrorMessage = updateStatusMessage;

        swapToView(createView());
    }
}
