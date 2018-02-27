
package return_core;

public class DVD
{
    
    /** the unique identifying number of the DVD */
    private int number; // This is not used in this scenario
    
    /** a link to the film which this is a copy of */
    private Film film;
    
    /** a link to a loan or null if the DVD is available for loan */
    private Loan loan;
    
    public DVD(Film aFilm)
    {
        //the DVD is initially available
       loan = null;
       film = aFilm;
    }
    
    public Film getFilm()
    {
        return film;
    }
    
    /** This method should be invoked only by the constructor in Loan*/
    public void setLoan(Loan aLoan)
    {
        loan = aLoan;
        film.decrementNumberAvailable();
    }
    
    public void cancelLoan()
    {
        loan.removeLoan();
        loan = null;
        film.incrementNumberAvailable();      
    }
      
    // This method is included solely to check the state after borrow and return
    public Loan getLoan()
    {
        return loan;
    }

}
