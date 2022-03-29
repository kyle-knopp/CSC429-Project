package model;

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

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

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
            updateStatusMessage = "Error in installing account data in database!";
            //System.out.println(ex.toString());
            ex.printStackTrace();
        }
        catch (Exception excep) {
            System.out.println(excep);
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Update", "UpdateStatusMessage");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public String toString() { //dont use dont need to change
        return "Title: " + persistentState.getProperty("bookTitle") + "; Author: " +
                persistentState.getProperty("author")  + "; Year: " +
                persistentState.getProperty("pubYear");
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

        return v;
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}