package schoolcore;

/**
 * Represents teachers, each having a name.
 */
public class Teacher implements java.io.Serializable
{
    private String name; /** the name of the teacher*/

   /**
    * Constructor.
    *
    * @param aName the name of the teacher
    */ 
    Teacher(String aName) 
    {
        name = aName;
    }

    /**
     * Returns the name of the teacher.
     *
     * @return        name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Returns a string representation of the receiver.
     * This consists of its name.
     *
     * @return       a String object containing information about the teacher
     */
    public String toString() 
    {
        return name;
    }
    
}