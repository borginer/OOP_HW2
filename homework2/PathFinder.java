package homework2;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PathFinder {

    /**
     * Finds the shortest path from any node in starts to any goal node.
     *
     * @param g              the graph to search
     * @param starts  a list of paths, each containing a single starting node
     * @param goals          the set of goal node names
     * @return the shortest path, or null if no path found (can also be no starts/goals)
     */
    public static <N, P extends Path<N, P> & Comparable<Path<?, ?>>>
    P findShortestPath(Graph g,
                       List<P> starts,
                       Set<N> goals,
                       BiFunction<String, Integer, N> nodeFactory,
                       Function<N, String> nameExtractor) {
        PriorityQueue<P> active = new PriorityQueue<>();
        Set<N> finished = new HashSet<>();

        // Initialize queue with all start paths
        for (P path : starts) {
            N startNode = path.getEnd();
            active.add(path);
        }

        while (!active.isEmpty()) {
            P minPath = active.poll();
            N curNode = minPath.getEnd();

            if (goals.contains(curNode)) {
                return minPath;
            }
            for (String childStr : g.listChildren(nameExtractor.apply(curNode))) {
                int cost = g.getCost(childStr);
                N child = nodeFactory.apply(childStr, cost);

                P newPath = minPath.extend(child);

                if (finished.contains(child) || active.contains(newPath)) {
                    continue;
                }

                active.add(newPath);
            }

            finished.add(curNode);
        }

        return null; // no path found
    }
}