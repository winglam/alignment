package edu.illinois.cs.alignment.graph;


import edu.illinois.cs.alignment.data.LogElementsWithWeight;

/**
 * <b>SinglePath</b> represents a immutable jump between two nodes datas that are connected by an edge weight.
 * SinglePaths are an ADT that holds a startNode, endNode, and a weight connecting the two nodes. Node data can be of
 * any type D while labels will be of type double.
 */
public class SinglePath<D extends Comparable<D>> implements Comparable<SinglePath<D>> {
    private D startNode;
    private D endNode;
    private LogElementsWithWeight edgeLabel;

    // Abstraction Function:
    // SinglePath, s, represents a jump between two nodes that are connected by an edge weight with type Double.
    // Each SinglePath must have a startNode, endNode, and label where the startNode is the node the edge is
    // pointing away from, the endNode is the node the edge is pointing towards, and label is is the weight
    // of type Double associated between the nodes. startNode and endNode must be of the same type.
    //
    // Representation Invariant for every SinglePath :
    // startNode != null && endNode != null && edgeLabel != null

    /**
     * @param startNode The node's data that the single path being constructed starts at.
     * @param endNode   The node's data that the single path being constructed ends at.
     * @param edgeLabel The weight that connects the two nodes.
     * @effects Constructs a new single path that starts with startNode and ends with endNodes while the edgeLabel is
     * the weight cost between these two nodes.
     */
    public SinglePath(D startNode, D endNode, LogElementsWithWeight edgeLabel) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.edgeLabel = edgeLabel;
    }

    /**
     * returns the start node of this single path.
     *
     * @return the start node of this single path.
     */
    public D getStartNode() {
        return startNode;
    }

    /**
     * returns the end node of this single path.
     *
     * @return the end node of this single path.
     */
    public D getEndNode() {
        return endNode;
    }

    /**
     * returns the edge label (weight/cost) of this single path.
     *
     * @return the edge label (weight/cost) of this single path.
     */
    public LogElementsWithWeight getEdgeLabel() {
        return edgeLabel;
    }

    /**
     * basic compareTo method that allows the single paths to be compared with another single path. single paths are
     * only dependent on the edgeLabel (weight/cost) of the single path.
     *
     * @param arg The single path that is being compared to this.
     * @return a negative integer, zero, or a positive integer as this single path is less than, equal to, or greater
     * than the other single path arg.
     */
    @Override public int compareTo(SinglePath<D> arg) {
        return this.edgeLabel.compareTo(arg.edgeLabel);
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((edgeLabel == null) ? 0 : edgeLabel.hashCode());
        result = prime * result + ((endNode == null) ? 0 : endNode.hashCode());
        result = prime * result + ((startNode == null) ? 0 : startNode.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked") @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SinglePath<D> other = (SinglePath<D>) obj;
        if (edgeLabel == null) {
            if (other.edgeLabel != null) {
                return false;
            }
        } else if (!edgeLabel.equals(other.edgeLabel)) {
            return false;
        }
        if (endNode == null) {
            if (other.endNode != null) {
                return false;
            }
        } else if (!endNode.equals(other.endNode)) {
            return false;
        }
        if (startNode == null) {
            if (other.startNode != null) {
                return false;
            }
        } else if (!startNode.equals(other.startNode)) {
            return false;
        }
        return true;
    }

    @Override public String toString() {
        return "StartNode=" + startNode +
               "\nEndNode=" + endNode +
               "\nEdgeLabelWeight=" + edgeLabel.getWeight();
    }
}
