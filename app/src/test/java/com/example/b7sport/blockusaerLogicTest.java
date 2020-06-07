package com.example.b7sport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class blockusaerLogicTest {
    public blockusaerLogic STU;

    @Before
    public  void setup()
    {
        STU=new blockusaerLogic();

    }
   @Test
    public  void chekemail_situck1 ()
    {
        String email="abd";
        Boolean result=STU.checkemail(email);
        assertThat(result,is(false));

    }
    @Test
    public  void chekemail_situck2 ()
    {
        String email="abd@asdsad";
        Boolean result=STU.checkemail(email);
        assertThat(result,is(false));

    }
    @Test
    public  void chekemail_situck3 ()
    {
        String email="abd@asdsad.com";
        Boolean result=STU.checkemail(email);
        assertThat(result,is(true));

    }
    @Test
    public  void chekemptystring1()
    {
        String empty="";
        Boolean result=STU.empty(empty);
        assertThat(result,is(true));

    }
    @Test
    public  void chekemptystring2()
    {
        String empty="12345";
        Boolean result=STU.empty(empty);
        assertThat(result,is(false));

    }









}