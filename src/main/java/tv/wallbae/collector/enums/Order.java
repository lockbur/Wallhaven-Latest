package tv.wallbae.collector.enums;

public enum Order
{
   ASC, DESC;

   @Override
   public String toString()
   {
      return name().toLowerCase();
   }
}
