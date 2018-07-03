package hu.mandaline.bakingmoni;

       import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
       import static android.support.test.espresso.matcher.ViewMatchers.withText;

       import android.support.test.espresso.contrib.RecyclerViewActions;
        import android.support.test.filters.LargeTest;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetail_RecylerViewTest {

    @Rule
    public ActivityTestRule<RecipeMainActivity> activityActivityTestRule =
            new ActivityTestRule<>(RecipeMainActivity.class);

    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //onView(withId(R.id.rv_recipes))             .perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tv_step))
                .check(matches(isDisplayed()));


    }

}