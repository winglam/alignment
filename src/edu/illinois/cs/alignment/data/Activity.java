package edu.illinois.cs.alignment.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winglam on 10/30/15.
 */
public class Activity {
    private List<InputField> fields;
    private List<Activity> nextScreens;

    public Activity(List<InputField> fields, List<Activity> nextScreens) {
        this.fields = fields;
        this.nextScreens = nextScreens;
    }

    public List<InputField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<InputField> fields) {
        this.fields = fields;
    }

    public List<Activity> getNextScreens() {
        return nextScreens;
    }

    public void setNextScreens(ArrayList<Activity> nextScreens) {
        this.nextScreens = nextScreens;
    }
}
