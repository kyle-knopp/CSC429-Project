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

    public void findBooksCheckedOut(){

        //query

        String query = "SELECT barcode, title, author1, author2, author3, author4, publisher, yearOfPublication,\n" +
                "ISBN, suggestedPrice, notes, bookCondition, Status, prefix, discipline\n" +
                "FROM " + myTableName + "\n" +
                "LEFT JOIN rental ON book.barcode = rental.BookId\n" +
                "WHERE rental.CheckoutDate IS NOT NULL AND rental.CheckinDate IS NULL;";

        queryHelper(query, "There are no Books that are Checked Out,");
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

    public void addBook(Book a) {
        int index = findIndexToAdd(a);
        bookList.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
    }

    public Book retrieve(int bookBarcode) {
        Book retValue = null;
        for (int cnt = 0; cnt < bookList.size(); cnt++) {
            Book nextBook = (Book) bookList.elementAt(cnt);
            String nextBookBarcode = (String)nextBook.getState("Barcode");
            if (nextBookBarcode.equals(""+bookBarcode) == true) {
                retValue = nextBook;
                break;
            }
        }
        return retValue;
    }

    private int findIndexToAdd(Book a) {
        int low = 0;
        int high = bookList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Book midSession = (Book)bookList.elementAt(middle);

            int result = Book.compare(a, midSession);

            if (result == 0) {
                return middle;
            }
            else if (result < 0) {
                high = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return low;
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
