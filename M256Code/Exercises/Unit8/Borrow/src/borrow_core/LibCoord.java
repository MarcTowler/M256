
package borrow_core;

import java.util.*;


public class LibCoord
{
    
    public LibCoord()
    {
    }
    
    public Map<Loan, Film> getLoansAndFilms(Member aMember)
    {
        return aMember.getLoansAndFilms();
    }
    
    public void borrowDVD(DVD aDVD, Member aMember)
    {
        new Loan(aDVD, aMember);
    }
}
