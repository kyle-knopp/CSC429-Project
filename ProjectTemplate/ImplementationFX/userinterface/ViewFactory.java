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
			return new addStudentBorrowerView(model);
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

		System.out.println("no View created");
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
