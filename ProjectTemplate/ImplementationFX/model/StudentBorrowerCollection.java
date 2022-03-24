//StudentBorrowerCollection
// specify the package
package model;

// system imports
import impresario.IView;

import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class StudentBorrowerCollection  extends EntityBase
{
    private static final String myTableName = "StudentBorrower";

    private Vector studentBorrowerList;

    // GUI Components

    // Blank constructor for this class
    //----------------------------------------------------------
    public StudentBorrowerCollection()
    {
        super(myTableName);

        studentBorrowerList = new Vector<StudentBorrower>();

    }

    private void queryHelper(String query, String message) {

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

                StudentBorrower patron = new StudentBorrower(nextAccountData);

                if (patron != null)
                {
                    studentBorrowerList.addElement(patron);
                }
            }

        }
        else
        {
            System.out.println(message);
        }
    }

    public void findStudentBorrowersWithFirstNameLike(String FirstName){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (FirstName like '%" + FirstName + "%')";

        queryHelper(query, "There are no Patrons who have a name that contains " + FirstName + ".");
    }

    public void findStudentBorrowersWithLastNameLike(String LastName){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (LastName like '%" + LastName + "%')";

        queryHelper(query, "There are no Patrons who have a name that contains " + LastName + ".");
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("StudentBorrowers"))
            return studentBorrowerList;
        else
        if (key.equals("StudentBorrowerList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // Class is invariant, so this method does not change any attributes
        // It does handle the request to display its view (if Impose Service Charge -3 is opted for)
        //if (key.equals("DisplayView") == true)
        //{
        //	createAndShowView();
        //}
        //else
        //if (key.equals("AccountSelected") == true
        //{
        //	String accountNumber = (String)value;
        //  Account acct = retrieve(accountNumber);
        //  acct.subscribe("AccountCancelled", this);
        //  acct.stateChangeRequest("DisplayView", this);
        //}
        //else
        //if (key.equals("AccountCancelled") == true)
        //{
        //	createAndShowView();
        //}

        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    /*
    public String toString(){

        String s = ("The Patron Collection contains: ");

        for (Object b : patronList) {
            s.concat(b + " ");
        }

        return s;
    }

     */

    public void display() {
        for (Object b : studentBorrowerList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

}
