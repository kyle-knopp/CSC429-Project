// specify the package
package model;

// system imports


import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory {

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType)
			throws Exception {
		Transaction retValue = null;

		/*if (transType.equals("InsertNewBook")) {
			retValue = new InsertBookTransaction();
		} else if (transType.equals("InsertNewPatron")) {
			retValue = new InsertPatronTransaction();
		} else if (transType.equals("SearchBooks")) {
			retValue = new BookSearchTransaction();
		} else if (transType.equals("SearchPatrons")) {
			retValue = new PatronSearchTransaction();
		} else */if (transType.equals("AddBook")) {
			retValue = new AddBookTransaction();
		}
		else if(transType.equals("ModifyBook"))
		{
			retValue = new ModifyBookTransaction();
		}
		else if(transType.equals("AddStudentBorrower"))
		{
			retValue = new AddStudentBorrowerTransaction();

		}
		else if(transType.equals("AddWorker"))
		{
			retValue = new AddWorkerTransaction();

		}
		return retValue;
	}
}