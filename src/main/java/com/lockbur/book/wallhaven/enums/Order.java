package com.lockbur.book.wallhaven.enums;

public enum Order
{
   ASC, DESC;

   @Override
   public String toString()
   {
      return name().toLowerCase();
   }
}
