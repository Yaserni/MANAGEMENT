package com.example.b7sport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class blockusaerLogic {
    public boolean checkemail(String Name1)
    {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(Name1);


        if(mat.matches()){

            return true;
        }else{

            return false;
        }


    }

    public   boolean empty(String Name1) {
        if (Name1.equals(""))
            return true;
        else
            return false;
    }

}
