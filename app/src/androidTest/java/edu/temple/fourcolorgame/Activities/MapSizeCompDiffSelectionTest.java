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
public class MapSizeCompDiffSelectionTest {

    @Rule
    public ActivityTestRule<TitleScreen> mActivityTestRule = new ActivityTestRule<>(TitleScreen.class);

    @Test
    public void mapSizeCompDiffSelectionTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.vs_ai_mode), withText("Vs. Computer Player"),
                        withParent(allOf(withId(R.id.activity_title_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.game_details_next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_game_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.computer_skills_title), withText("Computer Player Difficulty:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.computer_player_skill_fragment),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Computer Player Difficulty:")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.computer_skill_easy), withText("Easy"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.game_details_next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_game_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.large_map),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.map_choices_panel),
                                        0),
                                3),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.large_map), withText("Large"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.game_details_next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_game_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.color_picker_title), withText("Choose Your Colors:"),
                        childAtPosition(
                                allOf(withId(R.id.color_picker_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Choose Your Colors:")));

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
