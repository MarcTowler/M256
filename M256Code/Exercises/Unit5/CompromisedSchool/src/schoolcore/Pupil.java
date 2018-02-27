package schoolcore;

import java.util.*;
import m256date.*;

/**
 * Represents pupils, each having a name and a date of birth.
 */
public class Pupil implements java.io.Serializable
{
    private String name;            /** the name of the pupil*/
    private M256Date birthDate;     /** the birth date of the pupil*/
    
    /**
    * Constructor.
    *
    * @param aName the name of the pupil
    * @param aBirthDate the birth date of the pupil
    *
    * @throws IllegalArgumentException if the pupil's age 
    * is invalid (less than 4 or more than 18).
    */
    Pupil(String aName, M256Date aBirthDate) 
    {
        name = aName;
        int age = aBirthDate.getAge();
        if ( (age < 4) || (age > 18) )
        {
            throw new IllegalArgumentException("Invalid birth date"); 
        }
        else
        {
            birthDate = aBirthDate;
        }
    }
    
    /**
     * Returns the name of the pupil.
     *
     * @return    name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * Returns the birth date of the pupil.
     *
     * @return   birthDate
     */
    public M256Date getBirthDate()
    {
      return birthDate;
    }

    /**
     * Returns a string representation of the receiver.
     * This is the receiver's name and birth date.
     *
     * @return       a String object containing information about the pupil
     */
    public String toString() 
    {
       return name + " " + birthDate;
    }

 }