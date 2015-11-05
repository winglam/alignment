package edu.illinois.cs.alignment.Main;

import java.util.Arrays;
import java.util.List;

import edu.illinois.cs.alignment.data.Activity;
import edu.illinois.cs.alignment.data.InputField;
import edu.illinois.cs.alignment.data.LogElement;
import edu.illinois.cs.alignment.util.Constants;

/**
 * Created by winglam on 10/29/15.
 */
public class Main {

    public static void main(String[] args) {
        // GUI flow 1
        InputField strInput1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput1 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        InputField strInput2 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity strIntStrActivity = new Activity(Arrays.asList(strInput1, intInput1, strInput2), null);

        InputField emailInput = new InputField(null, Constants.INPUT_TYPE.EMAIL, true, Constants.VISIBILITY.VISIBLE);
        InputField passwordInput = new InputField(null, Constants.INPUT_TYPE.PASSWORD, true, Constants.VISIBILITY.VISIBLE);
        Activity loginActivity = new Activity(Arrays.asList(emailInput, passwordInput),
                                              Arrays.asList(strIntStrActivity));

        // GUI flow 2
        InputField strInput3 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity strActivity = new Activity(Arrays.asList(strInput3), null);

        InputField strInput4 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput2 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        Activity strIntActivity = new Activity(Arrays.asList(strInput4, intInput2),
                                               Arrays.asList(strActivity));

        List<Activity> startingActivities = Arrays.asList(loginActivity, strIntActivity);

        // Log elements for a user session
        LogElement element1 = new LogElement("abc@email.com", Constants.INPUT_TYPE.EMAIL);
        LogElement element2 = new LogElement("abc password", Constants.INPUT_TYPE.PASSWORD);
        LogElement element3 = new LogElement("string1", Constants.INPUT_TYPE.STRING);
        LogElement element4 = new LogElement("123", Constants.INPUT_TYPE.INTEGER);
        LogElement element5 = new LogElement("string2", Constants.INPUT_TYPE.STRING);
        List<LogElement> elements = Arrays.asList(element1, element2, element3, element4, element5);

    }

    public static void alignUserSessionToStartingActivity(List<LogElement> elements,
                                                          List<Activity> startingActivities) {
        for (Activity activity : startingActivities) {
            // Gather the input fields and for each input field in this GUI flow, match it with the log elements
        }
    }
}
