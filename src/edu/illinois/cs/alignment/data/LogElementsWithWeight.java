package edu.illinois.cs.alignment.data;

import java.util.List;

/**
 * Created by winglam on 11/9/15.
 */
public class LogElementsWithWeight implements Comparable<LogElementsWithWeight> {
    private Double weight;
    private List<LogElement> elements;

    public LogElementsWithWeight(Double weight, List<LogElement> elements) {
        this.weight = weight;
        this.elements = elements;
    }

    public LogElementsWithWeight() {
        this.weight = 0.0;
        this.elements = null;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<LogElement> getElements() {
        return elements;
    }

    public void setElements(List<LogElement> elements) {
        this.elements = elements;
    }

    @Override public String toString() {
        return "LogElementsWithWeight{" +
               "weight=" + weight +
               ", elements=" + elements +
               '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogElementsWithWeight that = (LogElementsWithWeight) o;

        if (!weight.equals(that.weight)) {
            return false;
        }
        return elements.equals(that.elements);

    }

    @Override public int hashCode() {
        int result = weight.hashCode();
        result = 31 * result + elements.hashCode();
        return result;
    }

    @Override public int compareTo(LogElementsWithWeight o) {
        return weight.compareTo(o.getWeight());
    }
}
