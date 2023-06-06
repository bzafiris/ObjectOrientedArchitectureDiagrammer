package manager;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import model.diagram.ClassDiagram;
import model.diagram.DiagramExporter;
import model.graph.Arc;
import model.graph.SinkVertex;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class ClassDiagramManager implements DiagramManager {

    private final SourceProject sourceProject;
    private final ArrayDeque<ClassDiagram> diagramStack;
    private Map<SinkVertex, Set<Arc<SinkVertex>>> diagram;

    public ClassDiagramManager() {
        sourceProject = new SourceProject();
        diagramStack = new ArrayDeque<>();
    }

    @Override
    public SourceProject createSourceProject(Path sourcePackagePath) {
        diagramStack.push(new ClassDiagram());
        sourceProject.createGraph(sourcePackagePath);
        Objects.requireNonNull(diagramStack.peek()).setSinkVertices(sourceProject.getSinkVertices());
        return sourceProject;
    }

    @Override
    public void createDiagram(List<String> chosenFilesNames) {
        diagram = Objects.requireNonNull(diagramStack.peek()).createDiagram(chosenFilesNames);
    }

    @Override
    public Map<Integer, List<Double>> arrangeDiagram(){
        return Objects.requireNonNull(diagramStack.peek()).arrangeDiagram();
    }

    @Override
    public File exportDiagramToGraphML(Path graphMLSavePath) {
        DiagramExporter diagramExporter = Objects.requireNonNull(diagramStack.peek()).createGraphMLExporter();
        return diagramExporter.exportDiagram(graphMLSavePath);
    }

    @Override
    public File exportPlantUMLDiagram(Path plantUMLSavePath) {
        DiagramExporter diagramExporter = Objects.requireNonNull(diagramStack.peek()).createPlantUMLImageExporter();
        return diagramExporter.exportDiagram(plantUMLSavePath);
    }

    @Override
    public File exportPlantUMLText(Path textSavePath) {
        DiagramExporter diagramExporter = Objects.requireNonNull(diagramStack.peek()).createPlantUMLTextExporter();
        return diagramExporter.exportDiagram(textSavePath);
    }

    @Override
    public File saveDiagram(Path graphSavePath) {
        DiagramExporter diagramExporter = Objects.requireNonNull(diagramStack.peek()).createJavaFXExporter();
        return diagramExporter.exportDiagram(graphSavePath);
    }

    @Override
    public void loadDiagram(Path graphSavePath) {
        diagramStack.push(new ClassDiagram());
        diagram = Objects.requireNonNull(diagramStack.peek()).loadDiagram(graphSavePath);
    }

    @Override
    public SmartGraphPanel<String, String> visualizeJavaFXGraph() {
        return Objects.requireNonNull(diagramStack.peek()).visualizeJavaFXGraph();
    }

    public ClassDiagram getDiagram() {
        return diagramStack.peek();
    }

    public Map<SinkVertex, Set<Arc<SinkVertex>>> getCreatedDiagram() { return  diagram; }

}
