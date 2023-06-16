package model.diagram.exportation;

import model.diagram.graphml.GraphMLVertex;
import model.diagram.graphml.GraphMLVertexArc;
import model.graph.Arc;
import model.graph.Vertex;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class GraphMLPackageDiagramExporter implements DiagramExporter {

    private final GraphMLFile graphMLFile;
    private final StringBuilder graphMLNodeBuffer;
    private final StringBuilder graphMLEdgeBuffer;

    public GraphMLPackageDiagramExporter(Map<Vertex, Integer> graphNodes, Map<Integer, Pair<Double, Double>> nodesGeometry,
                                         Map<Vertex, Set<Arc<Vertex>>> diagram) {
        GraphMLVertex graphMLVertex = new GraphMLVertex(graphNodes, nodesGeometry);
        graphMLNodeBuffer = graphMLVertex.convertVertex();
        GraphMLVertexArc graphMLVertexArc = new GraphMLVertexArc(graphNodes, diagram);
        graphMLEdgeBuffer = graphMLVertexArc.convertVertexArc();
        graphMLFile = new GraphMLFile();
    }

    @Override
    public File exportDiagram(Path exportPath) {
        try {
            graphMLFile.createGraphMLFile(exportPath);
            generateGraphMLGraph(graphMLNodeBuffer, graphMLEdgeBuffer);
            graphMLFile.closeGraphMLFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        return graphMLFile.getGraphMLFile();
    }

    private void generateGraphMLGraph(StringBuilder nodeBuffer, StringBuilder edgeBuffer){
        graphMLFile.writeToBuffer(nodeBuffer);
        graphMLFile.writeToBuffer(edgeBuffer);
    }
}