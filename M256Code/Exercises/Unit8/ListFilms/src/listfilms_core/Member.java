
package listfilms_core;

import java.util.*;


public class Member
{
    /** the member’s unique membership number */
    private int number; // not used in this scenario
    
    /** the member’s name */
    private String name; //not used in this scenario
    
    /** the member's current loans */
    private Collection<Loan> loans;
    
    public Member(Collection<Loan> someLoans)
    {
        loans = someLoans;
    }
    
    public Map<Loan, Film> getLoansAndFilms()
    {
        Map<Loan, Film> results =
                new HashMap<Loan, Film>();
        Film aFilm;
        for (Loan aLoan : loans)
        {
            aFilm = aLoan.getFilm();
            results.put(aLoan, aFilm);
        }
        return results;
    }   
}

