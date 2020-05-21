package com.example.b7sport;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecyclerViewArenaTest {
@Before
    public void setup() throws InterruptedException {
        while  (RecyclerViewArena.groundList.size()==0)
            Thread.sleep(1000);

    }
    @Rule
    public ActivityTestRule<RecyclerViewArena> mActivityTestRule = new ActivityTestRule<>(RecyclerViewArena.class);

    @Test
    public void recyclerViewArenaTest() throws InterruptedException {
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.linear1),
                        childAtPosition(
                                allOf(withId(R.id.main_list),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.gr_type1), withText("סוג מגרש : מגרש כדורסל – 19X32 מ'"),
                        childAtPosition(
                                allOf(withId(R.id.la),
                                        childAtPosition(
                                                withId(R.id.sc),
                                                0)),
                                2),
                        isDisplayed()));
        textView.check(matches(withText("סוג מגרש : מגרש כדורסל – 19X32 מ'")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.gr_street1), withText("כביש : מאיר יערי"),
                        childAtPosition(
                                allOf(withId(R.id.la),
                                        childAtPosition(
                                                withId(R.id.sc),
                                                0)),
                                3),
                        isDisplayed()));
        textView2.check(matches(withText("כביש : מאיר יערי")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.gr_activity1), withText("פעילות : פתוח ללא הגבלה"),
                        childAtPosition(
                                allOf(withId(R.id.la),
                                        childAtPosition(
                                                withId(R.id.sc),
                                                0)),
                                7),
                        isDisplayed()));
        textView3.check(matches(withText("פעילות : פתוח ללא הגבלה")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
