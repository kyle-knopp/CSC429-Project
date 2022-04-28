package model;

import exception.InvalidPrimaryKeyException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Rental extends EntityBase{

    private static final String myTableName = "rental";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    public Rental() {
        super(myTableName);
        setDependencies();

        persistentState = new Properties();
    }

    public Rental(String Id) throws exception.InvalidPrimaryKeyException {
        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + Id + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new exception.InvalidPrimaryKeyException("Multiple rentals matching Ids : "
                        + Id + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no book found for this id, throw an exception
        else
        {
            throw new exception.InvalidPrimaryKeyException("No rental matching Id : "
                    + Id + " found.");
        }
    }
    public Rental(Properties props)
    {
        super(myTableName);

        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Rental(String barcode, boolean flag) throws InvalidPrimaryKeyException {
        super(myTableName);
        // construct a query that checks the BookId in Rental equals barcode AND CheckinDate IS NULL Or CheckinDate = ''

        String query = "SELECT * FROM " + myTableName +
                " WHERE (BookId = " + barcode + ") AND ((CheckinDate IS NULL ) OR (CheckinDate = ''))";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size > 1) {
                throw new InvalidPrimaryKeyException("Multiple rental files matching Barcode : " + barcode +
                        " with Null CheckInDate found.");
            } else if (size == 1) {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = (Properties) allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
            else {
                throw new InvalidPrimaryKeyException("Error");
            }
        }
        // If no book is found for this barcode, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No rental file matching barcode : " + barcode + " found.");
        }
    }

    public void findIfBookIsOut(String barcode) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE ((BookId = " + barcode + ") AND " +
                "((CheckinDate IS NULL) OR (CheckinDate = '')))";


        Vector allDataRetrieved = getSelectQueryResult(query);

        System.out.println(allDataRetrieved);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size >= 1)
            {
                throw new exception.InvalidPrimaryKeyException("Error: Book "+barcode+" Already Checked Out.");
            }
        }
    }
    public void save(String trans)
    {
        updateStateInDatabase(trans);
    }
    public void checkIn(String trans){
        updateStateInDatabase(trans);
    }
    public void checkOut(String trans){
        updateStateInDatabase(trans);
    }

    public void update() {
        try {
            Properties whereClause = new Properties();
            whereClause.setProperty("Id", persistentState.getProperty("Id"));
            updatePersistentState(mySchema, persistentState, whereClause);
            updateStatusMessage = "Rental data updated successfully in database!";
        }catch (SQLException ex){
            updateStatusMessage ="Error in updating Student Borrower in database!";
            //System.out.println("Error in updating Student Borrower in database!");
            //System.out.println(ex.toString());
            //ex.printStackTrace();
        }catch (Exception excep) {
            updateStatusMessage = "Error in updating Student Borrower data in database!";
            System.out.println(excep);
        }
    }

    private void updateStateInDatabase(String trans) {
       // System.out.println("Inside updateStateInDatabase RENTAL RENTAL RENTAL");
       // System.out.println(persistentState.getProperty("Id"));
        try {
            if (trans == "checkIn") {
                Properties whereClause = new Properties();
                whereClause.setProperty("Id", persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Rental data updated successfully in database!";

            } else if (trans == "checkOut") {
               // System.out.println("Inside else in save rental.");
                Integer Id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                // GONNA HAVE TO LOOK AT THIS AT SOME POINT TO MAKE SURE BECAUSE OF AUTOINCREMENT ID
                persistentState.setProperty("Id", "" + Id.intValue());
                updateStatusMessage = "Rental data for new book installed successfully in database!";
            }
            /**if (persistentState.getProperty("bookId") != null)
             {
             Properties whereClause = new Properties();
             whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
             updatePersistentState(mySchema, persistentState, whereClause);
             updateStatusMessage = "Book data updated successfully in database!";
             }
             else
             {
             Integer bookID = insertAutoIncrementalPersistentState(mySchema, persistentState);
             persistentState.setProperty("bookId", "" + bookID.intValue());
             updateStatusMessage = "Book data for new book installed successfully in database!";
             }
             */


        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Rental data in database! Check input formats.";
            //System.out.println(ex.toString());
            ex.printStackTrace();
        }
        catch (Exception excep) {
            System.out.println(excep);
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    private void setDependencies() // definitely needs to be updated
    {
        dependencies = new Properties();
        dependencies.setProperty("CheckInBook", "TransactionError");
        dependencies.setProperty("CheckOutBook", "TransactionError");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public String toString() { //change this later - isn't used currently
        return "Id: " + persistentState.getProperty("Id") +
                "; BorrowerId: " + persistentState.getProperty("BorrowerId")  +
                "; BookId: " + persistentState.getProperty("BookId") +
                "; CheckoutDate " + persistentState.getProperty("CheckoutDate") +
                "; CheckoutWorkerId " + persistentState.getProperty("CheckoutWorkerId") +
                "; DueDate " + persistentState.getProperty("DueDate") +
                "; CheckinDate " + persistentState.getProperty("CheckinDate") +
                "; CheckinWorkerId " + persistentState.getProperty("CheckinWorkerId");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Rental a, Rental b)
    {
        String aNum = (String)a.getState("Id");
        String bNum = (String)b.getState("Id");

        return aNum.compareTo(bNum);
    }

    public Object getState(String key) {
        //System.out.println("Get State: "+key);
        //System.out.println("Status Message: "+updateStatusMessage);
        if(key.equals("TransactionError")){
            return updateStatusMessage;
        }else
        if(key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        }else
            return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {
        if (value != null) {
            persistentState.setProperty(key, (String)value);
        }
    }

    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("Id"));
        v.addElement(persistentState.getProperty("BorrowerId "));
        v.addElement(persistentState.getProperty("BookId"));
        v.addElement(persistentState.getProperty("CheckoutDate "));
        v.addElement(persistentState.getProperty("CheckoutWorkerId "));
        v.addElement(persistentState.getProperty("DueDate "));
        v.addElement(persistentState.getProperty("CheckinDate "));
        v.addElement(persistentState.getProperty("CheckinWorkerId "));

        return v;
    }

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    public static void setTextLimit(TextField textField, int length) {
        textField.setOnKeyTyped(event -> {
            String string = textField.getText();

            if (string.length() > length) {
                textField.setText(string.substring(0, length));
                textField.positionCaret(string.length());
            }
        });
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}