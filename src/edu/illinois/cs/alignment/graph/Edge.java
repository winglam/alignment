package edu.illinois.cs.alignment.graph;

final class Edge<D extends Comparable<D>, L extends Comparable<L>> {
    private final Node<D, L> startNode;
    private final Node<D, L> endNode;
    private final L label;

    // Abstraction Function:
    // Edge, e, represents an edge that belongs inside a graph ADT.
    // Each edge must have a startNode, endNode, and label where the
    // startNode is the node the edge is pointing away from, the endNode
    // is the node the edge is pointing towards, and label is is the
    // string associated with the edge.
    //
    // Representation Invariant for every Edge e:
    // startNode != null && endNode != null && label != null

    /**
     * @param startNode The node that the edge being constructed starts at.
     * @param endNode   The node that the edge being constructed ends at.
     * @param label     The label that is associated with the edge being constructed.
     * @effects Constructs a new edge with label as the label and startNode and endNode as the edge that it is
     * connecting.
     */
    Edge(Node<D, L> startNode, Node<D, L> endNode, L label) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.label = label;
        assert checkRep();
    }

    /**
     * returns the start node of this edge.
     *
     * @returns the start node of this edge.
     */
    Node<D, L> getStartNode() {
        return startNode;
    }

    /**
     * returns the end node of this edge.
     *
     * @returns the end node of this edge.
     */
    Node<D, L> getEndNode() {
        return endNode;
    }

    /**
     * returns the label of this edge.
     *
     * @returns the label of this edge.
     */
    L getLabel() {
        return label;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        Edge<D, L> other = (Edge<D, L>) obj;
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label)) {
            return false;
        }
        if (startNode == null) {
            if (other.startNode != null) {
                return false;
            }
        } else if (!startNode.equals(other.startNode)) {
            return false;
        }
        if (endNode == null) {
            if (other.endNode != null) {
                return false;
            }
        } else if (!endNode.equals(other.endNode)) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the representation invariant holds (if any).
     */
    // Throws a RuntimeException if the rep invariant is violated.
    private boolean checkRep() throws RuntimeException {
        if (startNode == null || endNode == null || label == null) {
            throw new RuntimeException("Arguments passed in can not be null.");
        }
        return true;
    }
}
