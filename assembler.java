import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class assembler 
{

	public static void main(String[] args) throws FileNotFoundException 

	{
	    String first="111";
	    String a=null;
	    String third;
	    String fourth;
	    String fifth; 
		String before;
		String after;
		int has_var=-1;
		int has_label=-1;
		int has_pdf=-1;
		String[] comp= {"0","1","-1","D","A","!D","!A","-D","-A","D+1","A+1","D-1","A-1","D+A","D-A","A-D","D&A","D|A","M","!M","-M","M+1","M-1","D+M","D-M","M-D","D&M","D|M"};
		String[] dest = {"0","M","D","MD","A","AM","AD","AMD"};
		String[] jump= {"null","JGT","JEQ","JGE","JLT","JNE","JLE","JMP"};
		String [] comp_binaries= {"101010","111111","111010","001100","110000","001101","110001","001111","110011","011111","110111","001110","110010","000010","010011","000111","000000","010101","110000","110001","110011","110111","110010","000010","010011","000111","000000","010101"};
		String [] dest_binaries = {"000","001","010","011","100","101","110","111"};
		String [] jump_binaries= {"000","001","010","011","100","101","110","111"};
		String[] predefined_var= {"R0","R1","R2","R3","R4","R5","R6","R7","R8","R9","R10","R11","R12","R13","R14","R15","KBD","SCREEN"};
		int[] predefined_var_index= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,24576,16384};
		int comp_len=comp.length;
		int dest_len=dest.length;
		int jump_len=jump.length;
		int nbr=0;
		String temp;
		int spaces=0;
		int var_count=0;
		int label_count=0;
		File access=new File("C:\\eocass\\Max.asm");
	    PrintWriter write = new PrintWriter ("C:\\eocass\\Output.hack");
		Scanner read=new Scanner(access);
		int cou=0;
		//no of variable and label counting
		while(read.hasNextLine())                                      //working
		{
			String s=read.nextLine();
			s=s.trim();
			char[] ch=s.toCharArray();
		cou=cou+1;
			
			if(ch[0]=='@')
			{
				if(ch[1]>='A'&&ch[1]<='Z'||ch[1]>='a'&&ch[1]<='z')
				{
					
					var_count=var_count+1;
					
				}
			}
			
			 if(ch[0]=='(')
			{	
				label_count=label_count+1;
			}

			if(s==" ")
			{
				break;
			}
		}
		
		int[] variable_location=new int[var_count];
		String[] variable=new String[var_count];
		int[] label_location=new int[label_count];
		String[] label=new String[label_count];
		read.close();
		
		
		
		//reading label declaration and storing them
		int count=1;
		int label_index=0;
		read=new Scanner(access);
		while(read.hasNextLine())                   
		{
			String s=read.nextLine();
			s=s.trim();
			char[] ch=s.toCharArray();
			int len=ch.length;
			if(ch[0]=='(')
			{	
				char[] dupl=new char[len-2];
				for(int i=0;i<len-2;i++) 
				{
					dupl[i]=ch[i+1];
					
				}
				
				 temp=new String(dupl);
				 
				label[label_index]=temp;
				label_location[label_index]=count-1;
				label_index=label_index+1;

			}
			else if(s=="  ")
			{	
		         break;
			}
			else 
			{
			count=count+1;
			}
		}
		read.close();

		
		
		//locations of variables and labels with names
		read=new Scanner(access);
		int variable_index=0;
		int loop=0;
		while(read.hasNextLine())
		{
			String s=read.nextLine();
			s=s.trim();
			char[] ch1=s.toCharArray();
			int len=ch1.length;
			int new_len;

			if(ch1[0]=='@')
			{
				if(ch1[1]>='A'&&ch1[1]<='Z'||ch1[1]>='a'&&ch1[1]<='z')
				{
					for(int i=0;i<len-1;i++) 
					{
						if(ch1[i]==' ')
						{
						 spaces=spaces+1;
						}
					}
					if(spaces>0)
					{
					 new_len=(len-2)-spaces;
					}
					else
					 new_len=len-1;
					spaces=0;
					char[] dupl1=new char[new_len];
					for(int i=0;i<new_len;i++) 
					{
						
						dupl1[i]=ch1[i+1];
						
						
					}
					 temp=new String(dupl1);
			
					 has_label= check.has_label(label,temp,label_count);
					 has_var= check.has_Variable(variable,temp,variable_index);
					 has_pdf= check.has_predefined_variable(predefined_var,temp,18);
						

					
						 if(has_label>=0)
							{
								variable[variable_index]=temp;
								variable_location[variable_index]=label_location[has_label];
								variable_index=variable_index+1;
							
							}
						 else if(has_var>=0&&has_pdf>=0)
						 {
							    variable[variable_index]=temp;
								variable_location[variable_index]=predefined_var_index[has_pdf];
								variable_index=variable_index+1;
						 }
						 else if(has_var>=0)
					{
					
						    variable[variable_index]=temp;
							variable_location[variable_index]=variable_location[has_var];
							variable_index=variable_index+1;
							
					}

					else if(has_pdf>=0)
					{
					
						variable[variable_index]=temp;
						variable_location[variable_index]=predefined_var_index[has_pdf];
						variable_index=variable_index+1;
						
					}
					

					else
					{
						variable[variable_index]=temp;
						variable_location[variable_index]=16+loop;
						variable_index=variable_index+1;
						loop=loop+1;
					}
					has_label=-1;
					has_pdf=-1;
					has_var=-1;

					}
				}
			
				
		}
			
			read.close();
		
			//main execution of comp and dest and jump
			int jump_index=0;
			int comp_index=0;

	        read=new Scanner(access);
	        while(read.hasNextLine())
	        {
	        
                nbr=0;
	        	String m=read.nextLine();
	        	m=m.trim();
	        	char[] ach=m.toCharArray();
	        	int length=ach.length;
	        
	        	if(ach[0]=='@')
	        	{
	              
	        		if(ach[1]>='A'&&ach[1]<='Z'||ach[1]>='a'&&ach[1]<='z')
	        		{
	        		
	        			char[] dupl2=new char[length-1];
						for(int i=0;i<length-1;i++) 
						{		
							dupl2[i]=ach[i+1];
						}
						 temp=new String(dupl2);
						  
						for(int i=0;i<var_count;i++)
						{
							String foo=variable[i];
							if(temp.toString().compareTo(foo.toString())==0)
							{
						    
								int x=variable_location[i];
								int[] bin_num=binary_conv.binary(x);
								char[] temp6=new char[16];
							
					             for(int k=0;k<16;k++)
					             {
					             temp6[k]=(char)(bin_num[k]+'0');
					             }
					             String p3=new String(temp6);
					             write.println(p3);
					          
					           
					             break;
					             
						    }
						}
							
						
	        		}
	        		else
	        		{
	        			
	        			for(int j=1;j<length;j++)
	        			{
	        			
	        				int no=Character.getNumericValue(ach[j]); 
	        				
	        			nbr=nbr*10+no;
	        			}
	        			int [] v=binary_conv.binary(nbr);
	        			char[] conve=new char[16];
			             for(int k=0;k<16;k++)
			             {
			             conve[k]=(char)(v[k]+'0');
			             }
			             String p=new String(conve);
			           
			             write.println(p);
			             
	        		}
	        	}
	        	
	        	else
	        	{
	        	for(int e=0;e<length;e++)
	        	{
	        		if(ach[e]==';')
	        		{
	        		jump_index=e;

	        		}
	        		if(ach[e]=='=')
	        		{
	        			comp_index=e;
	        		}
	        	}
	        	
	        	if(jump_index!=0)
	        	{
	        	  a="0";
	        	  third="000000";
                  fourth="000";
                  fifth="000";
				 String[] temp1=m.split(";");
				 before=temp1[0];
				 after=temp1[1];
				 char[] chb=before.toCharArray();
				 int length_bef=chb.length;
				  for(int u=0;u<comp_len;u++)
				  {
					  String j=comp[u];
					  if(before.toString().compareTo(j.toString())==0)
					  {
						  third=comp_binaries[u];
					  }
				  }
				  
				  for(int u=0;u<jump_len;u++)
				  {
					  String j=jump[u];
					  if(after.toString().compareTo(j.toString())==0)
					  {
						  fifth=jump_binaries[u];
					  }
				  }
				 
				  for(int u=0;u<length_bef;u++)
				  {
					 
					  if(chb[u]=='M')
					  {
						a="1";
					  }

				  }
				  write.println(first+a+third+fourth+fifth);
			
				  jump_index=0;
				  
	        	}
	        	
	        	
	        	if(comp_index!=0)
	        	{
	        		  a="0";
	        		  third="000000";
	                  fourth="000";
	                  fifth="000";
	                  
					 String[] temp1=m.split("=");
					 before=temp1[0];
					 after=temp1[1];

					 char[] cha=after.toCharArray();
					 int length_bef=cha.length;
					 for(int u=0;u<comp_len;u++)
					  {
						  String j=comp[u];
						  if(after.toString().compareTo(j.toString())==0)
						  {
							  third=comp_binaries[u];
						
						  }
					  }
					 
					 for(int u=0;u<dest_len;u++)
					  {
						  String j=dest[u];
						  if(before.toString().compareTo(j.toString())==0)
						  {
							  fourth=dest_binaries[u];
							
						  }
					  }
					 
					 for(int u=0;u<length_bef;u++)
					  {
						 
						  if(cha[u]=='M')
						  {
							a="1";
						  }

					  }
					 write.println(first+a+third+fourth+fifth);
					 
	        		comp_index=0;
				}
					
						
	        	}
	        	
	        	}
	        read.close();
	        write.close();	
	        System.out.println("$$  THANKS FOR USING THE B19 ASSEMBLER , CHECK YOUR OUTPUT IN THE DESTINATION FILE $$");
	        }
}