package competitionadmincore;

import java.text.ParseException;
import java.util.*;
import m256date.*;

/**
 * The coordinating class for the competitionAdmin core system.
 * Public messages in the protocol of this class enable clients to 
 * interact with the competitionadmincore package.
 */
public class CompetitionAdminCoord
{
   // links
   /**
    * a collection of all Member objects
    */
   private Collection<Member> members;
   /**
    * a collection of all Category objects
    */
   private Collection<Category> categories;

   /**
    * Constructor. Initialises a new CompetitionAdminCoord object
    * with the default members and categories.
    */
   public CompetitionAdminCoord() throws ParseException
   {
      members = new HashSet<Member>();

      members.add(new Member("Michael Goldrei", new M256Date("01/11/1978")));
      members.add(new Member("Annie Liebovitz", new M256Date("02/10/1949")));
      members.add(new Member("Dorothea Lange", new M256Date("26/05/1895")));
      members.add(new Member("Robert Capa", new M256Date("22/10/1913")));
      members.add(new Member("Ben Archer", new M256Date("15/03/2002")));

      categories = new HashSet<Category>();
      categories.add(new Category("Street Life", true));
      categories.add(new Category("Portrait", false));
      categories.add(new Category("Wildlife", false));
      categories.add(new Category("Erotic", true));
   }

   //public protocol

   /**
    * Returns all the members in the competition.
    *
    * @return a collection of all the Member objects
    */
   public Collection<Member> getMembers()
   {
      return new HashSet<Member>(members);
   }

   /**
    * Returns all the categories in the competition.
    *
    * @return a collection of all the Category objects
    */
   public Collection<Category> getCategories()
   {
      return new HashSet<Category>(categories);
   }

   /**
    * Returns true if the category is an inappropriate category for the member, false otherwise.
    *
    * @param theMember   the member
    * @param theCategory the category
    *
    * @return a boolean
    */
   public boolean isInappropriateCategory(Member theMember, Category theCategory)
   {
      boolean result = false;
      if (theMember.isJunior() && theCategory.isAdult())
      {
         result = true;
      }
      return result;
   }

   /**
    * Returns true if the maximum number of entries in the category have been reached for the member, false otherwise.
    *
    * @param theMember   the member
    * @param theCategory the category
    *
    * @return a boolean
    */
   public boolean hasMaximumEntries(Member theMember, Category theCategory)
   {
      boolean result = false;
      if (theMember.hasMaximumEntries(theCategory))
      {
         result = true;
      }
      return result;
   }

   /**
    * Returns true if the title is a duplicate title for theMember, false otherwise.
    *
    * @param theMember the member
    * @param theTitle the title
    *
    * @return a boolean
    */
   public boolean hasDuplicateTitle(Member theMember, String theTitle)
   {
      boolean result = false;
      if (theMember.hasPhotoWithTitle(theTitle))
      {
         result = true;
      }
      return result;
   }

   /**
    * Returns true if the title references an empty string, false otherwise.
    *
    * @param theTitle the title
    *
    * @return a boolean
    */
   public boolean hasNoTitle(String theTitle)
   {
      return theTitle.equals("");
   }

   /**
    * Records a photo that has been submitted by the member in the category,
    * if it meets the required criteria
    *
    * @param theMember   the member
    * @param theCategory the category  
    * @param theTitle    the title
    *
    * @throws IllegalArgument exception if the category is not appropriate for the member
    *         or the member has maximum entries for that category or the member has already
    *         submitted a photo with the given title or the title is empty.
    */
   public void recordPhoto(Member theMember, Category theCategory, String theTitle)
   {
      if (isInappropriateCategory(theMember, theCategory))
      {
         throw new IllegalArgumentException("Junior member and adult only category");
      }
      else if (hasMaximumEntries(theMember, theCategory))
      {
         throw new IllegalArgumentException("Member has maximum number of photos for category");
      }
      else if (hasDuplicateTitle(theMember, theTitle))
      {
         throw new IllegalArgumentException("Duplicate title for photo");
      }
      else if (hasNoTitle(theTitle))
      {
         throw new IllegalArgumentException("No title provided for photo");
      }
      else
      {
         Photo newPhoto = new Photo(theTitle, theCategory);
         theMember.addPhoto(newPhoto);
      }
   }

   /**
    * Returns a map of pairs (aPhoto, aCategory) where the photos are
    * those taken by the given member and the category is the category of the photo. 
    *
    * @param theMember the Member
    *
    * @return a map of pairs (aPhoto, aCategory)
    */
   public Map<Photo, Category> getPhotosAndCategories(Member theMember)
   {
      return theMember.getPhotosAndCategories();
   }

   /**
    * Returns the number of entries for the member.
    * @param  aMember the Member
    * @return anInt
    */
   public int totalEntries(Member theMember)
   {
      return getPhotosAndCategories(theMember).size();
   }

   /**
    * Returns the category of the Photo argument.
    * @param   thePhoto the photo
    * @return  a Category
    */
   public Category getCategory(Photo thePhoto)
   {
      return thePhoto.getCategory();
   }

   /**
    * Returns a string representation of all categories and members.
    *
    * @return a String object representing the receiver
    *
    */
   @Override
   public String toString()
   {
      return categories.toString() + "\n" + members.toString();
   }
}
