package edu.illinois.cs.alignment.graph;

import java.util.LinkedList;
import java.util.List;

final class Node<D extends Comparable<D>, L extends Comparable<L>> {
    private List<Edge<D, L>> outgoingEdges;
    private List<Edge<D, L>> incomingEdges;
    private final D data;

    // Abstraction Function:
    // Node, n, represents a node that belongs inside a graph ADT.
    // Each node has outgoingEdges, incomingEdges, and data. The outgoingEdges
    // is a list of all edges that are pointing away from the node. The
    // incomingEdges are also a list of edge pointing towards this node.
    //
    // Representation Invariant for every Node n:
    // outgoingEdges != null && incomingEdges != null && data != null
    // Also edges inside both list != null.

    /**
     * @param data The data of the node to be added into this.
     * @effects Constructs a new node with data as the data and has no edges.
     */
    Node(D data) {
        outgoingEdges = new LinkedList<Edge<D, L>>();
        incomingEdges = new LinkedList<Edge<D, L>>();
        this.data = data;
        assert checkRep();
    }

    /**
     * returns the data of the node.
     *
     * @returns the data of the node.
     */
    D getData() {
        return data;
    }

    /**
     * Returns a new linked list of edges pointing away from this.
     *
     * @returns a new linked list of edges pointing away from this.
     */
    List<Edge<D, L>> getOutgoingEdges() {
        assert checkRep();
        return new LinkedList<Edge<D, L>>(outgoingEdges);
    }

    /**
     * Returns a new linked list of edges pointing towards this.
     *
     * @returns a new linked list of edges pointing towards this.
     */
    List<Edge<D, L>> getIncomingEdges() {
        assert checkRep();
        return new LinkedList<Edge<D, L>>(incomingEdges);
    }

    /**
     * Adds an edge that is pointing away from this to another node. The node can be itself making the edge point to
     * itself.
     *
     * @param e The edge of the node where the edge being created starts from this.
     * @requires edges are non-null
     * @modifies this
     * @effects adds edge pointing from this to another node.
     */
    void addOutgoingEdge(Edge<D, L> e) {
        assert checkRep();
        outgoingEdges.add(e);
        assert checkRep();
    }

    /**
     * Adds an edge that is pointing to this from a different node. The node can be itself making the edge point to
     * itself.
     *
     * @param e The edge of the node where the edge being created points to this.
     * @requires edges are non-null
     * @modifies this
     * @effects adds edge pointing to this from another node.
     */
    void addIncomingEdge(Edge<D, L> e) {
        assert checkRep();
        incomingEdges.add(e);
        assert checkRep();
    }

    /**
     * Removes an edge that is pointing away from this and to a different node
     *
     * @param e The edge of the node where the edge being removed starts from this.
     * @requires e was an edge pointing away from this and e is non-null
     * @modifies this
     * @effects removes edge pointing from this to endNode with label as its label
     */
    void removeOutgoingEdge(Edge<D, L> e) {
        assert checkRep();
        outgoingEdges.remove(e);
        assert checkRep();
    }

    /**
     * Removes an edge that is pointing to this from a different node
     *
     * @param e The edge of the node where the edge being removed points to this.
     * @requires e was an edge pointing towards this and e is non-null
     * @modifies this
     * @effects removes edge pointing to this from startNode with label as its label
     */
    void removeIncomingEdge(Edge<D, L> e) {
        assert checkRep();
        incomingEdges.remove(e);
        assert checkRep();
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
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
        Node<D, L> other = (Node<D, L>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the representation invariant holds (if any).
     */
    // Throws a RuntimeException if the rep invariant is violated.
    private boolean checkRep() throws RuntimeException {
        if (outgoingEdges == null || incomingEdges == null || data == null) {
            throw new RuntimeException("Arguments passed to constructor can not be null.");
        }
        for (int i = 0; i < outgoingEdges.size(); i++) {
            if (outgoingEdges.get(i) == null) {
                throw new RuntimeException("Element " + i + " in outgoingEdge is null.");
            }
        }
        for (int i = 0; i < incomingEdges.size(); i++) {
            if (incomingEdges.get(i) == null) {
                throw new RuntimeException("Element " + i + " in outgoingEdge is null.");
            }
        }
        return true;
    }
}
