package model.diagram.arrangement.algorithms;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

public class KamadaKawai implements LayoutAlgorithm{

	private Graph<String, String> graph;
	
	public KamadaKawai(Graph<String, String> graph) {
        this.graph = graph;
    }
	@Override
	public Map<String, Pair<Double, Double>> arrangeDiagram() {
		Map<String, Pair<Double, Double>> nodesGeometry = new HashMap<>();
		AbstractLayout<String, String> layout = new KKLayout<>(graph);
        layout.setSize(new Dimension(900, 800));
        for (String vertex : graph.getVertices()) {
            Point2D coordinates = layout.apply(vertex);
            nodesGeometry.put(vertex, new Pair<>(coordinates.getX(), coordinates.getY()));
            double x = coordinates.getX();
            double y = coordinates.getY();
            System.out.println("Vertex: " + vertex + ", Coordinates: (" + x + ", " + y + ")");
        }

		return nodesGeometry;
	}

}
