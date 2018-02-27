
package listfilms_core;


public class DVD
{
    
    /** the unique identifying number of the DVD */
    private int number; // This is not used in this scenario
    
    /** a link to the film of which this is a copy */
    private Film film;
    
    public DVD(Film aFilm)
    {
        film = aFilm;
    }

    public Film getFilm()
    {
        return film;
    }
}
