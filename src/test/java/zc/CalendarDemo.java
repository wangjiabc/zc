package zc;

import java.util.*;

public class CalendarDemo {

   public static void main(String[] args) {
   
      // create a calendar    
      Calendar cal = Calendar.getInstance();

      cal.set(Calendar.HOUR_OF_DAY, 0);  
      cal.set(Calendar.SECOND, 0);  
      cal.set(Calendar.MINUTE, 0);  
      cal.set(Calendar.MILLISECOND, 0);  
      
      // gets a calendar using the default time zone and locale.
      System.out.print("Date And Time Is: " + cal.getTime());
   }
}
