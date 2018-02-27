package competitionadmincore;

import java.util.*;

/**
 * Category objects represent the Categories in the competition.
 */
public class Category
{
   //attributes

   /**
    * the name of the Category
    */
   private String name;
   /**
    * whether or not the category is adult only
    *
    * @return a boolean
    */
   private boolean isAdultOnly;

   /**
    * Constructor. Initialises a new Category object with the given name and whether or not it is adult only.
    *
    * @param aName the name of the Category
    * @param aBoolean true if the category is adult only, false otherwise
    */
   public Category(String aName, boolean aBoolean)
   {
      name = aName;
      isAdultOnly = aBoolean;
   }

   //public protocol

   /**
    * Returns the name of this Category.
    *
    * @return name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Returns whether adults or juniors are allowed in this Category.
    *
    * @return isAdultOnly
    */
   public boolean isAdult()
   {
      return isAdultOnly;
   }

   /**
    * Returns a string representation of this category.
    *
    * @return a String object representing the receiver
    *
    */
   @Override
   public String toString()
   {
      return getName();
   }
}
