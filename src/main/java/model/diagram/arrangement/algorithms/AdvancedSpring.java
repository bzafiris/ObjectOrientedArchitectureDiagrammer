package model.diagram.arrangement.algorithms;

import edu.uci.ics.jung.graph.Graph;
import model.diagram.arrangement.geometry.DiagramGeometry;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public class AdvancedSpring implements LayoutAlgorithm{

	private Graph<String, String> graph;
	
	public AdvancedSpring() {
    }
	
	@Override
	public void setGraph(Graph<String, String> graph) {
		this.graph = graph;
	}
	
	@Override
	public DiagramGeometry arrangeDiagram() {
		DiagramGeometry diagramGeometry = new DiagramGeometry();
		AbstractLayout<String, String> layout = new SpringLayout2<>(graph);
        layout.setSize(new Dimension(1200, 725));
        for (String vertex : graph.getVertices()) {
            Point2D coordinates = layout.apply(vertex);
            double x = coordinates.getX();
            double y = coordinates.getY();            
        	if (x < 25) {
        		x = 25;
        	}
        	if (y < 25) {
        		y = 25;
        	}
            diagramGeometry.addGeometry(vertex, x, y);        }
		return diagramGeometry;
	}
}
