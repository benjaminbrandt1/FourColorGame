package edu.temple.fourcolorgame.Activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.temple.fourcolorgame.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CustomMapCancelTest {

    @Rule
    public ActivityTestRule<TitleScreen> mActivityTestRule = new ActivityTestRule<>(TitleScreen.class);

    @Test
    public void customMapCancelTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.puzzle_mode), withText("Puzzle Mode"),
                        withParent(allOf(withId(R.id.activity_title_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.custom_map), withText("Custom"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.custom_map_num_regions), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.map_choices_panel),
                                        0),
                                2),
                        isDisplayed()));
        editText.check(matches(withText("10")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.custom_map_cancel), withText("Cancel"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.custom_map),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.map_choices_panel),
                                        0),
                                4),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

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
