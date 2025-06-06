import java.util.*;

/**
 * A directed graph with weighted nodes.
 * Nodes are identified by unique names (Strings)
 */
public class Graph {
    // Maps a node name to its cost
    private final Map<String, Integer> nodeMap;
    // Maps a node to the set of its children
    private final Map<String, TreeSet<String>> edgeMap;

    /**
     * Abstraction Function:
     *     A Graph is represented by a mapping of node names to Node objects,
     *     and a mapping from each node to its set of child nodes.
     *     nodeMap represents all nodes and their weights
     *     edgeMap represents directed edges between nodes
     *
     * Representation Invariant:
     *     nodeMap != null
     *     edgeMap != null
     *     all keys and values in edgeMap exist in nodeMap
     */
    private void checkRep() {
        assert nodeMap != null;
        assert edgeMap != null;
        for (String parent : edgeMap.keySet()) {
            assert nodeMap.containsKey(parent);
            for (String child : edgeMap.get(parent)) {
                assert nodeMap.containsKey(child);
            }
        }
    }

    /**
     * Creates a new empty graph.
     * @modifies this
     * @effects creates a new graph with no nodes or edges
     */
    public Graph() {
        this.nodeMap = new HashMap<>();
        this.edgeMap = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the graph with a given cost and a name.
     * @requires nodeName != null && cost >= 0 && nodeName not already in graph
     * @modifies this
     * @effects adds a new node with the given name and cost
     */
    public void addNode(String nodeName, int cost) {
        if (nodeName == null || cost < 0 || nodeMap.containsKey(nodeName)) {
            throw new IllegalArgumentException("Invalid node name, cost or duplicate node name");
        }
        nodeMap.put(nodeName, cost);
        edgeMap.put(nodeName, new TreeSet<>());
        checkRep();
    }

    /**
     * Adds a directed edge from parent to child.
     * @requires parent and child already exist in the graph
     * @modifies this
     * @effects adds an edge from parent to child
     */
    public void addEdge(String parent, String child) {
        if (!nodeMap.containsKey(parent) || !nodeMap.containsKey(child)) {
            throw new IllegalArgumentException("Parent or child node does not exist");
        }
        edgeMap.get(parent).add(child);
        checkRep();
    }

    /**
     * Returns the set of all node names in the graph sorted.
     * @effects returns the names of all nodes sorted
     */
    public Set<String> listNodes() {
        TreeSet<String> result = new TreeSet<>(nodeMap.keySet());
        return result;
    }

    /**
     * Returns an unmodifiable view of the children of the given node.
     * @requires node exists in the graph
     * @effects returns a set of the children of the node (unsorted and unmodifiable)
     */
    public Set<String> listChildren(String node) {
        if (!nodeMap.containsKey(node)) {
            throw new IllegalArgumentException("Node does not exist");
        }
        return Collections.unmodifiableSet(edgeMap.get(node));
    }


    /**
     * Returns the cost of a node.
     * @requires node exists in the graph
     * @effects returns the cost of the node
     */
    public int getCost(String node) {
        if (!nodeMap.containsKey(node)) {
            throw new IllegalArgumentException("Node does not exist");
        }
        return nodeMap.get(node);
    }
}
