
package borrow_core;

public class Film
{
    /** the unique title of the film */
    private String title;
    
    /** the number of copies available */
    private int numberAvailable;
    
    public Film(String aTitle, int aNumber)
    {
        title = aTitle;
        numberAvailable = aNumber;
    }

    public int getNumberAvailable()
    {
        return numberAvailable;
    }
    
    public void decrementNumberAvailable()
    {
        numberAvailable--;
    }

    public String getTitle()
    {
        return title;
    }
}
