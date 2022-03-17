package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Book extends EntityBase{

    private static final String myTableName = "Book";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    public Book(String bookID) throws exception.InvalidPrimaryKeyException {
        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (bookID = " + bookID + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new exception.InvalidPrimaryKeyException("Multiple accounts matching id : "
                        + bookID + " found.");
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
            throw new exception.InvalidPrimaryKeyException("No book matching id : "
                    + bookID + " found.");
        }
    }
    public Book(Properties props)
    {
        super(myTableName);

        setDependencies();
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

    public Book(){

        super(myTableName);

        setDependencies();
        persistentState = new Properties();

        persistentState.setProperty("bookTitle", "");
        persistentState.setProperty("author", "");
        persistentState.setProperty("pubYear", "");
        persistentState.setProperty("status", "Active");
    }

    public void save()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase() // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
    {
        try
        {
            if (persistentState.getProperty("bookID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("bookID",
                        persistentState.getProperty("bookID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for bookID number : " + persistentState.getProperty("bookID") + " updated successfully in database!";
            }
            else
            {
                Integer bookID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookID", "" + bookID.intValue());
                updateStatusMessage = "Book data for new book : " +  persistentState.getProperty("bookID")
                        + " installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing account data in database!";
            System.out.println(ex.toString());
            ex.printStackTrace();
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
    public String toString() {
        return "Title: " + persistentState.getProperty("bookTitle") + "; Author: " +
                persistentState.getProperty("author")  + "; Year: " +
                persistentState.getProperty("pubYear") ;
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("bookID");
        String bNum = (String)b.getState("bookID");

        return aNum.compareTo(bNum);
    }

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {
        if (value != null) {
            persistentState.setProperty(key, (String)value);
        }
    }
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("bookTitle"));
        v.addElement(persistentState.getProperty("author"));
        v.addElement(persistentState.getProperty("pubYear"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    public void processNewBook(Properties p){
        /*
        persistentState.setProperty("bookTitle", );
        persistentState.setProperty("author", );
        persistentState.setProperty("pubYear", );
        persistentState.setProperty("status", );
        this.save();
        */
    }
}