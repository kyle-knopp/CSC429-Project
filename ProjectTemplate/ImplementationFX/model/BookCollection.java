//BookCollection
// specify the package
package model;

// system imports
import impresario.IView;

import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class BookCollection  extends EntityBase
{
    private static final String myTableName = "book";

    private Vector bookList;

    // GUI Components

    // Blank constructor for this class
    //----------------------------------------------------------
    public BookCollection()
    {
        super(myTableName);

        bookList = new Vector<Book>();

    }

    private void queryHelper(String query, String message) {

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

                Book patron = new Book(nextAccountData);

                if (patron != null)
                {
                    bookList.addElement(patron);
                }
            }

        }
        else
        {
            System.out.println(message);
        }
    }

    public void findBookWithBarcodeLike(String barcode){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (barcode like '%" + barcode + "%')";

        queryHelper(query, "There are no Books that contains the Barcode: " + barcode + ".");
    }

    public void findBookWithAuthorLike(String author){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (author like '%" + author + "%')";

        queryHelper(query, "There are no Author who have a name that contains " + author + ".");
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Book"))
            return bookList;
        else
        if (key.equals("BookList"))
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
        for (Object b : bookList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

}
