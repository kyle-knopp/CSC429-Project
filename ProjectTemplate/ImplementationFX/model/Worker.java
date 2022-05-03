package model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Worker extends EntityBase{

    private static final String myTableName = "worker";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    public Worker(String bannerID) throws exception.InvalidPrimaryKeyException {
        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (bannerID = " + bannerID + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        System.out.println(query);
        System.out.println(bannerID);
        System.out.println("All data retrieved: "+allDataRetrieved);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            System.out.println(allDataRetrieved.size());

            if(size==0){
                throw new exception.InvalidPrimaryKeyException("No matching bannerID found");
            }

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new exception.InvalidPrimaryKeyException("Multiple accounts matching bannerIDs : "
                        + bannerID + " found.");
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
            throw new exception.InvalidPrimaryKeyException("No worker matching bannerIDs : "
                    + bannerID + " found.");
        }
    }
    public Worker(Properties props)
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

    public void save(String trans)
    {
        updateStateInDatabase(trans);
    }

    private void updateStateInDatabase(String trans) // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
    {
        System.out.println("Inside updateStateInDatabase WORKER WORKER WORKER");
       // System.out.println(persistentState.getProperty("barcode"));
        try
        {
            if (trans=="update")
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("bannerID", persistentState.getProperty("bannerID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Worker data updated successfully in database!";
            }
            else if (trans=="add")
            {
                System.out.println("Inside else in save worker.");
                Integer bannerID = insertPersistentState(mySchema, persistentState);
                persistentState.setProperty("bannerID", "" + bannerID.intValue());
                updateStatusMessage = "bannerID data for new worker installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Worker data in database! Check input formats.";
            //System.out.println(ex.toString());
            ex.printStackTrace();
        }
        catch (Exception excep) {
            updateStatusMessage = "Error in installing Worker data in database! Check input formats.";
            System.out.println(excep);
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("AddWorker", "TransactionError");
        dependencies.setProperty("UpdateWorker", "TransactionError");
        dependencies.setProperty("DeleteWorker", "TransactionError");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public String toString() { //dont use dont need to change
        return "bannerID: " + persistentState.getProperty("bannerID") +
                "; firstName: " + persistentState.getProperty("firstName")  +
                "; lastName: " + persistentState.getProperty("lastName") +
                "; phone: " + persistentState.getProperty("phone")  +
                "; email: " + persistentState.getProperty("email") +
                "; credentials: " + persistentState.getProperty("credentials")  +
                "; dateOfLatestCredentials: " + persistentState.getProperty("dateOfLatestCredentials") +
                "; dateOfHire: " + persistentState.getProperty("dateOfHire")  +
                "; password: " + persistentState.getProperty("password");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Worker a, Worker b)
    {
        String aNum = (String)a.getState("bannerID");
        String bNum = (String)b.getState("bannerID");

        return aNum.compareTo(bNum);
    }

    public Object getState(String key) {
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

        v.addElement(persistentState.getProperty("bannerID")); //need to enter
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("phone")); //need to enter
        v.addElement(persistentState.getProperty("email")); //need to enter
        v.addElement(persistentState.getProperty("credentials")); //need to enter
        v.addElement(persistentState.getProperty("dateOfLatestCredentials")); //need to enter
        v.addElement(persistentState.getProperty("dateOfHire"));
        v.addElement(persistentState.getProperty("password")); //may need to check name
        v.addElement(persistentState.getProperty("status"));

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