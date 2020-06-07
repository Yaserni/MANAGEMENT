package com.example.b7sport;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTest {
    Logic l=new Logic();

      @Test
    public void test_password()
      {

          boolean result=l.PasswordIsEmpty("");
          assertEquals(result,true);
      }
    @Test

    public void test_password_notempty()
    {

        boolean result=l.PasswordIsEmpty("as");
        assertEquals(result,false);
    }
      @Test
      public  void PasswordLength()
      {
          assertEquals(l.PasswordLength("1234456"),false);

      }
    @Test
    public  void PasswordLength_lowerthan6()
    {
        assertEquals(l.PasswordLength("1234"),true);

    }

      @Test
    public void EmailRequired(){

          assertEquals(l.EmailRequired("a@m.com"),false);

      }
    @Test
      public void EmailRequired_empty(){

        assertEquals(l.EmailRequired(""),true);

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
        @Test
    public void CheckGrName()
    {
        assertEquals(l.CheckGrName(""),false);
    }

    public void CheckNumber()
    {
        assertEquals(l.CheckNumber(-3),false);

    }
}
