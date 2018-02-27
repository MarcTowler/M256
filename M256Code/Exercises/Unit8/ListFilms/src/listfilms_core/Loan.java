
package listfilms_core;

import java.util.*;
import m256date.*;

public class Loan
{
    /** the date the DVD was issued */
    private M256Date issueDate; // 
    
    /** a link to the DVD which this is a loan of */
    private DVD dvd;
    
    public Loan(DVD aDVD)
    {
        issueDate = new M256Date();
        dvd = aDVD;
    }
    
    public Film getFilm()
    {
        return dvd.getFilm();
    }
    
    /** returnDate is derived as issueDate + 3 days */
    public M256Date getReturnDate() 
    {
        return issueDate.addDays(3);
    }
}
