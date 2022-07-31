package model.diagram;

import model.diagram.graphml.GraphMLPackageEdge;
import model.diagram.graphml.GraphMLPackageNode;
import model.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class PackageDiagram extends Diagram {

    public PackageDiagram() {
        super();
        graphNodeCollection = new GraphMLPackageNode();
        graphEdgeCollection = new GraphMLPackageEdge();
    }

    public List<Node> getChosenNodes(List<String> chosenPackagesNames) {
        List<Node> chosenPackages = new ArrayList<>();
        for (String chosenPackage: chosenPackagesNames) {
            if (sourceProject.getPackageNodes().get(chosenPackage).isValid()) {
                chosenPackages.add(sourceProject.getPackageNodes().get(chosenPackage));
            }
        }
        return chosenPackages;
    }

}
