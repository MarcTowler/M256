/*
 * SchoolClient.java
 *
 * Created on 16 January 2006, 09:25
 */

package schoolclient;

import schoolcore.*;
import java.util.*;   

/**
 *
 * @author slm28
 */
public class SchoolClient
{
    
    public static void main(String[] args)
   {
        //Set up a reference to a SchoolCoord object
        SchoolCoord school = SchoolCoord.getSchoolCoordObject();
	
        //print out the details of the SchoolCoord object
        System.out.println("School details are:");
        System.out.println(school);
        
        //get the forms 
        Collection<Form> clientForms = school.getForms();
        
       //Now 'clear' the forms
        clientForms.clear();
        
        //again, print out the details of the SchoolCoord object
        System.out.println("School details are now:");
        System.out.println(school);       
    }        
    
}
