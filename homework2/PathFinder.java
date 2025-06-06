package homework2;

import java.util.*;

public class PathFinder {

    /**
     * Finds the shortest path from any start node to any goal node.
     *
     * @param g      the graph to search
     * @param starts the list of starting node names
     * @param goals  the list of goal node names
     * @return the shortest path as a WeightedNodePath, or null if no path found
     */
    public static WeightedNodePath findShortestPath(Graph g, List<String> starts, List<String> goals) {
        PriorityQueue<WeightedNodePath> active = new PriorityQueue<>();
        Set<String> finished = new HashSet<>();
        Map<String, WeightedNodePath> paths = new HashMap<>();

        // Initialize queue with all start nodes
        for (String start : starts) {
            int cost = g.getCost(start);
            WeightedNodePath initialPath = new WeightedNodePath(new WeightedNode(start, cost));
            active.add(initialPath);
            paths.put(start, initialPath);
        }

        while (!active.isEmpty()) {
            WeightedNodePath minElem = active.poll();
            String curNode = minElem.getEnd().getName();

            if (goals.contains(curNode)) {
                return minElem;
            }

            for (String child : g.listChildren(curNode)) {
                int cost = g.getCost(child);
                WeightedNodePath newPath = minElem.extend(new WeightedNode(child, cost));
                if (finished.contains(child) || active.contains(newPath)) {
                    continue;
                }
                paths.put(child, newPath);
                active.add(newPath);
            }

            finished.add(curNode);
        }

        // no path from starts to goals
        return null;
    }
}