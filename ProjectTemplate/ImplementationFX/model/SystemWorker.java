package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import database.*;

import impresario.IView;

public class SystemWorker extends EntityBase implements IView {
    private static final String myTableName = "worker";

    public SystemWorker(Properties props)
            throws InvalidPrimaryKeyException, PasswordMismatchException
    {
        super(myTableName);

        String idToQuery = props.getProperty("bannerID");

        String query = "SELECT * FROM " + myTableName + " WHERE (bannerID = " + idToQuery + ")";

        Vector allDataRetrieved =  getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();
            System.out.println(size);
            if(size==0){
                throw new InvalidPrimaryKeyException("No account matching banner id : "
                        + idToQuery + " found.");
            }

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple accounts matching banner id : "
                        + idToQuery + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedCustomerData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedCustomerData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No account matching banner id : "
                    + idToQuery + " found.");
        }

        String password = props.getProperty("password");

        String accountPassword = persistentState.getProperty("password");

        if (accountPassword != null)
        {
            boolean passwordCheck = accountPassword.equals(password);
            if (passwordCheck == false)
            {
                throw new PasswordMismatchException("Password mismatch");
            }

        }
        else
        {
            throw new PasswordMismatchException("Password missing for account");
        }

    }


    //----------------------------------------------------------
    public SystemWorker(String idToQuery)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + idToQuery + ")";

        Vector allDataRetrieved =  getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple accounts matching banner id : "
                        + idToQuery + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedCustomerData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedCustomerData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No account matching banner id : "
                    + idToQuery + " found.");
        }
    }


    //----------------------------------------------------------
    public Object getState(String key)
    {
        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        persistentState.setProperty(key, (String)value);

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
