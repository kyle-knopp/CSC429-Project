//StudentBorrowerCollection
// specify the package
package model;

// system imports
import event.Event;
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

    private StudentBorrower selectedStudentBorrower;

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

                StudentBorrower selectedStudentBorrower = new StudentBorrower(nextAccountData);

                if (selectedStudentBorrower != null)
                {
                    studentBorrowerList.addElement(selectedStudentBorrower);
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

    public void findStudentBorrowersWithNameLike(String Name){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE FirstName like '%" + Name + "%'" +
                " OR " + "LastName like '%" + Name + "%'";

        queryHelper(query, "There are no Patrons who have a name that contains " + Name + ".");
    }

    public void findStudentBorrowersWithBooksCheckedOut(){

        //query

        String query = "SELECT DISTINCT BannerId, FirstName, LastName, ContactPhone, Email, DateOfLatestBorrowerStatus,\n" +
                "DateOfRegistration, Notes, status, BorrowerStatus\n" +
                "FROM StudentBorrower\n" +
                "LEFT JOIN rental ON StudentBorrower.BannerId = rental.BorrowerId\n" +
                "WHERE rental.CheckoutDate IS NOT NULL AND rental.CheckinDate IS NULL;";

        queryHelper(query, "There are no Student Borrowers who have books Checked Out.");
    }

    //----------------------------------------------------------
    public Object getState(String key) {
        //System.out.println("Selected Student Borrower: "+selectedStudentBorrower);
        if (key.equals("StudentBorrowers")) {
            System.out.println("Student Borrower List" + studentBorrowerList);
            return studentBorrowerList;
        }
        else if (key.equals("StudentBorrowerList")){
            System.out.println("Student Borrower List"+studentBorrowerList);
            return this;
        }
        else if (selectedStudentBorrower != null) {
            Object val = selectedStudentBorrower.getState(key);
            if (val != null)
                return val;
        }
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        /*if (key.equals("StudentSelected") == true) {

            try {
                selectedStudentBorrower = new StudentBorrower((String) value);
                stateChangeRequest("ModifyStudentBorrowerView",selectedStudentBorrower);
            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyStudentView", Event.ERROR);
            }
        }*/
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


    public void display() {
        for (Object b : studentBorrowerList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

}
