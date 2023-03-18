package ru.romanov.tests.services;

import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FGOSUploadService {

    private String YKCompetence = "УК-";
    private String OPKCompetence = "ОПК-";
    private String PKCompetence = "ОП-";

    public void uploadFileByComponent(MemoryBuffer memoryBuffer) {

        File file = new File("/home/ioromanov/Диплом/graduate-work/in" + memoryBuffer.getFileName());
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

        Set<String> setYK = list.stream().filter(item -> item.contains(YKCompetence)).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> setOPK = list.stream().filter(item -> item.contains(OPKCompetence)).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> setPK = list.stream().filter(item -> item.contains(PKCompetence)).collect(Collectors.toCollection(LinkedHashSet::new));
        setYK.forEach(System.out::println);
        setOPK.forEach(System.out::println);
        setPK.forEach(System.out::println);

    }


    public void uploadFileByLink(String link) {
        System.out.println(link);
    }
}
