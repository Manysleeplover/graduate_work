package ru.romanov.tests.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.repository.StudyDirectionRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FGOSUploadService {

    private final String YKCompetence = "УК-";
    private final String OPKCompetence = "ОПК-";
    private final String PKCompetence = "ПК-";

    private final StudyDirectionRepository studyDirectionRepository;

    public FGOSUploadService(StudyDirectionRepository studyDirectionRepository) {
        this.studyDirectionRepository = studyDirectionRepository;
    }

    public String uploadFileByComponent(MemoryBuffer memoryBuffer) {

        File file = new File("/home/ioromanov/Диплом/graduate_work/in" + memoryBuffer.getFileName());
        try {
            FileUtils.copyInputStreamToFile(memoryBuffer.getInputStream(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> list = new ArrayList<>();

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(file.toURI())))) {

            Iterator<IBodyElement> docElementsIterator = doc.getBodyElementsIterator();

            //Iterate through the list and check for table element type
            while (docElementsIterator.hasNext()) {
                IBodyElement docElement = docElementsIterator.next();
                if ("TABLE".equalsIgnoreCase(docElement.getElementType().name())) {
                    //Get List of table and iterate it
                    List<XWPFTable> xwpfTableList = docElement.getBody().getTables();
                    for (XWPFTable xwpfTable : xwpfTableList) {
                        for (int i = 0; i < xwpfTable.getRows().size(); i++) {
                            for (int j = 0; j < xwpfTable.getRow(i).getTableCells().size(); j++) {
                                list.add(xwpfTable.getRow(i).getCell(j).getText());
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Set<String>> setMap = new HashMap<>();

        Set<String> setYK = list.stream().filter(item -> item.matches("^" + YKCompetence + ".*")).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> setOPK = list.stream().filter(item -> item.matches("^" + OPKCompetence + ".*")).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> setPK = list.stream().filter(item -> item.matches("^" + PKCompetence + ".*")).collect(Collectors.toCollection(LinkedHashSet::new));


        setMap.put(YKCompetence, setYK);
        setMap.put(OPKCompetence, setOPK);
        setMap.put(PKCompetence, setPK);


        return buildCompetenceJson(setMap);
    }

    @SneakyThrows
    private String buildCompetenceJson(Map<String, Set<String>> setMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        for (Map.Entry<String, Set<String>> item : setMap.entrySet()) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (String s : item.getValue()) {
                arrayNode.add(s);
            }
            objectNode.set(item.getKey(), arrayNode);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    }

    public List<StudyDirection> getAllStudyDirection(String levelOfTraining){

        return studyDirectionRepository.findStudyDirectionByLevelOfTraining(levelOfTraining);
    }
}
