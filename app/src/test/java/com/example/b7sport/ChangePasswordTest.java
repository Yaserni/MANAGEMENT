package com.example.b7sport;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ChangePasswordTest {
    ChangePassword password = new ChangePassword();

    @Test
    public void checkpas()
    {
        boolean result = password.checknewp("asd123");
        assertThat(result,is(true));
    }

    @Test
    public void checkpas1()
    {
        boolean result = password.checknewp("");
        assertThat(result,is(false));
    }

}