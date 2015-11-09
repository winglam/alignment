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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        result = prime * result + ((input_type == null) ? 0 : input_type.hashCode());
        result = prime * result + (!isMandatory ? 0 : 1);
        result = prime * result + ((visibility == null) ? 0 : visibility.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InputField other = (InputField) obj;
        if (labels == null) {
            if (other.labels != null)
                return false;
        } else if (!labels.equals(other.labels))
            return false;
        if (input_type == null) {
            if (other.input_type != null)
                return false;
        } else if (!input_type.equals(other.input_type))
            return false;
        if (isMandatory != other.isMandatory)
            return false;
        if (visibility == null) {
            if (other.visibility != null)
                return false;
        } else if (!visibility.equals(other.visibility))
            return false;
        return true;
    }

    @Override public String toString() {
        return "InputField{" +
               "input_type=" + input_type +
               '}';
    }
}
