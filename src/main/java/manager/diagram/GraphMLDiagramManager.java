package manager.diagram;

import java.util.List;

public interface GraphMLDiagramManager {

    void createDiagram(List<String> chosenPackagesNames);

    void exportDiagramToGraphML(String graphMLSavePath);

}