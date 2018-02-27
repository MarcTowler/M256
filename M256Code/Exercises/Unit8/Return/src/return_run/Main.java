
package return_run;

import java.util.*;
import m256date.*;
import return_core.*;

public class Main
{
    
    public static void main(String[] args)
    {
        /** Part 1 -- set up the 'before' scenario by first doing a borrow*/
        /** Part 1a -- create Film, DVD and Member objects */
        Film film4 = new Film("Casablanca",1);
        
        DVD dvd6 = new DVD(film4);
        
        Member member11 = new Member();
         
        System.out.println("At start:");
        System.out.println("Number of DVDs of film4 not on loan = " 
                            + film4.getNumberAvailable());
        System.out.println();
        
        /** Part 1b -- create the coordinating object */
        LibCoord library = new LibCoord();
        
        /** Part 1c -- send the coordinating message */
        library.borrowDVD(dvd6, member11);
        
        /** Part 1d -- see if the loan has been recorded */
        System.out.println("After borrow:");
        System.out.println("member11 has " +
                member11.getLoansAndFilms().size() + " current loan(s)");
        System.out.println("member11 is linked to " + member11.getLoansAndFilms().keySet());
        System.out.println("dvd6 is linked to " +
                dvd6.getLoan());
        System.out.println("Number of DVDs of film4 not on loan = " 
                                + film4.getNumberAvailable());
        
        System.out.println();
        
        /** The set up for return is now completed */
        
        /** Part 2 -- return the DVD  */
        library.returnDVD(dvd6);
        
        /** Part 3 -- check whether the return has been recorded */
        System.out.println("After return:");
        System.out.println("member11 has " +
                member11.getLoansAndFilms().size() + " current loans");
        System.out.println("member11 is linked to " + member11.getLoansAndFilms().keySet());
        System.out.println("dvd6 is linked to " +
                dvd6.getLoan());
        System.out.println("Number of DVDs of film4 not on loan = " 
                                + film4.getNumberAvailable());
    }    
}
