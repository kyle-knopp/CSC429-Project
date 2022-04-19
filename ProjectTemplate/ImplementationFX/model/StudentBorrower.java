package model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    public void update() {
        try {
            Properties whereClause = new Properties();
            whereClause.setProperty("BannerId", persistentState.getProperty("BannerId"));
            updatePersistentState(mySchema, persistentState, whereClause);
            updateStatusMessage = "StudentBorrower data updated successfully in database!";
            updateStatusMessage = "StudentBorrower data updated successfully in database!";
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
            updateStatusMessage = "Error in installing Student Borrower data in database! Check input formats.";
            //System.out.println(ex.toString());
            ex.printStackTrace();
        }
        catch (Exception excep) {
            System.out.println(excep);
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    public String getId(){
        return this.persistentState.getProperty("BannerId");
    }

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("AddStudentBorrower", "TransactionError");
        dependencies.setProperty("UpdateStudentBorrower", "TransactionError");
        dependencies.setProperty("DeleteStudentBorrower", "TransactionError");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public String toString() { //change this later - isn't used currently
        return "BannerId: " + persistentState.getProperty("BannerId") + "; FirstName: " +
                persistentState.getProperty("FirstName")  + "; LastName: " +
                persistentState.getProperty("LastName") + "; ContactPhone " + persistentState.getProperty("ContactPhone") +
                "; Email " + persistentState.getProperty("Email") +
                "; DateOfLatestBorrowerStatus " + persistentState.getProperty("DateOfLatestBorrowerStatus") +
                "; DateOfRegistration " + persistentState.getProperty("DateOfRegistration") +
                "; Notes " + persistentState.getProperty("Notes") +
                "; status " + persistentState.getProperty("status");
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
        System.out.println("Get State: "+key);
        System.out.println("Status Message: "+updateStatusMessage);
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

    protected void setDelinquent() {
        Calendar rightNow = Calendar.getInstance();
        Date todayDate = rightNow.getTime();
        String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
        persistentState.setProperty("DateOfLatestBorrowerStatus", todayDateText);
        persistentState.setProperty("BorrowerStatus", "Delinquent");

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
        v.addElement(persistentState.getProperty("BorrowerStatus"));

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