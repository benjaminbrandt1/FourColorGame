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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
//Test that the puzzle mode button works
public class TitleScreenPuzzleModeTest {

    @Rule
    public ActivityTestRule<TitleScreen> mActivityTestRule = new ActivityTestRule<>(TitleScreen.class);

    @Test
    public void titleScreenPuzzleModeTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.puzzle_mode), withText("Puzzle Mode"),
                        withParent(allOf(withId(R.id.activity_title_screen),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

    }

}
