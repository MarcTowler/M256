
package return_core;


import java.util.*;
import m256date.*;

public class Loan
{
    /** the date the DVD has to be returned */
    private M256Date returnDate;
    
    /** a link to the DVD which this is a loan of */
    private DVD dvd;
    
    /** a link to the Member borrowing the DVD */
    private Member member;
    
    public Loan(DVD aDVD, Member aMember)
    {
        //set return date to issue date plus 3 days
        returnDate = (new M256Date()).addDays(3);
        
        // create an isTo link and a consistsOf link 
        member = aMember;
        aMember.addLoan(this);
        dvd = aDVD;
        aDVD.setLoan(this);
    }
    
    public void removeLoan()
    {
        member.removeLoan(this);    
    }
    
    public Film getFilm()
    {
        return dvd.getFilm();
    }
    
    public M256Date getReturnDate()
    {
        return returnDate;
    }
    
    public String toString()
    {
        return "a Loan object";
    }
    
}
