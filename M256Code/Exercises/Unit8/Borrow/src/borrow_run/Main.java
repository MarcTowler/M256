
package borrow_run;


import java.util.*;
import m256date.*;
import borrow_core.*;

public class Main
{
    
    public static void main(String[] args)
    {
        /** Part 1 -- set up the 'before' scenario */
        Film film4 = new Film("Casablanca",1);     
        DVD dvd6 = new DVD(film4);
        Member member11 = new Member();
        
        System.out.println("At start:");
        System.out.println("Number of DVDs of film4 not on loan = " 
                            + film4.getNumberAvailable());
        System.out.println("");
        
        /** Part 2 -- create the coordinating object */
        LibCoord library = new LibCoord();        
        
        /** Part 3 -- send the coordinating message */
        library.borrowDVD(dvd6, member11);
        
        /** Part 4 -- use getLoansAndFilms() to see if the new loan is recorded! */
        Map<Loan, Film> results =
                library.getLoansAndFilms(member11);
        System.out.println("After borrow:");
        System.out.println("member11 has the following loans");
        /** displaying the results */
        Film aFilm;
        // for each Loan, aLoan, in the map results...
        for (Loan aLoan : results.keySet())
        {         
            // ...get the Film corresponding to aLoan 
            aFilm = results.get(aLoan); 
            System.out.println("Film: " + aFilm.getTitle());
            System.out.println("Return date: " + aLoan.getReturnDate());           
        }
        System.out.println("Number of DVDs of film4 not on loan = " 
                                + film4.getNumberAvailable());
    }   
}
