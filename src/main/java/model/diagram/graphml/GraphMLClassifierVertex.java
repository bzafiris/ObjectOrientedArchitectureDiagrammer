package model.diagram.graphml;

import model.diagram.ClassDiagram;
import model.graph.ClassifierVertex;
import model.graph.VertexType;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphMLClassifierVertex
{

    private static final String CLASS_COLOR     = "#FF9900";
    private static final String INTERFACE_COLOR = "#3366FF";

    private final StringBuilder graphMLBuffer;
    private final ClassDiagram  classDiagram;


    public GraphMLClassifierVertex(ClassDiagram classDiagram)
    {
        this.classDiagram = classDiagram;
        graphMLBuffer     = new StringBuilder();
    }


    public StringBuilder convertSinkVertex()
    {
        for (Map.Entry<ClassifierVertex, Integer> sinkVertex : classDiagram.getGraphNodes().entrySet())
        {
            graphMLBuffer.append(GraphMLSyntax.getInstance()
                                     .getGraphMLSinkVertexSyntax(getSinkVertexDescription(sinkVertex.getKey(),
                                                                                          sinkVertex.getValue(),
                                                                                          classDiagram.getGraphMLDiagramGeometry().get(sinkVertex.getValue()))));
        }
        return graphMLBuffer;
    }


    private List<String> getSinkVertexDescription(ClassifierVertex     classifierVertex,
                                                  int                  nodeId,
                                                  Pair<Double, Double> nodeGeometry)
    {
        return Arrays.asList(String.valueOf(nodeId),
                             getSinkVertexColor(classifierVertex),
                             classifierVertex.getName(),
                             getSinkVertexFields(classifierVertex),
                             getSinkVertexMethods(classifierVertex),
                             String.valueOf(nodeGeometry.getValue0()),
                             String.valueOf(nodeGeometry.getValue1()));
    }


    private String getSinkVertexMethods(ClassifierVertex classifierVertex)
    {
        if (classifierVertex.getMethods().isEmpty())
        {
            return "";
        }
        return classifierVertex.getMethods()
            .stream()
            .map(method -> method.returnType() + " " + method.name())
            .collect(Collectors.joining("\n"));
    }


    private String getSinkVertexFields(ClassifierVertex classifierVertex)
    {
        if (classifierVertex.getFields().isEmpty())
        {
            return "";
        }
        return classifierVertex.getFields()
            .stream()
            .map(field -> String.join(" ",
                                      field.type(),
                                      field.name()))
            .collect(Collectors.joining("\n"));
    }


    private String getSinkVertexColor(ClassifierVertex leafNode)
    {
        if (leafNode.getVertexType().equals(VertexType.INTERFACE))
        {
            return INTERFACE_COLOR;
        }
        return CLASS_COLOR;
    }

}
