package edu.illinois.cs.alignment.data;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.alignment.util.Constants;

/**
 * Created by winglam on 10/29/15.
 */
public class InputField {
    private List<String> labels;
    private Constants.INPUT_TYPE input_type;
    private boolean isMandatory;
    private Constants.VISIBILITY visibility;

    public InputField(List<String> labels, Constants.INPUT_TYPE input_type, boolean isMandatory,
                      Constants.VISIBILITY visibility) {
        this.labels = labels;
        this.input_type = input_type;
        this.isMandatory = isMandatory;
        this.visibility = visibility;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public Constants.INPUT_TYPE getInput_type() {
        return input_type;
    }

    public void setInput_type(Constants.INPUT_TYPE input_type) {
        this.input_type = input_type;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Constants.VISIBILITY getVisibility() {
        return visibility;
    }

    public void setVisibility(Constants.VISIBILITY visibility) {
        this.visibility = visibility;
    }
}
