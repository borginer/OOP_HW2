package homework2;

import org.junit.Test;
import tests.ScriptFileTests;

import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Arrays;




/**
 * This class contains a set of test cases that can be used to test the graph
 * and shortest path finding algorithm implementations of homework assignment
 * #2.
 */
public class GraphTests extends ScriptFileTests {

	// black-box test are inherited from super
	public GraphTests(java.nio.file.Path testFile) {
		super(testFile);
	}

	// TODO: add additional white box tests

	// basic test for adding a node
    @Test
    public void testAddSingleNode() {
        Graph g = new Graph();
        g.addNode("A", 5);
        assertEquals(new HashSet<>(Arrays.asList("A")), g.listNodes());
        assertEquals(5, g.getCost("A"));
    }

    // basic test for adding an edge between two nodes
    @Test
    public void testAddEdgeAndChildren() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addEdge("A", "B");

        Set<String> children = g.listChildren("A");
        assertEquals(new HashSet<>(Arrays.asList("B")), children);
    }

    // test for checking if the list of nodes is sorted
    @Test
    public void testListNodesSorted() {
        Graph g = new Graph();
        g.addNode("C", 1);
        g.addNode("A", 2);
        g.addNode("B", 3);

        assertEquals(new HashSet<>(Arrays.asList("A", "B", "C")), g.listNodes());
    }

    //  test for trying to add duplicate node name
    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateNodeThrows() {
        Graph g = new Graph();
        g.addNode("X", 5);
        g.addNode("X", 10);  // should throw exception
    }

    // test for getting an empty list of children
    @Test
    public void testListChildrenEmpty() {
        Graph g = new Graph();
        g.addNode("Solo", 1);
        assertTrue(g.listChildren("Solo").isEmpty());
    }

    @Test
    public void testShortestPathDirectConnection() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addEdge("A", "B");

        WeightedNode start = new WeightedNode("A", g.getCost("A"));
        WeightedNodePath startPath = new WeightedNodePath(start);
        WeightedNode goal = new WeightedNode("B", g.getCost("B"));

        WeightedNodePath path = PathFinder.findShortestPath(
                g,
                List.of(startPath),
                Set.of(goal),
                WeightedNode::new,
                WeightedNode::getName
        );

        assertNotNull(path);
        assertEquals(3.0, path.getCost(), 0.001);
        assertEquals("B", path.getEnd().getName());
    }

    @Test
    public void testEmptyStarts() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addEdge("A", "B");

        WeightedNode goal = new WeightedNode("B", g.getCost("B"));

        WeightedNodePath path = PathFinder.findShortestPath(
                g,
                List.<WeightedNodePath>of(), // empty starts
                Set.of(goal),
                WeightedNode::new,
                WeightedNode::getName
        );

        assertNull(path);
    }

    @Test
    public void testEmptyGoals() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addEdge("A", "B");

        WeightedNode start = new WeightedNode("A", g.getCost("A"));
        WeightedNodePath startPath = new WeightedNodePath(start);

        WeightedNodePath path = PathFinder.findShortestPath(
                g,
                List.of(startPath),
                Set.of(), // empty goals
                WeightedNode::new,
                WeightedNode::getName
        );

        assertNull(path);
    }

    @Test
    public void testShortestPathViaIntermediate() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addNode("C", 3);
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        WeightedNode start = new WeightedNode("A", g.getCost("A"));
        WeightedNodePath startPath = new WeightedNodePath(start);
        WeightedNode goal = new WeightedNode("C", g.getCost("C"));

        WeightedNodePath path = PathFinder.findShortestPath(
                g,
                List.of(startPath),
                Set.of(goal),
                WeightedNode::new,
                WeightedNode::getName
        );

        assertNotNull(path);
        assertEquals(6.0, path.getCost(), 0.001);
        assertEquals("C", path.getEnd().getName());
    }

    @Test
    public void testNoPathToGoal() {
        Graph g = new Graph();
        g.addNode("A", 1);
        g.addNode("B", 2);
        g.addNode("C", 3);
        g.addEdge("A", "B"); // no connection to C

        WeightedNode start = new WeightedNode("A", g.getCost("A"));
        WeightedNodePath startPath = new WeightedNodePath(start);
        WeightedNode goal = new WeightedNode("C", g.getCost("C"));

        WeightedNodePath path = PathFinder.findShortestPath(
                g,
                List.of(startPath),
                Set.of(goal),
                WeightedNode::new,
                WeightedNode::getName
        );

        assertNull(path);
    }
}
