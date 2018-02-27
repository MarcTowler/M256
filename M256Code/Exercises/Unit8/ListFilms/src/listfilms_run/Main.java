
package listfilms_run;

import java.util.*;
import m256date.*;
import listfilms_core.*;

public class Main
{
    public static void main(String[] args)
    {
        /** Part 1 -- set up the scenario */
        Film film7 = new Film("The African Queen");       
        Film film3 = new Film("Spellbound");
        
        DVD dvd2 = new DVD(film7);        
        DVD dvd4 = new DVD(film7);
        DVD dvd7 = new DVD(film3);
        
        Loan loan23 = new Loan(dvd2);
        Loan loan27 = new Loan(dvd7);
        Loan loan29 = new Loan(dvd4);
        
        Collection<Loan> loans = new HashSet<Loan>();
        loans.add(loan23);
        loans.add(loan27);
        loans.add(loan29);
       
        Member member6 = new Member(loans);
              
        /** Part 2 -- create the coordinating object */
        LibCoord library = new LibCoord();
        
        /** Part 3 -- send the coordinating message */
        Map<Loan, Film> results =
                library.getLoansAndFilms(member6);
        
        /** Part 4 -- simulate the user interface displaying the results */
        Film aFilm;
        /** for each Loan, aLoan, in the map results... */
        for (Loan aLoan : results.keySet())
        {
            /** ...get the Film corresponding to aLoan */
            aFilm = results.get(aLoan);
            System.out.println("Film: " + aFilm.getTitle());
            System.out.println("Return date: " + aLoan.getReturnDate());
        }
    }   
}
