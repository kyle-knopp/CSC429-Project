package userinterface;



import javafx.beans.property.SimpleStringProperty;



import java.util.Vector;



public class WorkerTableModel {

    private final SimpleStringProperty BannerId;

    private final SimpleStringProperty FirstName;

    private final SimpleStringProperty LastName;

    private final SimpleStringProperty Phone;

    private final SimpleStringProperty Email;

    private final SimpleStringProperty Credentials;

    private final SimpleStringProperty dateOfLatestCredentials;

    private final SimpleStringProperty dateOfHire;

    private final SimpleStringProperty status;


    //----------------------------------------------------------------------------

    public WorkerTableModel(Vector<String> accountData) {

        BannerId = new SimpleStringProperty(accountData.elementAt(0));

        FirstName = new SimpleStringProperty(accountData.elementAt(1));

        LastName = new SimpleStringProperty(accountData.elementAt(2));

        Phone = new SimpleStringProperty(accountData.elementAt(3));

        Email = new SimpleStringProperty(accountData.elementAt(4));

        Credentials = new SimpleStringProperty(accountData.elementAt(5));

        dateOfLatestCredentials = new SimpleStringProperty(accountData.elementAt(6));

        dateOfHire = new SimpleStringProperty(accountData.elementAt(7));

        status = new SimpleStringProperty(accountData.elementAt(8));

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

    public String getPhone() {
        return Phone.get();
    }


    //----------------------------------------------------------------------------

    public void setPhone(String s) {

        Phone.set(s);
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

    public String getCredentials() {
        return Credentials.get();
    }


    //----------------------------------------------------------------------------

    public void setCredentials(String s) {

        Credentials.set(s);

    }


    //----------------------------------------------------------------------------

    public String getDateOfLatestCredentials() {
        return dateOfLatestCredentials.get();
    }


    //----------------------------------------------------------------------------

    public void setDateOfLatestCredentials(String s) {

        dateOfLatestCredentials.set(s);
    }


    //----------------------------------------------------------------------------

    public String getDateOfHire() {
        return dateOfHire.get();
    }


    //----------------------------------------------------------------------------

    public void setDateOfHire(String stat) {

        dateOfHire.set(stat);
    }
    //----------------------------------------------------------------------------

    public String status() {

        return status.get();
    }


    //----------------------------------------------------------------------------

    public void status(String stat) {
        status.set(stat);
    }

}



