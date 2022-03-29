package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class StudentBorrower extends EntityBase{

    private static final String myTableName = "StudentBorrower";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    public StudentBorrower(String BannerId) throws exception.InvalidPrimaryKeyException {
        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (BannerId = " + BannerId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new exception.InvalidPrimaryKeyException("Multiple student borrowers matching bannerIDs : "
                        + BannerId + " found.");
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
            throw new exception.InvalidPrimaryKeyException("No student borrower matching bannerID : "
                    + BannerId + " found.");
        }
    }
    public StudentBorrower(Properties props)
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
        System.out.println("Inside updateStateInDatabase STUDENT STUDENT STUDENT");
        System.out.println(persistentState.getProperty("BannerId"));
        try
        {
            if (trans=="update")
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("BannerId", persistentState.getProperty("BannerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "StudentBorrower data updated successfully in database!";
            }
            else if (trans=="add")
            {
                System.out.println("Inside else in save studentborrower.");
                Integer BannerId = insertPersistentState(mySchema, persistentState);
                persistentState.setProperty("BannerId", "" + BannerId.intValue());
                updateStatusMessage = "StudentBorrower data for new book installed successfully in database!";
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
    public String toString() { //change this later - isn't used currently
        return "Title: " + persistentState.getProperty("bookTitle") + "; Author: " +
                persistentState.getProperty("author")  + "; Year: " +
                persistentState.getProperty("pubYear");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(StudentBorrower a, StudentBorrower b)
    {
        String aNum = (String)a.getState("BannerId");
        String bNum = (String)b.getState("BannerId");

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

        v.addElement(persistentState.getProperty("BannerId")); //need to enter
        v.addElement(persistentState.getProperty("FirstName"));
        v.addElement(persistentState.getProperty("LastName"));
        v.addElement(persistentState.getProperty("ContactPhone")); //need to enter
        v.addElement(persistentState.getProperty("Email")); //need to enter
        v.addElement(persistentState.getProperty("DateOfLatestBorrowerStatus")); //need to enter
        v.addElement(persistentState.getProperty("DateOfRegistration")); //need to enter
        v.addElement(persistentState.getProperty("Notes"));
        v.addElement(persistentState.getProperty("status")); //may need to check name

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