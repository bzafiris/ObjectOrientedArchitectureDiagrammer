package model;

import manager.diagram.ClassDiagramManager;
import manager.diagram.DiagramManager;
import model.diagram.CollectionsDiagramConverter;
import model.diagram.GraphEdgeCollection;
import model.diagram.GraphNodeCollection;
import model.diagram.graphml.GraphMLLeafEdge;
import model.diagram.graphml.GraphMLLeafNode;
import model.tree.Node;
import model.tree.PackageNode;
import model.tree.Relationship;
import model.tree.SourceProject;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionsDiagramConvertTest {

    @Test
    void convertCollectionsToDiagramTest() {
        DiagramManager classDiagramManager = new ClassDiagramManager();
        List<String> chosenFiles = Arrays.asList("MainWindow", "LatexEditorView", "OpeningWindow");
        SourceProject sourceProject = classDiagramManager.createTree("src\\test\\resources\\LatexEditor\\src");
        Map<String, Map<String, String>> testingCreatedDiagram = classDiagramManager.createDiagram(chosenFiles);

        GraphNodeCollection graphNodeCollection = new GraphMLLeafNode();
        GraphEdgeCollection graphEdgeCollection = new GraphMLLeafEdge();
        graphNodeCollection.populateGraphNodes(getChosenNodes(chosenFiles, sourceProject));
        graphEdgeCollection.setGraphNodes(graphNodeCollection.getGraphNodes());
        graphEdgeCollection.populateGraphEdges(getChosenNodes(chosenFiles, sourceProject));

        CollectionsDiagramConverter collectionsDiagramConverter = new CollectionsDiagramConverter(graphNodeCollection, graphEdgeCollection);
        Map<String, Map<String, String>> actualDiagram = collectionsDiagramConverter.convertCollectionsToDiagram();

        Map<String, Map<String, String>> expectedDiagram = new HashMap<>();
        for (Node leafNode: graphNodeCollection.getGraphNodes().keySet()) {
            Map<String, String> nodeEdges = new HashMap<>();
            for (Relationship relationship: graphEdgeCollection.getGraphEdges().keySet()){
                if (relationship.getStartingNode().equals(leafNode)) {
                    nodeEdges.put(relationship.getEndingNode().getName() + "_" + relationship.getEndingNode().getType().name(),
                            relationship.getRelationshipType().name());
                }
            }
            expectedDiagram.put(leafNode.getName() + "_" + leafNode.getType().name(), nodeEdges);
        }
    }

    private List<Node> getChosenNodes(List<String> chosenClassesNames, SourceProject sourceProject) {
        List<Node> chosenClasses = new ArrayList<>();
        for (String chosenClass: chosenClassesNames) {
            for (PackageNode p: sourceProject.getPackageNodes().values()){
                if (p.getLeafNodes().containsKey(chosenClass)) {
                    chosenClasses.add(p.getLeafNodes().get(chosenClass));
                    break;
                }
            }
        }
        return chosenClasses;
    }
}
