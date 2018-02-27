
package return_core;

import java.util.*;


public class Member
{
    /** the member’s unique membership number */
    private int number; //This is not used in this scenario
    
    /** the member’s name */
    private String name;  //This is not used in this scenario
    
    /** the member's current loans */
    private Collection<Loan> loans;
    
    public Member()
    {
        loans = new HashSet<Loan>();
    }
    
    public void addLoan(Loan aLoan)
    {
        loans.add(aLoan);
    }
    
    public void removeLoan(Loan aLoan)
    {
        loans.remove(aLoan);
    }
    
    public Map<Loan, Film> getLoansAndFilms()
    {
        Map<Loan, Film> results =
                new HashMap<Loan, Film>();
        Film aFilm;
        DVD aDVD;
        for (Loan aLoan : loans)
        {
            aFilm = aLoan.getFilm();
            results.put(aLoan, aFilm);
        }
        return results;
    }   
}

