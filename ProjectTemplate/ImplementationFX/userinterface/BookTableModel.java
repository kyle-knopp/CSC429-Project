package userinterface;



import javafx.beans.property.SimpleStringProperty;



import java.util.Vector;


public class BookTableModel {

    private final SimpleStringProperty barcode;

    private final SimpleStringProperty title;

    private final SimpleStringProperty author1;

    private final SimpleStringProperty author2;

    private final SimpleStringProperty author3;

    private final SimpleStringProperty author4;

    private final SimpleStringProperty publisher;

    private final SimpleStringProperty yearOfPublication;

    private final SimpleStringProperty ISBN;

    private final SimpleStringProperty suggestedPrice;

    private final SimpleStringProperty notes;

    private final SimpleStringProperty bookCondition;

    private final SimpleStringProperty Status;

    private final SimpleStringProperty prefix;

    public BookTableModel(Vector<String> accountData) {

        barcode = new SimpleStringProperty(accountData.elementAt(0));

        title = new SimpleStringProperty(accountData.elementAt(1));

        author1 = new SimpleStringProperty(accountData.elementAt(2));

        author2 = new SimpleStringProperty(accountData.elementAt(3));

        author3 = new SimpleStringProperty(accountData.elementAt(4));

        author4 = new SimpleStringProperty(accountData.elementAt(5));

        publisher = new SimpleStringProperty(accountData.elementAt(6));

        yearOfPublication = new SimpleStringProperty(accountData.elementAt(7));

        ISBN = new SimpleStringProperty(accountData.elementAt(8));

        suggestedPrice = new SimpleStringProperty(accountData.elementAt(9));

        notes = new SimpleStringProperty(accountData.elementAt(10));

        bookCondition = new SimpleStringProperty(accountData.elementAt(11));

        Status = new SimpleStringProperty(accountData.elementAt(12));

        prefix = new SimpleStringProperty(accountData.elementAt(13));

    }

    public String getBarcode() {
        return barcode.get();
    }


    public void setBarcode(String s) {
        this.barcode.set(s);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String s) {
        this.title.set(s);
    }

    public String getAuthor1() {
        return author1.get();
    }

    public void setAuthor1(String s) {
        this.author1.set(s);
    }

    public String getAuthor2() {
        return author2.get();
    }

    public void setAuthor2(String s) {
        this.author2.set(s);
    }

    public String getAuthor3() {
        return author3.get();
    }

    public void setAuthor3(String s) {
        this.author3.set(s);
    }

    public String getAuthor4() {
        return author4.get();
    }

    public void setAuthor4(String s) {
        this.author4.set(s);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String s) {
        this.publisher.set(s);
    }

    public String getYearOfPublication() {
        return yearOfPublication.get();
    }

    public void setYearOfPublication(String s) {
        this.yearOfPublication.set(s);
    }

    public String getISBN() {
        return ISBN.get();
    }

    public void setISBN(String s) {
        this.ISBN.set(s);
    }

    public String getSuggestedPrice() {
        return suggestedPrice.get();
    }

    public void setSuggestedPrice(String s) {
        this.suggestedPrice.set(s);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String s) {
        this.notes.set(s);
    }

    public String getBookCondition() {
        return bookCondition.get();
    }

    public void setBookCondition(String s) {
        this.bookCondition.set(s);
    }

    public String getStatus() {
        return Status.get();
    }

    public void setStatus(String s) {
        this.Status.set(s);
    }

    public String getPrefix() {
        return prefix.get();
    }

    public void setPrefix(String s) {
        this.prefix.set(s);
    }
}
