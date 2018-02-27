package schoolcore;
import java.util.*; 
import java.io.*;   
import m256date.*; 

 /**
 * The coordinating class for the School core system. Public messages in the 
 * protocol of this class enable clients to interact with the schoolcore package.
 */
public class SchoolCoord implements java.io.Serializable
{
    /** A collection of all the forms in the school.*/ 
    private Collection<Form> forms; 
        
    /** The maximum number of pupils per form.*/
    public static final int MAX_FORM_SIZE = 10; 
    
    /**
     * Returns all the forms in the school.
     *
     * @return     an unmodifiable collection of all the Form objects
     */
    public Collection<Form> getForms() 
    {
        return Collections.unmodifiableCollection(forms); 
    }
       
    /**
     * Returns the teacher of the form. 
     *
     * @param aForm      a form
     * @return           a Teacher object 
     */
    public Teacher getTeacher(Form aForm) 
    {
        return aForm.getTeacher();
    }
        
    /**
     * Returns all the pupils in the form. 
     *
     * @param aForm      a form
     * @return           a collection of Pupil objects
     */
    public Collection<Pupil>  getPupils(Form aForm) 
    {
        return aForm.getPupils();
    }
   
    /**
     * Creates a new pupil with the given name and birth date
     * and adds the pupil into the form.
     *
     * @param aName               the name of the pupil
     * @param aBirthDate          the birth date of the pupil
     * @param aForm               a form 
     *
     * @throws     IllegalArgumentException        if the pupil's age is invalid (less than 4
     *                                             or more than 18)
     * @throws     IllegalStateException          if the form is full (10 pupils)
     *                                               
     */
    public void enrolPupil(String aName, M256Date aBirthDate, Form aForm) 
    {
        Pupil thePupil = new Pupil(aName, aBirthDate);
        aForm.addPupil(thePupil); 
    }

    /**
     * Returns the teacher with the most pupils in their form.
     * If there is more than one such teacher, returns one of them.
     *
     * @return     a Teacher object, or null if there are no pupils
     */
    public Teacher getTeacherWithMostPupils()
    {
        int size = -1;
        Form bigForm = null; 
        for (Form f : forms)             //iterate through all the forms
        {        
            if (f.getSize() > size)      // if this is the biggest form so far...
            { 
                 size = f.getSize();    //...set size to this form's size...
                 bigForm = f;           //...and set bigForm to reference this form.
            }
        }
        if (size > 0)
            return bigForm.getTeacher();   
        else
            return null;
    }

/**
     * Returns the oldest pupil in the form.
     *
     * @param aForm          a form
     * @return               a Pupil object or null if the form is empty
     */
    public Pupil getOldestPupil(Form aForm)
    {
        return aForm.getOldestPupil(); 
    }
    
    /**
     * Returns a string representation of the receiver.
     * This consists of details of each form.
     *
     * @return       a String object containing information about the school
     */
    public String toString() 
    {
        return forms.toString();
    }

    /**
     * A helper method to read the initial state of the school (in terms of form
     * names and teacher names) from a file.
     */
    private void readFormDetails()
    {
        Scanner fileScanner = null;
        try
        {
           fileScanner = new Scanner(new BufferedReader(new FileReader ("forms.csv")));
           String formDetails;
           String formName;
           String teacherName;
           Scanner formScanner;
           int formNumber = 0;
           while (fileScanner.hasNextLine())
           {
              formDetails = fileScanner.nextLine();
              formScanner = new Scanner(formDetails);
              formScanner.useDelimiter(",");
              try
              {
                 formName = formScanner.next();
                 teacherName = formScanner.next();
                 forms.add(new Form(formName, new Teacher(teacherName)));
              }      
              catch (Exception anException)
              {
                 System.out.println(anException + ": Form corrupted");
              }
              formNumber++;
           }
        }
        catch (Exception anException)
        {
           System.out.println("Error: " + anException);
        }
        finally
        {
           fileScanner.close();
        }
    }
      
    /** Private constructor; initialises the state of the core system.*/
    private SchoolCoord() 
    {
        forms = new HashSet<Form>();
        /* Reads the initial information of the name of forms and their 
           teachers from a file */
        readFormDetails();
    }
        
    /**
     * Creates and returns a new SchoolCoord object.
     * Reads in the state of the object from the  file School.data; 
     * if there is no such file, returns the object in its initial state.
     *
     * @return   a new SchoolCoord object
     */
    public static SchoolCoord getSchoolCoordObject()
    {
        SchoolCoord school = null;
        try
        {
            FileInputStream fis = new FileInputStream("School.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            school = (SchoolCoord)ois.readObject();
        }
        catch(FileNotFoundException ex)
        {
            school =  new SchoolCoord();
        }
        catch(Exception ex)
        {
            System.out.println("Initialisation error. You may need to delete the data file. ");
            System.exit(1);
        }
        return school;
    }

    /**
     * Creates and returns a new SchoolCoord object.
     * @return a new SchoolCoord object
     */
    public static SchoolCoord resetSchoolCoordObject()
    {
        return new SchoolCoord();
    }
        
    /**
      * Saves the state of the receiver to the file School.data.
      */
    public void save()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("School.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        }
        catch(Exception ex)
        {
            System.out.println("Problem storing state of school");
            System.exit(1);
        }
    }
    
}
