package model.diagram.exportation;

import model.diagram.plantuml.PlantUMLVertex;
import model.diagram.plantuml.PlantUMLVertexArc;
import model.graph.Arc;
import model.graph.Vertex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class PlantUMLPackageDiagramTextExporter implements DiagramExporter {

    private final String bufferBody;

    public PlantUMLPackageDiagramTextExporter(Map<Vertex, Set<Arc<Vertex>>> diagram) {
        PlantUMLVertex plantUMLVertex = new PlantUMLVertex(diagram);
        StringBuilder plantUMLNodeBuffer = plantUMLVertex.convertVertex();
        PlantUMLVertexArc plantUMLEdge = new PlantUMLVertexArc(diagram);
        StringBuilder plantUMLEdgeBuffer = plantUMLEdge.convertVertexArc();
        bufferBody = plantUMLNodeBuffer.append("\n\n").append(plantUMLEdgeBuffer) + "\n @enduml";
    }

    @Override
    public File exportDiagram(Path exportPath) {
        File plantUMLFile = exportPath.toFile();
        String plantUMLCode = getPackageText();
        plantUMLCode += bufferBody;
        plantUMLCode = dotChanger(plantUMLCode);
        writeFile(plantUMLFile, plantUMLCode);
        return plantUMLFile;
    }

    private void writeFile(File plantUMLFile, String plantCode) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(plantUMLFile))) {
            writer.write(plantCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String dotChanger(String plantUMLCode) {
        StringBuilder newString = new StringBuilder();
        String[] lines = plantUMLCode.split("\n");
        for (String line: lines) {
            String[] splittedLine = line.split(" ");
            for (String word: splittedLine) {
                String newWord = word;
                if(word.contains(".") && !word.contains("..")) {
                    newWord = word.replace(".", "_");
                    newWord = newWord.replace("-", "_");
                }
                newString.append(newWord).append(" ");
            }
            newString.append("\n");
        }
        return newString.toString();
    }

    private String getPackageText() {
        return "@startuml\n" +
                "skinparam package {\n" +
                "    BackgroundColor lightyellow\n" +
                "    BorderColor black\n" +
                "    ArrowColor black\n" +
                "    Shadowing true\n" +
                "}\n\n";
    }
}