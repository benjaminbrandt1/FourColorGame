package edu.temple.fourcolorgame.Activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.temple.fourcolorgame.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ColorSelectionUniqueTest {

    @Rule
    public ActivityTestRule<TitleScreen> mActivityTestRule = new ActivityTestRule<>(TitleScreen.class);

    @Test
    public void colorSelectionUniqueTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.puzzle_mode), withText("Puzzle Mode"),
                        withParent(allOf(withId(R.id.activity_title_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.large_map), withText("Large"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.game_details_next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_game_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.first_color_choice),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction textView = onView(
                allOf(withClassName(is("android.widget.TextView")), isDisplayed()));
        textView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.second_color_choice),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withClassName(is("android.widget.TextView")), isDisplayed()));
        textView2.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.third_color_choice),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withClassName(is("android.widget.TextView")), isDisplayed()));
        textView3.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.fourth_color_choice),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withClassName(is("android.widget.TextView")), isDisplayed()));
        textView4.perform(click());

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.first_color_choice),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withClassName(is("android.widget.TextView")), isDisplayed()));
        textView5.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.color_picker_start), withText("Start!"),
                        withParent(allOf(withId(R.id.color_picker_layout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

    }

}
