package competitionadmincore;

import java.util.*;
import m256date.*;

/**
 * Member objects represent the members who are submitting photos in the competition.
 */
public class Member
{

   // class constant
   /**
    * the maximum number of entries for this member in a category
    */
   static final int MAXENTRIES = 3;

   //attributes

   /**
    * the name of the member
    */
   private String name;

   /**
    * the date of birth of the member
    */
   private M256Date dob;

   // links

   /**
    * all the member's photos
    */
   private Collection<Photo> photos;

   /**
    * Constructor. Initialises a new Member object with
    * the given name and date of birth.  Initialises photos to an empty set.
    *
    * @param aName the name of this member
    * @param aDob  the date of birth of this member
    */
   public Member(String aName, M256Date aDob)
   {
      name = aName;
      dob = aDob;
      photos = new HashSet<Photo>();
   }

   //public protocol

   /**
    * Returns the name of this member.
    *
    * @return name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Returns the date of birth of this member
    *
    * @return dob
    */
   public M256Date getDob()
   {
      return dob;
   }

    /**
    * Returns a string representation of this member.
    *
    * @return a String object representing the receiver
    */
   @Override
   public String toString()
   {
      String tempString = "Adult";
      if (isJunior())
      {
         tempString = "Junior";
      }
      return name + " (" + tempString + ")";
   }

   // package protocol

   /**
    * Determines if the member is a junior
    *
    * @return true if the member is a junior, false otherwise
    */
   boolean isJunior()
   {
      return dob.getAge() < 18;
   }

   /**
    * Returns a collection of all of the photos of this member.
    *
    * @return Collection of photos
    */
   Collection<Photo> getPhotos()
   {
      return photos;
   }

   /**
    * Adds the given photo to the collection of photos for this member.
    *
    * @param thePhoto the photo to be added
    */
   void addPhoto(Photo thePhoto)
   {
      photos.add(thePhoto);
   }

   /**
    * Returns a map of pairs (aPhoto, aCategory) where the photos are
    * those taken by this member and the category is the category of the photo. 
    *
    * @return a map of pairs (aPhoto, aCategory)
    */
   Map<Photo, Category> getPhotosAndCategories()
   {
      Map<Photo, Category> tempMap = new HashMap<Photo, Category>();
      for (Photo photo : photos)
      {
         tempMap.put(photo, photo.getCategory());
      }
      return tempMap;
   }

   /**
    * Returns whether this member has a photo with the given title.
    * 
    * @param theTitle a title
    * @return a boolean 
    */
   boolean hasPhotoWithTitle(String theTitle)
   {
      for (Photo aPhoto : photos)
      {
         if (aPhoto.getTitle().equals(theTitle))
         {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns whether this member has made the maximum number of entries in the given category. 
    * 
    * @param theCategory a category
    * @return a boolean 
    */
   boolean hasMaximumEntries(Category theCategory)
   {
      int count = 0;
      for (Photo aPhoto : photos)
      {
         if (aPhoto.getCategory().equals(theCategory))
         {
            count++;
         }
      }
      return count >= MAXENTRIES;
   }

  
}
