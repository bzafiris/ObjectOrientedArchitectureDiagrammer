package model.diagram.exportation;

import model.diagram.graphml.GraphMLSinkVertex;
import model.diagram.graphml.GraphMLSinkVertexArc;
import model.graph.Arc;
import model.graph.SinkVertex;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class GraphMLClassDiagramExporter implements DiagramExporter {

    private final GraphMLFile graphMLFile;
    private final StringBuilder graphMLNodeBuffer;
    private final StringBuilder graphMLEdgeBuffer;

    public GraphMLClassDiagramExporter(Map<SinkVertex, Integer> graphNodes, Map<Integer, Pair<Double, Double>> nodesGeometry,
                                       Map<SinkVertex, Set<Arc<SinkVertex>>> diagram) {
        GraphMLSinkVertex graphMLSinkVertex = new GraphMLSinkVertex(graphNodes, nodesGeometry);
        this.graphMLNodeBuffer = graphMLSinkVertex.convertSinkVertex();
        GraphMLSinkVertexArc graphMLSinkVertexArc = new GraphMLSinkVertexArc(graphNodes, diagram);
        this.graphMLEdgeBuffer = graphMLSinkVertexArc.convertSinkVertexArc();
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