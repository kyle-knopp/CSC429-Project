// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	/*public static Transaction createTransaction(String transType,
		AccountHolder cust)
		throws Exception
	{
		Transaction retValue = null;

<<<<<<< Updated upstream
		if (transType.equals("Deposit") == true)
		{
			retValue = new DepositTransaction(cust);
		}
		else
		if (transType.equals("Withdraw") == true)
		{
			retValue = new WithdrawTransaction(cust);
		}
		else
		if (transType.equals("Transfer") == true)
		{
			retValue = new TransferTransaction(cust);
		}
		else
		if (transType.equals("BalanceInquiry") == true)
		{
			retValue = new BalanceInquiryTransaction(cust);
		}
		else
		if (transType.equals("ImposeServiceCharge") == true)
		{
			retValue = new ImposeServiceChargeTransaction(cust);
		}

=======
		/*if (transType.equals("InsertNewBook")) {
			retValue = new InsertBookTransaction();
		} else if (transType.equals("InsertNewPatron")) {
			retValue = new InsertPatronTransaction();
		} else if (transType.equals("SearchBooks")) {
			retValue = new BookSearchTransaction();
		} else if (transType.equals("SearchPatrons")) {
			retValue = new PatronSearchTransaction();
		} else */if (transType.equals("AddBook")) {
			System.out.println("creating transcation add");

			retValue = new AddBookTransaction();
		}
		else if(transType.equals("ModifyBook"))
		{
			System.out.println("creating transcation modify");

			retValue = new ModifyBookTransaction();

		}
>>>>>>> Stashed changes
		return retValue;
	}*/
}
