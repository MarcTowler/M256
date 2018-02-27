package schoolcore;

import java.util.*;
import m256date.*;

/**
 * Represents forms, each having a name, a teacher and a group of pupils.
 */
public class Form implements java.io.Serializable
{
    
    private String name;                /** the name of the form*/
    private Teacher teacher;            /** the teacher of the form*/
    private Collection<Pupil> pupils;   /** a collection of the pupils in the form*/
    
    /**
     * Constructor.
     *
     * @param aName the  name of the form
     * @param aTeacher the teacher of the form
     */
    Form(String aName, Teacher aTeacher)
    {
        name = aName;
        teacher = aTeacher;
        pupils = new HashSet<Pupil>();
    }
    
    /**
     * Returns the size of the form (the number of pupils  in it).
     *
     * @return the size of the receiver
     */
    public int getSize()
    {
        return pupils.size();
    }
    
    /**
     * Returns the name of the form.
     *
     * @return name 
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the teacher of the form.
     *
     * @return a Teacher object.
     */
    Teacher getTeacher()
    {
        return teacher;
    }
        
    /**
     * Returns the pupils in the form.
     *
     * @return an unmodifiable collection of Pupil objects.
     */
    Collection<Pupil> getPupils()
    {
        return Collections.unmodifiableCollection(pupils);       
    }
    
    /**
     * Adds the pupil into the form. 
     * 
     * @param aPupil  a pupil
     *
     * @throws IllegalStateException   when the receiver is full 
     *                                  (and does not place the pupil in the receiver).
     */
    void addPupil(Pupil aPupil) 
    {
        if (pupils.size() >= SchoolCoord.MAX_FORM_SIZE)
        {
            throw new IllegalStateException("Form full");
        }
        else
        {
            pupils.add(aPupil);
        }
    }
    
    /**
     * Returns the oldest pupil in the form.
     *
     * @return a Pupil object corresponding to the oldest pupil in
     *         the form or null if the form is empty.
     */
    Pupil getOldestPupil()
    {
        M256Date birthDate;
        M256Date firstBirthDate = new M256Date(); //set firstBirthDate to today's date
        Pupil oldestPupil = null;
        for (Pupil p : pupils)          //iterate through the receiver's pupils
        {
            birthDate = p.getBirthDate(); 
            if (birthDate.before(firstBirthDate))//if this is the oldest pupil so far...
            {
                firstBirthDate = birthDate;     //...set firstBirthDate to this pupil's birth date...
                oldestPupil = p;             //...and set oldestPupil to this pupil.
            }
        }
        return oldestPupil;
    }
    
    /**
     * Returns a string representation of the receiver.
     * This consists of its name, and the
     * details of its teacher and pupils.
     *
     * @return a String object containing information about the form
     */
    public String toString()
    {
        return name + " " + teacher.toString() + " " + pupils.toString();
    }
    
}