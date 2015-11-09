package edu.illinois.cs.alignment.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Path</b> represents a path between two nodes datas that are connected by different edge weights and other Nodes.
 * Paths are an ADT that holds a list of SinglePaths where the SinglePath must also be of type D. The path contains a
 * list of single paths where the first single path's startNode and the last single path's endNode is the start and end
 * to the path itself. The path also keeps track of the total cost of the whole path way by adding up all the edgeLabel
 * (weight/cost) of each of the single path.
 */
public class Path<D extends Comparable<D>> implements Comparable<Path<D>> {
    private List<SinglePath<D>> path;
    private Double totalWeight;
    private boolean sortWithWeight;

    // Abstraction Function:
    // Path, p, represents a path between two nodes that are connected by edge weights with type Double and other nodes.
    // Each Path is represented by a list of SinglePath and a Double that is the totalWeight/cost of the pathway.
    //
    // Representation Invariant for every SinglePath :
    // path != null && totalWeight != null && every SinglePath inside path is also != null (path.get(i) != null)

    /**
     * @effects Constructs a new empty Path with the total weight starting at 0.0.
     */
    public Path() {
        path = new ArrayList<SinglePath<D>>();
        totalWeight = 0.0;
        sortWithWeight = false;
    }

    public Path(SinglePath<D> singlePath) {
        this();
        add(singlePath);
    }

    /**
     * @param previousPath The path to be constructed into 'this'.
     * @requires previous path passed in is of the same type that this path, previous path is not null and none of the
     * single paths inside the previous path are null either.
     * @effects Constructs a path containing the SinglePaths of the previous path, in the same order they were in and
     * makes the total weight equal to the total weight of the previous path.
     */
    public Path(Path<D> previousPath) {
        path = new ArrayList<SinglePath<D>>(previousPath.path);
        totalWeight = previousPath.totalWeight;
        sortWithWeight = false;
    }

    /**
     * @param singlePath The singlePath to be added into 'this' path.
     * @requires the single path are not null and none of the nodes or edge label are null either.
     * @effects addes the single path to the end of 'this path so that the first single path's startNode is the start of
     * the path and the single path being passed in's endNode is the current end of the path.
     */
    public void add(SinglePath<D> singlePath) {
        totalWeight += singlePath.getEdgeLabel().getWeight();
        path.add(singlePath);
    }

    /**
     * Returns the number of SinglePaths in 'this' path.
     *
     * @return the number of SinglePaths in 'this' path.
     */
    public int size() {
        return path.size();
    }

    /**
     * Returns the single path at the index being passed in.
     *
     * @param index the index of the single path trying to be gotten.
     * @return the single path at the index being passed in. (The first single path has index = 0)
     */
    public SinglePath<D> get(int index) {
        return path.get(index);
    }

    /**
     * Returns the the total weight of 'this' path.
     *
     * @return the the total weight of 'this' path.
     */
    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setSortWithWeight(boolean sortWithWeight) {
        this.sortWithWeight = sortWithWeight;
    }

    /**
     * basic compareTo method that allows the paths to be compared with another path. single paths are only dependent on
     * the totalWeight (cost) of the path.
     *
     * @param arg The path that is being compared to 'this'.
     * @return a negative integer, zero, or a positive integer as this path is less than, equal to, or greater than the
     * other path arg.
     */
    @Override public int compareTo(Path<D> arg) {
        if (sortWithWeight) {
            return arg.getTotalWeight().compareTo(totalWeight);
        } else {
            if (path.size() > arg.path.size()) {
                return 1;
            } else if (path.size() < arg.path.size()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
