package edu.illinois.cs.alignment.data;

import edu.illinois.cs.alignment.util.Constants.INPUT_TYPE;

/**
 * Created by winglam on 10/29/15.
 */
public class LogElement {
    private int length;
    private String content;
    private INPUT_TYPE input_type;

    public LogElement(String content, INPUT_TYPE input_type) {
        this.content = content;
        this.length = content.length();
        this.input_type = input_type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public INPUT_TYPE getInput_type() {
        return input_type;
    }

    public void setInput_type(INPUT_TYPE input_type) {
        this.input_type = input_type;
    }

    @Override public String toString() {
        return "LogElement{" +
               "length=" + length +
               ", content='" + content + '\'' +
               ", input_type=" + input_type +
               '}';
    }
}
