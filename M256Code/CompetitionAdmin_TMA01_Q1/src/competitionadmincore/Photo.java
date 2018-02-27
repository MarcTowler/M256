/*
 * 
 */
package competitionadmincore;

import m256date.M256Date;

/**
 *
 * Photo objects represent the photos submitted to the competition
 */
public class Photo
{

   // attributes

   /**
    * the title of the photo
    */
   private String title;

   //links

   /**
    *  the Category of the photo
    */
   private Category category;

   /**
    * Constructor. Initialises a new Photo object with the
    * given title, and creates a link with
    * the given Category object. 
    *
    * @param aTitle        the title of the photo
    * @param aCategory     the Category of the photo
   
    */
   public Photo(String aTitle, Category aCategory)
   {
      title = aTitle;
      category = aCategory;
   }

   // public protocol

   /**
    * Returns the title of this photo.
    *
    * @return title
    */
   public String getTitle()
   {
      return title;
   }

   /**
    * Returns a string representation of the photo.
    *
    * @return a String object representing the receiver
    */
   @Override
   public String toString()
   {
      return title;
   }

   // package protocol

   /**
    * Returns the Category of this photo.
    *
    * @return a Category object
    */
   Category getCategory()
   {
      return category;
   }

   
}
