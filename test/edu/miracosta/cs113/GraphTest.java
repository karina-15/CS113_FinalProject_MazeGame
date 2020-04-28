package edu.miracosta.cs113;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class GraphTest {
    private MatrixGraph graph;

    private void setUp() {
        graph = new MatrixGraph(5, true);
        graph.insert(new Edge(0, 1, 10));
        graph.insert(new Edge(0, 3, 30));
        graph.insert(new Edge(0, 4, 100));
        graph.insert(new Edge(1, 2, 50));
        graph.insert(new Edge(2, 4, 10));
        graph.insert(new Edge(3, 2, 20));
        graph.insert(new Edge(3, 4, 60));
    }

    @Test
    public void testInsertDirected() {
        graph = new MatrixGraph(3, true);
        Edge expected = new Edge(2, 2, 120);
        graph.insert(expected);
        Edge actual = graph.getEdge(2, 2);
        assertEquals("Expected edge does not equal actual edge", actual, expected);
    }

    @Test
    public void testInsertUndirected() {
        graph = new MatrixGraph(5, false);
        Edge original = new Edge(2, 4, 120);
        graph.insert(original);
        Edge actual = graph.getEdge(4, 2);
        Edge expected = new Edge(4, 2, 120);
        assertEquals("Expected edge does not match actual edge", actual, expected);
    }

    @Test
    public void testDijkstras() {
        setUp();
        int[] pred = new int[5];
        double[] dist = new double[5];
        graph.dijkstrasAlgorithm(0, pred, dist);
        double[] expectedDistanceValues = {0, 10.0, 50.0, 30.0, 60.0};
        int[] expectedPredecesors = {0, 0, 3, 0, 2};
        for (int i = 1; i < pred.length; i++) {
            double actual = dist[i];
            double expected = expectedDistanceValues[i];
            assertTrue(actual == expected);
            int actualPred = pred[i];
            int expectedPred = expectedPredecesors[i];
            assertEquals("Predecessors do not match", expectedPred, actualPred);
        }
    }

    @Test
    public void testGetPath() {
        setUp();
        int[] actual = graph.getPath(0, 4);
        int[] expected = {3, 2, 4};

        for(int i = 1; i < expected.length; i++) {
            int actualPath = actual[i];
            int expectedPath = expected[i];

            assertEquals("Predecessors do not match", expectedPath, actualPath);
        }
    }

    @Test
    public void testIteratorNext() {
        setUp();
        Iterator itr = graph.edgeIterator(0);
        Edge[] expected = {new Edge(0, 1, 10),
                            new Edge(0, 3, 30),
                            new Edge(0, 4, 40)};
        int counter = 0;

        while (itr.hasNext()) {
            Edge actual = (Edge)itr.next();
            assertEquals(actual, expected[counter]);
            counter++;
        }
    }

    @Test
    public void testHasNextFalse() {
        setUp();
        Iterator itr = graph.edgeIterator(4);

        // no adjacencies
        assertFalse(itr.hasNext());
    }

    @Test
    public void testHasNextTrue() {
        setUp();
        Iterator itr = graph.edgeIterator(2);

        // 1 adjacency
        assertTrue(itr.hasNext());
        itr.next();
        assertFalse(itr.hasNext());
    }
}
