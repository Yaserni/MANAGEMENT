package com.example.b7sport;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Logic l=new Logic();
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
/*
    @Test
    public void blocktest()
    {
         blockuser b=new blockuser();
         boolean result=b.check_database("abdalsk");
         assertEquals("test",true,result);
    }
    */

      @Test
    public void test_password()
      {
          boolean result=l.PasswordIsEmpty("");
          assertEquals(result,true);
      }
      @Test
      public  void PasswordLength()
      {
          assertEquals(l.PasswordLength("1234456"),false);
      }
      @Test
    public  void EmailRequired(){
          assertEquals(l.EmailRequired("a@m.com"),false);
      }
      @Test
    public void  Emailregx()
      {
          assertEquals(l.EmailRegex("as@gmail.com"),false);
      }
      @Test
    public void checkname()
      {
          assertEquals(l.CheckName("abd"),false);
      }

}
