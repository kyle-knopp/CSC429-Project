package userinterface;



import javafx.beans.property.SimpleStringProperty;



import java.util.Vector;



public class StudentBorrowerTableModel {

    private final SimpleStringProperty BannerId;

    private final SimpleStringProperty FirstName;

    private final SimpleStringProperty LastName;

    private final SimpleStringProperty ContactPhone;

    private final SimpleStringProperty Email;

    private final SimpleStringProperty DateOfLatestBorrowerStatus;

    private final SimpleStringProperty DateOfRegistration;

    private final SimpleStringProperty Notes;


    //----------------------------------------------------------------------------

    public StudentBorrowerTableModel(Vector<String> accountData) {

        BannerId = new SimpleStringProperty(accountData.elementAt(0));

        FirstName = new SimpleStringProperty(accountData.elementAt(1));

        LastName = new SimpleStringProperty(accountData.elementAt(2));

        ContactPhone = new SimpleStringProperty(accountData.elementAt(3));

        Email = new SimpleStringProperty(accountData.elementAt(4));

        DateOfLatestBorrowerStatus = new SimpleStringProperty(accountData.elementAt(5));

        DateOfRegistration = new SimpleStringProperty(accountData.elementAt(6));

        Notes = new SimpleStringProperty(accountData.elementAt(7));

    }


    //----------------------------------------------------------------------------

    public String getBannerId() {

        return BannerId.get();
    }


    //----------------------------------------------------------------------------

    public void setBannerId(String number) {
        BannerId.set(number);
    }


    //----------------------------------------------------------------------------

    public String getFirstName() {

        return FirstName.get();
    }


    //----------------------------------------------------------------------------

    public void setFirstName(String auth) {

        FirstName.set(auth);
    }


    //----------------------------------------------------------------------------

    public String getLastName() {

        return LastName.get();

    }


    //----------------------------------------------------------------------------

    public void setLastName(String ti) {

        LastName.set(ti);

    }


    //----------------------------------------------------------------------------

    public String getContactPhone() {
        return ContactPhone.get();
    }


    //----------------------------------------------------------------------------

    public void setContactPhone(String pubyear) {

        ContactPhone.set(pubyear);
    }

    //----------------------------------------------------------------------------

    public String getEmail() {
        return Email.get();
    }


    //----------------------------------------------------------------------------

    public void setEmail(String stat) {

        Email.set(stat);

    }


    //----------------------------------------------------------------------------

    public String getDateOfLatestBorrowerStatus() {
        return DateOfLatestBorrowerStatus.get();
    }


    //----------------------------------------------------------------------------

    public void setDateOfLatestBorrowerStatus(String s) {

        DateOfLatestBorrowerStatus.set(s);
    }


    //----------------------------------------------------------------------------

    public String getDateOfRegistration() {
        return DateOfRegistration.get();
    }


    //----------------------------------------------------------------------------

    public void setDateOfRegistration(String stat) {

        DateOfRegistration.set(stat);
    }

    //----------------------------------------------------------------------------

    public String getNotes() {
        return Notes.get();
    }


    //----------------------------------------------------------------------------

    public void setNotes(String stat) {

        Notes.set(stat);
    }

}


