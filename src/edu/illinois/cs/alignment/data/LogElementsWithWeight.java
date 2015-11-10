package edu.illinois.cs.alignment.data;

import java.util.List;

/**
 * Created by winglam on 11/9/15.
 */
public class LogElementsWithWeight implements Comparable<LogElementsWithWeight> {
    private Double weight;
    private List<LogElement> remainingElements;
    private List<LogElement> acceptedElements;
    private Double maxWeight;

    public LogElementsWithWeight() {
        this.weight = 0.0;
        this.remainingElements = null;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<LogElement> getRemainingElements() {
        return remainingElements;
    }

    public void setRemainingElements(List<LogElement> remainingElements) {
        this.remainingElements = remainingElements;
    }

    public List<LogElement> getAcceptedElements() {
        return acceptedElements;
    }

    public void setAcceptedElements(List<LogElement> acceptedElements) {
        this.acceptedElements = acceptedElements;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override public String toString() {
        return "LogElementsWithWeight{" +
               "weight=" + weight +
               ", remainingElements=" + remainingElements +
               ", acceptedElements=" + acceptedElements +
               ", maxWeight =" + maxWeight +
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
        if (!maxWeight.equals(that.maxWeight)) {
            return false;
        }
        return remainingElements.equals(that.remainingElements) && acceptedElements.equals(that.getAcceptedElements());

    }

    @Override public int hashCode() {
        int result = weight.hashCode();
        result = 31 * result + maxWeight.hashCode();
        result = 31 * result + remainingElements.hashCode();
        result = 31 * result + acceptedElements.hashCode();
        return result;
    }

    @Override public int compareTo(LogElementsWithWeight o) {
        return weight.compareTo(o.getWeight());
    }
}
