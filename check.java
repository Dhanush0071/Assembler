public class check 
{

	public static int has_Variable(String[] Variables,String temp,int var_count)
	{
	    int h=0;
		int index=-1;
		while(h<var_count)
		{
			String t=Variables[h];
			if(temp.toString().compareTo(t.toString())==0)
			{
				index=h;
			}
			h=h+1;
		}
		
		return index;
	}
	public static int has_label(String[] labels,String temp,int label_count)
	{
	    int h=0;
      int index=-1;
		while(h<label_count)
		{
			String t=labels[h];
			
			if(temp.toString().compareTo(t.toString())==0)
			{
			
				 index=h;
			}
        h=h+1;
			
		}

         
		return index;
	}
	public static int has_predefined_variable(String[] pdf,String temp,int pdf_count)
	{
		int h=0;
		int index=-1;
	
		while(h<pdf_count)
		{
			String t=pdf[h];
			if(temp.toString().compareTo(t.toString())==0) 
			{
				
				index=h;
			}
			h=h+1;
		}
		return index;
	}
	

}