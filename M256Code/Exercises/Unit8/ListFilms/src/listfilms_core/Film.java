package listfilms_core;

public class Film
{
    /** the unique title of the film */
    private String title;
    
    /** the number of copies available */
    private int numberAvailable; //not used in this scenario
    
    public Film(String aTitle)
    {
        title = aTitle;
    }

    public String getTitle()
    {
        return title;
    }
}
