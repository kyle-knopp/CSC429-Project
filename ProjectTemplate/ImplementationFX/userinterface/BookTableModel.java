package userinterface;



import javafx.beans.property.SimpleStringProperty;



import java.util.Vector;


public class BookTableModel {

    private final SimpleStringProperty Barcode;

    private final SimpleStringProperty Title;

    private final SimpleStringProperty Author1;

    private final SimpleStringProperty Author2;

    private final SimpleStringProperty Author3;

    private final SimpleStringProperty Author4;

    private final SimpleStringProperty Publisher;

    private final SimpleStringProperty YearOfPublication;

    private final SimpleStringProperty ISBN;

    private final SimpleStringProperty SuggestedPrice;

    private final SimpleStringProperty Notes;

    private final SimpleStringProperty BookCondition;

    private final SimpleStringProperty Status;

    private final SimpleStringProperty Prefix;

    public BookTableModel(Vector<String> accountData) {

        Barcode = new SimpleStringProperty(accountData.elementAt(0));

        Title = new SimpleStringProperty(accountData.elementAt(1));

        Author1 = new SimpleStringProperty(accountData.elementAt(2));

        Author2 = new SimpleStringProperty(accountData.elementAt(3));

        Author3 = new SimpleStringProperty(accountData.elementAt(4));

        Author4 = new SimpleStringProperty(accountData.elementAt(5));

        Publisher = new SimpleStringProperty(accountData.elementAt(6));

        YearOfPublication = new SimpleStringProperty(accountData.elementAt(7));

        ISBN = new SimpleStringProperty(accountData.elementAt(8));

        SuggestedPrice = new SimpleStringProperty(accountData.elementAt(9));

        Notes = new SimpleStringProperty(accountData.elementAt(10));

        BookCondition = new SimpleStringProperty(accountData.elementAt(11));

        Status = new SimpleStringProperty(accountData.elementAt(12));

        Prefix = new SimpleStringProperty(accountData.elementAt(13));

    }

    public String getBarcode() {
        return Barcode.get();
    }

    public void setBarcode(String s) {
        this.Barcode.set(s);
    }

    public String getTitle() {
        return Title.get();
    }

    public void setTitle(String s) {
        this.Title.set(s);
    }

    public String getAuthor1() {
        return Author1.get();
    }

    public void setAuthor1(String s) {
        this.Author1.set(s);
    }

    public String getAuthor2() {
        return Author2.get();
    }

    public void setAuthor2(String s) {
        this.Author2.set(s);
    }

    public String getAuthor3() {
        return Author3.get();
    }

    public void setAuthor3(String s) {
        this.Author3.set(s);
    }

    public String getAuthor4() {
        return Author4.get();
    }

    public void setAuthor4(String s) {
        this.Author4.set(s);
    }

    public String getPublisher() {
        return Publisher.get();
    }

    public void setPublisher(String s) {
        this.Publisher.set(s);
    }

    public String getYearOfPublication() {
        return YearOfPublication.get();
    }

    public void setYearOfPublication(String s) {
        this.YearOfPublication.set(s);
    }

    public String getISBN() {
        return ISBN.get();
    }

    public void setISBN(String s) {
        this.ISBN.set(s);
    }

    public String getSuggestedPrice() {
        return SuggestedPrice.get();
    }

    public void setSuggestedPrice(String s) {
        this.SuggestedPrice.set(s);
    }

    public String getNotes() {
        return Notes.get();
    }

    public void setNotes(String s) {
        this.Notes.set(s);
    }

    public String getBookCondition() {
        return BookCondition.get();
    }

    public void setBookCondition(String s) {
        this.BookCondition.set(s);
    }

    public String getStatus() {
        return Status.get();
    }

    public void setStatus(String s) {
        this.Status.set(s);
    }

    public String getPrefix() {
        return Prefix.get();
    }

    public void setPrefix(String s) {
        this.Prefix.set(s);
    }
}
