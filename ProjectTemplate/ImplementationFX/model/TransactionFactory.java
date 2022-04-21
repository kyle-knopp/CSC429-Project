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

		if (transType.equals("AddBook")) {
			retValue = new AddBookTransaction();
		}
		else if(transType.equals("ModifyBook"))
		{
			retValue = new ModifyBookTransaction();
		}
		else if(transType.equals("DeleteBook"))
		{
			retValue = new DeleteBookTransaction();
		}
		else if(transType.equals("AddStudentBorrower"))
		{
			retValue = new AddStudentBorrowerTransaction();

		}
		else if(transType.equals("AddWorker"))
		{
			retValue = new AddWorkerTransaction();

		}else if(transType.equals("DelinquencyCheck")){
			retValue=new DelinquencyCheckTransaction();
		}
		else if(transType.equals("CheckIn"))
		{
			retValue = new CheckInBookTransaction();

		}else if(transType.equals("CheckOut")){
			retValue=new CheckOutBookTransaction();
		}

		return retValue;
	}
}