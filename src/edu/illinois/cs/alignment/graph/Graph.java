package edu.illinois.cs.alignment.graph;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <b>Graph</b> represents a mutable directed and labeled graph. Graphs are an ADT that holds nodes and edges where
 * edges connect nodes by pointing from one node to another node. Each edge has a label and each node has data. Data and
 * labels can be of any type called D and L respectively. While there can only be one node with a certain data, there
 * can be many edges with the same label. Edges that connect the same two nodes with the same label are also allowed.
 */
public class Graph<D extends Comparable<D>, L extends Comparable<L>> {

    private Hashtable<D, Node<D, L>> nodes;

    // Abstraction Function:
    // Graph, g, represents a graph ADT. Each graph has a nodes hashtable
    // that maps the data of a node with the actual node itself.
    //
    // Representation Invariant for every Graph g:
    // nodes != null and the strings and nodes inside the table are non-null also.
    // every edge connects nodes that are part of the same graph.

    /**
     * @effects Constructs a new empty Graph.
     */
    public Graph() {
        nodes = new Hashtable<D, Node<D, L>>();
        assert checkRep();
    }

    /**
     * Adds a node associated with the data being passed in.
     *
     * @param data The data of the node to be added into 'this'.
     * @throws RuntimeException when data being passed in is already a node.
     * @requires data is non-null and data is not a data of a node already in the list
     * @modifies 'this'
     * @effects adds node with data into this graph.
     */
    public void addNode(D data) {
        assert checkRep();
        if (nodes.containsKey(data)) {
            throw new RuntimeException("data passed in is data of a node already inside the graph.");
        }
        nodes.put(data, new Node<D, L>(data));
        assert checkRep();
    }

    /**
     * Adds an edge from startNode to endNode associated with the label being passed in.
     *
     * @param startNode The data of the node where the edge being created starts from.
     * @param endNode   The data of the node where the edge being created points to.
     * @param label     The label of the edge that is being added into this.
     * @requires startNode and endNode are datas of nodes inside this. All three params are non-null
     * @modifies 'this'
     * @effects adds edge pointing from startNode to endNode with label as its label into this graph.
     */
    public void addEdge(D startNode, D endNode, L label) {
        assert checkRep();
        Node<D, L> start = nodes.get(startNode);
        Node<D, L> end = nodes.get(endNode);
        Edge<D, L> e = new Edge<D, L>(start, end, label);
        start.addOutgoingEdge(e);
        end.addIncomingEdge(e);
        assert checkRep();
    }

    /**
     * @modifies 'this'
     * @effects clears the Graph completely of any nodes and edges.
     */
    public void clear() {
        nodes.clear();
    }

    /**
     * Removes the node associated with the data being passed in, returning true of the remove was successful and false
     * if not (data passed in was not a node).
     *
     * @param data The data of the node to be removed from this.
     * @requires data is non-null
     * @modifies this
     * @returns whether or not the remove was successful.
     * @effects removes node with data from the graph
     */
    public boolean removeNode(D data) {
        assert checkRep();
        if (!nodes.containsKey(data)) {
            return false;
        }
        Node<D, L> n = nodes.get(data);
        List<Edge<D, L>> out = n.getOutgoingEdges();
        List<Edge<D, L>> in = n.getIncomingEdges();
        for (Edge<D, L> e : out) {
            e.getEndNode().removeIncomingEdge(e);
        }
        for (Edge<D, L> e : in) {
            e.getStartNode().removeOutgoingEdge(e);
        }
        nodes.remove(data);
        assert checkRep();
        return true;
    }

