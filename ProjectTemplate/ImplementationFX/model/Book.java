package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Book extends EntityBase{

    private static final String myTableName = "book";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    public Book(String barcode) throws exception.InvalidPrimaryKeyException {
        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (barcode = " + barcode + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new exception.InvalidPrimaryKeyException("Multiple accounts matching barcodes : "
                        + barcode + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = (Properties)allDataRetrieved.elementAt(0);
                this.persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        this.persistentState.setProperty(nextKey, nextValue);
                    }
                    //System.out.println(nextKey+" : "+nextValue);
                }

            }
        }
        // If no book found for this id, throw an exception
        else
        {
            throw new exception.InvalidPrimaryKeyException("No book matching barcode : "
                    + barcode + " found.");
        }
    }
    public Book(Properties props)
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

    public void update() {
        try {
            Properties whereClause = new Properties();
            whereClause.setProperty("barcode", persistentState.getProperty("barcode"));
            updatePersistentState(mySchema, persistentState, whereClause);
            updateStatusMessage = "Book Successfully Updated in Database";
        }catch (SQLException ex){
            updateStatusMessage ="Error in adding book to database! Check format of inputs.";
            System.out.println("Error in adding book to database! Check format of inputs.");
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    private void updateStateInDatabase(String trans) // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
    {
        //System.out.println("Inside updateStateInDatabase BOOK BOOK BOOK");
       // System.out.println("Inside updateStateInDatabase");

        System.out.println(persistentState.getProperty("barcode"));
        try
        {
            if (trans=="modify")
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("barcode", persistentState.getProperty("barcode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data updated successfully in database!";
            }
            else if (trans=="add") {
                System.out.println("Inside else in save book.");
                Integer barcode = insertPersistentState(mySchema, persistentState);
                persistentState.setProperty("barcode", "" + barcode.intValue());
                updateStatusMessage = "Book data for new book installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in adding book to database! Check format of inputs.";
            //System.out.println(ex.toString());
            ex.printStackTrace();
            //  DEBUG System.out.println(updateStatusMessage);
        }
        catch (Exception excep) {
            System.out.println(excep);
            updateStatusMessage = "Error in adding book to database! Check format of inputs.";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("AddBook", "AddBookErrorMessage");
        dependencies.setProperty("AddBook", "AddBookSuccessMessage");
        dependencies.setProperty("ModifyBook", "TransactionError");
        dependencies.setProperty("DeleteBook", "TransactionError");
        dependencies.setProperty("SubmitBarcode", "updateStatusMessage");
        dependencies.setProperty("SubmitBarCode", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public String toString() {
        return "Title: " + persistentState.getProperty("title") + "; Author: " +
                persistentState.getProperty("author1")  + "; Year: " +
                persistentState.getProperty("yearOfPublication");
    }

    public void display() {
        System.out.println(toString());
    }

    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("barcode");
        String bNum = (String)b.getState("barcode");

        return aNum.compareTo(bNum);
    }

    public Object getState(String key) {
        //  DEBUG System.out.println("In get State in book: "+key);
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

        v.addElement(persistentState.getProperty("barcode")); //need to enter
        v.addElement(persistentState.getProperty("title"));
        v.addElement(persistentState.getProperty("author1"));
        v.addElement(persistentState.getProperty("author2")); //need to enter
        v.addElement(persistentState.getProperty("author3")); //need to enter
        v.addElement(persistentState.getProperty("author4")); //need to enter
        v.addElement(persistentState.getProperty("publisher")); //need to enter
        v.addElement(persistentState.getProperty("yearOfPublication"));
        v.addElement(persistentState.getProperty("ISBN")); //may need to check name
        v.addElement(persistentState.getProperty("suggestedPrice")); //need to enter
        v.addElement(persistentState.getProperty("notes")); //need to enter
        v.addElement(persistentState.getProperty("bookCondition")); // gotta enter
        v.addElement(persistentState.getProperty("Status"));
        v.addElement(persistentState.getProperty("prefix"));


        return v;
    }
    public String getId(){
        return this.persistentState.getProperty("barcode");
    }

    public static void getBookPrefix()
    {

    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}