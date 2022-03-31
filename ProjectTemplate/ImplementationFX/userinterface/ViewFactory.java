package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("LoginView")==true){
			return new LoginView(model);
		}
		else if(viewName.equals("LibrarianView") == true)
		{
			return new LibrarianView(model);
		}

		else if(viewName.equals("AddStudentBorrowerView") == true)
		{
			return new AddStudentBorrowerView(model);
		}
		else if(viewName.equals("SearchStudentBorrowerView") == true)
		{
			return new SearchStudentBorrower(model);
		}
		else if(viewName.equals("StudentBorrowerCollectionDeleteView") == true)
		{
			return new StudentBorrowerCollectionDeleteView(model);
		}
		else if(viewName.equals("DeleteStudentBorrowerView") == true)
		{
			return new DeleteStudentBorrowerView(model);
		}

		else if(viewName.equals("SearchStudentBorrowerViewM") == true)
		{
			return new SearchStudentBorrowerViewM(model);
		}
		else if(viewName.equals("StudentBorrowerCollectionModifyView") == true)
		{
			return new StudentBorrowerCollectionModifyView(model);
		}
		else if(viewName.equals("ModifyStudentBorrowerView") == true)
		{
			return new ModifyStudentBorrowerView(model);
		}
		else if(viewName.equals("AddBookView") == true)
		{
			return new AddBookView(model);
		}
		else if(viewName.equals("AddWorkerView") == true)
		{
			return new AddWorkerView(model);
		}
		else if(viewName.equals("EnterBookBarcodeView") == true)
		{
			return new EnterBookBarcodeView(model);
		}
		else if(viewName.equals("ModifyBookView") == true)
		{
			return new ModifyBookView(model);
		}else if(viewName.equals("DeleteABookView") == true)
		{
			return new DeleteABookView(model);
		}
		else if(viewName.equals("SearchWorkerViewD") == true)
		{
			return new SearchWorkerViewD(model);
		}
		else if(viewName.equals("WorkerCollectionDeleteView") == true)
		{
		return new WorkerCollectionDeleteView(model);
		}
		else if(viewName.equals("DeleteWorkerView") == true)
		{
			return new DeleteWorkerView(model);
		}
		else if(viewName.equals("SearchWorkerViewM") == true)
		{
			return new SearchWorkerViewM(model);
		}
		else if(viewName.equals("WorkerCollectionModifyView") == true)
		{
			return new WorkerCollectionModifyView(model);
		}
		else if(viewName.equals("ModifyWorkerView") == true)
		{
			return new ModifyWorkerView(model);
		}


		System.out.println("no View created "+viewName );
		return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else if(viewName.equals("DeleteStudentBorrowerView") == true)
		{
			return null; //new DeleteStudentBorrowerView(model);
		}
		else
			return null;
	}
	*/

}