    /**
     * Removes an edge from startNode to endNode associated with the label being passed in, returning true of the remove
     * was successful and false if not (data passed in was not a node).
     *
     * @param startNode The data of the node where the edge being removed starts from.
     * @param endNode   The data of the node where the edge being removed points to.
     * @param label     The label of the edge that is being removed from 'this'.
     * @requires all three params (startNode, endNode, and label) are non-null
     * @modifies this
     * @returns whether or not the remove was successful.
     * @effects removes edge pointing from startNode to endNode with label as its label into the graph. If there are
     * multiple duplicates of this edge, remove only one.
     */
    public boolean removeEdge(D startNode, D endNode, L label) {
        assert checkRep();
        if (!nodes.containsKey(startNode) || !nodes.containsKey(endNode)) {
            return false;
        }
        Node<D, L> start = nodes.get(startNode);
        Node<D, L> end = nodes.get(endNode);
        for (Edge<D, L> e : start.getOutgoingEdges()) {
            if (e.getLabel().equals(label) && e.getEndNode().getData().equals(endNode)) {
                start.removeOutgoingEdge(e);
                end.removeIncomingEdge(e);
                assert checkRep();
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if the data being passed in is the data of any nodes in this
     *
     * @param data The string being compared to see if this contains a node with the same data.
     * @return whether or not a node with data is in this.
     * @requires data is non-null.
     */
    public boolean isNode(D data) {
        assert checkRep();
        return nodes.containsKey(data);
    }

    /**
     * Checks to see if startNode has an edge pointing to endNode.
     *
     * @param startNode The data of the node being checked to see if it is pointing to endNode
     * @param endNode   The data of the node being checked to see if it is pointed at by startNode
     * @return whether or not an edge pointing from StartNode to endNode exist.
     * @requires Both params (startNode and endNode) are non-null.
     */
    public boolean isConnected(D startNode, D endNode) {
        if (!nodes.containsKey(startNode) || !nodes.containsKey(endNode)) {
            return false;
        }
        for (Edge<D, L> e : nodes.get(startNode).getOutgoingEdges()) {
            if (e.getEndNode().equals(nodes.get(endNode))) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the total number of nodes in the graph.
     *
     * @return number of nodes in this.
     */
    public int numberOfNodes() {
        return nodes.size();
    }

    /**
     * returns the total number of edges in the graph.
     *
     * @return the number of edges in this. Edges that are exactly alike still count as multiple edges.
     */
    public int numberOfEdges() {
        int total = 0;
        for (Node<D, L> n : nodes.values()) {
            total += n.getOutgoingEdges().size();
        }
        return total;
    }

    /**
     * returns a new linked list of all the nodes in the graph's data in alphabetical order.
     *
     * @return a new linked list of the graph's nodes's data in alphabetical order.
     */
    public List<D> getNodeDatas() {
        List<D> list = new LinkedList<D>(nodes.keySet());
        Collections.sort(list);
        return list;
    }

    /**
     * returns a new linked list of all the edges in the graph's labels in alphabetical order.
     *
     * @return a new linked list of the graph's edge's label in alphabetical order. Duplicate labels can be in there
     * multiple times.
     */
    public List<L> getEdgeLabels() {
        List<L> list = new LinkedList<L>();
        for (Node<D, L> n : nodes.values()) {
            for (Edge<D, L> e : n.getOutgoingEdges()) {
                list.add(e.getLabel());
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Returns a new linked list of all the edge's labels from startNode to Endnode in alphabetical order.
     *
     * @param startNode The data of the node the edges are pointing away from
     * @param endNode   The data of the node the edges are point towards.
     * @return a new linked list of all the edge's labels from startNode to Endnode in alphabetical order. If there are
     * multiple edges with the same label then multiple labels will be returned.
     * @requires startNode and endNode are datas of nodes inside this. Both params are non-null
     */
    public List<L> getEdgeLabel(D startNode, D endNode) {
        assert checkRep();
        Node<D, L> start = nodes.get(startNode);
        Node<D, L> end = nodes.get(endNode);
        List<L> list = new LinkedList<L>();
        for (Edge<D, L> e : start.getOutgoingEdges()) {
            if (e.getEndNode().equals(end)) {
                list.add(e.getLabel());
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Returns new map of all the different nodes' data as the key and a list of the edge labels as the value that an
     * edge is pointing to, from startNode  in alphabetical order.
     *
     * @param startNode The data of the node the edges are pointing away from
     * @return new map of all the different nodes' data as the key and a list of the edge labels as the value that an
     * edge is pointing to, from startNode  in alphabetical order.
     * @requires startNode is the data of one of the nodes inside this and it is non-null
     */
    public Map<D, List<L>> getOutgoingNodeDatas(D startNode) {
        Map<D, List<L>> map = new TreeMap<D, List<L>>();
        for (Edge<D, L> e : nodes.get(startNode).getOutgoingEdges()) {
            D node = e.getEndNode().getData();
            List<L> list;
            if (map.containsKey(node)) {
                list = map.get(node);
            } else {
                list = new LinkedList<L>();
            }
            list.add(e.getLabel());
            Collections.sort(list);
            map.put(node, list);
        }
        return map;
    }

    /**
     * Returns a new map of all the different nodes' data as the key and a list of all edge labels as the value that has
     * an edge pointing to endNode in alphabetical order.
     *
     * @param endNode The data of the node the edges of different nodes are pointing to.
     * @return a new map of all the different nodes' data as the key and a list of all edge labels as the value that has
     * an edge pointing to endNode in alphabetical order.
     * @requires endNode is the data of one of the nodes inside this and it is non-null
     */
    public Map<D, List<L>> getIncomingNodeDatas(D endNode) {
        Map<D, List<L>> map = new TreeMap<D, List<L>>();
        for (Edge<D, L> e : nodes.get(endNode).getIncomingEdges()) {
            D node = e.getStartNode().getData();
            List<L> list;
            if (map.containsKey(node)) {
                list = map.get(node);
            } else {
                list = new LinkedList<L>();
            }
            list.add(e.getLabel());
            Collections.sort(list);
            map.put(node, list);
        }
        return map;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
        Graph<D, L> other = (Graph<D, L>) obj;
        if (nodes == null) {
            if (other.nodes != null) {
                return false;
            }
        } else if (!nodes.equals(other.nodes)) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the representation invariant holds (if any).
     *
     * @return
     */
    // Throws a RuntimeException if the rep invariant is violated.
    private boolean checkRep() throws RuntimeException {
        if (nodes == null) {
            throw new RuntimeException("Arguments passed in can not be null.");
        }
        List<D> datas = new LinkedList<D>(nodes.keySet());
        List<Node<D, L>> node = new LinkedList<Node<D, L>>(nodes.values());
        for (int i = 0; i < datas.size(); i++) {
        }
        for (int i = 0; i < node.size(); i++) {
            if (node.get(i) == null) {
                throw new RuntimeException("Element " + i + " in values of nodes is null.");
            }
        }
        return true;
    }
}
