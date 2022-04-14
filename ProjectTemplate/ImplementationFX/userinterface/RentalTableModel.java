package userinterface;



import javafx.beans.property.SimpleStringProperty;



import java.util.Vector;



public class RentalTableModel {

    private final SimpleStringProperty Id;

    private final SimpleStringProperty BorrowerId;

    private final SimpleStringProperty BookId;

    private final SimpleStringProperty CheckoutDate;

    private final SimpleStringProperty CheckoutWorkerId;

    private final SimpleStringProperty DueDate;

    private final SimpleStringProperty CheckinDate;

    private final SimpleStringProperty CheckinWorkerId;

    //----------------------------------------------------------------------------

    public RentalTableModel(Vector<String> accountData) {

        Id = new SimpleStringProperty(accountData.elementAt(0));

        BorrowerId = new SimpleStringProperty(accountData.elementAt(1));

        BookId = new SimpleStringProperty(accountData.elementAt(2));

        CheckoutDate = new SimpleStringProperty(accountData.elementAt(3));

        CheckoutWorkerId = new SimpleStringProperty(accountData.elementAt(4));

        DueDate = new SimpleStringProperty(accountData.elementAt(5));

        CheckinDate = new SimpleStringProperty(accountData.elementAt(6));

        CheckinWorkerId = new SimpleStringProperty(accountData.elementAt(7));

    }


    //----------------------------------------------------------------------------

    public String getId() {

        return Id.get();
    }


    //----------------------------------------------------------------------------

    public void setId(String in) {
        Id.set(in);
    }

    //----------------------------------------------------------------------------

    public String getBorrowerId() {

        return BorrowerId.get();
    }


    //----------------------------------------------------------------------------

    public void setBorrowerId(String in) {
        BorrowerId.set(in);
    }

    //----------------------------------------------------------------------------

    public String getBookId() {

        return BookId.get();
    }


    //----------------------------------------------------------------------------

    public void setBookId(String in) {
        BookId.set(in);
    }

    //----------------------------------------------------------------------------

    public String getCheckoutDate() {

        return CheckoutDate.get();
    }


    //----------------------------------------------------------------------------

    public void setCheckoutDate(String in) {
        CheckoutDate.set(in);
    }

    //----------------------------------------------------------------------------

    public String getCheckoutWorkerId() {

        return CheckoutWorkerId.get();
    }


    //----------------------------------------------------------------------------

    public void setCheckoutWorkerId(String in) {
        CheckoutWorkerId.set(in);
    }

    //----------------------------------------------------------------------------

    public String getDueDate() {

        return DueDate.get();
    }


    //----------------------------------------------------------------------------

    public void setDueDate(String in) {
        DueDate.set(in);
    }

    //----------------------------------------------------------------------------

    public String getCheckinDate() {

        return CheckinDate.get();
    }


    //----------------------------------------------------------------------------

    public void setCheckinDate(String in) {
        CheckinDate.set(in);
    }

    //----------------------------------------------------------------------------

    public String getCheckinWorkerId() {

        return CheckinWorkerId.get();
    }


    //----------------------------------------------------------------------------

    public void setCheckinWorkerId(String in) {
        CheckinWorkerId.set(in);
    }

}

