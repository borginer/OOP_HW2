package homework2;

import org.junit.Test;
import java.util.Set;
import static org.junit.Assert.*;



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
        assertEquals(Set.of("A"), g.listNodes());
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
        assertEquals(Set.of("B"), children);
    }

    // test for checking if the list of nodes is sorted
    @Test
    public void testListNodesSorted() {
        Graph g = new Graph();
        g.addNode("C", 1);
        g.addNode("A", 2);
        g.addNode("B", 3);

        assertEquals(Set.of("A", "B", "C"), g.listNodes());  
    }

    //  test for trying to add duplicate node name
    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateNodeThrows() {
        Graph g = new Graph();
        g.addNode("X", 5);
        g.addNode("X", 10);  // should throw exeption
    }

    // test for getting an empty list of children
    @Test
    public void testListChildrenEmpty() {
        Graph g = new Graph();
        g.addNode("Solo", 1);
        assertTrue(g.listChildren("Solo").isEmpty());
    }

}
