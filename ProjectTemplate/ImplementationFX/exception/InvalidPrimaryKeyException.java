// tabs=4
//************************************************************
//	COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
//
// This file is the product of ArchSynergy, Ltd. and cannot be 
// reproduced, copied, or used in any shape or form without 
// the express written consent of ArchSynergy, Ltd.
//************************************************************
//
//	$Source: /cvsroot/ATM/implementation/exception/InvalidPrimaryKeyException.java,v $
//
//	Reason: This class indicates an exception that is thrown 
//			if the primary key is not properly supplied to the 
//			data access model object as it seeks to retrieve 
//			a record from the repository
//
//	Revision History: See end of file.
//
//*************************************************************

/** @author		$Author: smitra $ */
/** @version	$Revision: 1.1 $ */

// specify the package
package exception;

// system imports

// local imports

/** 
 * This class indicates an exception that is thrown if the primary
 * key is not properly supplied to the data access model object as
 * it seeks to retrieve a record from the database
 * 
 */
//--------------------------------------------------------------
public class InvalidPrimaryKeyException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public InvalidPrimaryKeyException(String message)
	{
		super(message);
	}
}

		

//**************************************************************
//	Revision History:
//
//	$Log: InvalidPrimaryKeyException.java,v $
//	Revision 1.1  2004/06/17 04:40:56  smitra
//	First check in
//	
