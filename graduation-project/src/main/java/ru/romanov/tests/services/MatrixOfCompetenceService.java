package ru.romanov.tests.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.romanov.tests.entity.Competence;
import ru.romanov.tests.entity.Discipline;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.repository.CompetenceRepository;
import ru.romanov.tests.repository.DisciplineRepository;
import ru.romanov.tests.repository.StudyDirectionRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatrixOfCompetenceService {
    private final DisciplineRepository disciplineRepository;
    private final StudyDirectionRepository studyDirectionRepository;

    private final CompetenceRepository competenceRepository;

    public MatrixOfCompetenceService(DisciplineRepository disciplineRepository, StudyDirectionRepository studyDirectionRepository, CompetenceRepository competenceRepository) {
        this.disciplineRepository = disciplineRepository;
        this.studyDirectionRepository = studyDirectionRepository;
        this.competenceRepository = competenceRepository;
    }


    public String buildMatrixOfCompetence(StudyDirection studyDirection) {
        Competence competence = competenceRepository.findCompetenceByIdStudyDirection(studyDirection.getId());
        List<Discipline> disciplineList = disciplineRepository.findDisciplineByIdStudyDirection(studyDirection.getId());
        List<String> blocks = disciplineList.stream().map(Discipline::getBlockName).distinct().collect(Collectors.toList());
        LinkedHashMap<String, LinkedHashMap<String, List<Discipline>>> commonList = new LinkedHashMap<>();

        for (String block : blocks) {
            List<String> parts = disciplineList.stream().filter(item -> item.getBlockName().equals(block)).map(Discipline::getPartName)
                    .distinct().collect(Collectors.toList());
            for (String part : parts) {
                List<Discipline> collect = disciplineList.stream().filter(item -> item.getPartName().equals(part)).collect(Collectors.toList());
                LinkedHashMap<String, List<Discipline>> partHashMap = new LinkedHashMap<>();
                partHashMap.put(part, collect);
                commonList.put(block, partHashMap);
            }
        }

        System.out.println(commonList);


        try {
            getXLSXFile(commonList, competence.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public String getXLSXFile(LinkedHashMap<String, LinkedHashMap<String, List<Discipline>>> disciplineList, long idCompetence) throws IOException {
        String fileLocation = "/home/ioromanov/Диплом/graduate_work/files/out/" + "matrix" + ".xlsx";

        try (FileOutputStream outputStream = new FileOutputStream(fileLocation);
             Workbook workbook = new XSSFWorkbook()) {
            //страница с qd
            setDisciplineToYKCompetenceToXLSX(workbook, disciplineList, idCompetence, "УК-");
            setDisciplineToYKCompetenceToXLSX(workbook, disciplineList, idCompetence, "ОПК-");
            setDisciplineToYKCompetenceToXLSX(workbook, disciplineList, idCompetence, "ПК-");


            workbook.write(outputStream);
        }

        return fileLocation;
    }


    /**
     * Вставляем данные по QD в xlsx
     *
     * @param workbook - книга экселя
     */
    private void setDisciplineToYKCompetenceToXLSX(Workbook workbook, LinkedHashMap<String, LinkedHashMap<String, List<Discipline>>> commonList, long idCompetence, String competenceName) {
        Competence competence = competenceRepository.findCompetenceById(idCompetence);

        String competenceList = competence.getCompetenceList();

        JSONObject mainNode = new JSONObject(competenceList);
        JSONArray ykArray = mainNode.getJSONArray(competenceName);


        //В этой книге создаем страницу
        Sheet queryDataSheet = workbook.createSheet("Матрица компетенций" + competenceName);
        queryDataSheet.setColumnWidth(0, 60 * 256);

        //На этой странице задаем заголовки столбцов
        Row headerRow = queryDataSheet.createRow(0);

        //непосредственно заголовки
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Наименование дисциплины (модуля), структурного элемента ОП ВО");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Семестры");

        for (int i = 0; i < ykArray.length(); i++) {
            Cell cell = headerRow.createCell(i + 2);
            String string = ykArray.getString(i);
            cell.setCellValue(competenceName+(i+1));
        }

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font newFont = headerCell.getSheet().getWorkbook().createFont();
        newFont.setBold(true);
        newFont.setFontHeightInPoints((short) 10);
        newFont.setItalic(false);
        headerCellStyle.setFont(newFont);



        for (Cell cell : headerRow) {
            cell.setCellStyle(headerCellStyle);
        }



        //Сетим все найденные заказы query_data построчно в xlsx
        int count = 1;
        for (Map.Entry<String, LinkedHashMap<String, List<Discipline>>> outerEntry : commonList.entrySet()) {
            Row row = queryDataSheet.createRow(count);
            Cell cell = row.createCell(0);
            cell.setCellValue(outerEntry.getKey());
            count++;
            for (Map.Entry<String, List<Discipline>> innerEntry : outerEntry.getValue().entrySet()) {
                Row row1 = queryDataSheet.createRow(count);
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue(innerEntry.getKey());
                count++;
                for (Discipline discipline : innerEntry.getValue()) {
                    Row row2 = queryDataSheet.createRow(count);
                    Cell cell2 = row2.createCell(0);
                    cell2.setCellValue(discipline.getDisciplineName());
                    List<String> list = List.of(discipline.getListOfCompetence().split(","));
                    int disciplineCounter = 0;
                    for (Cell cell3 : headerRow) {
                        for (String s : list) {
                            if (cell3.getStringCellValue().contains(s.replace(" ", ""))) {
                                Cell cell4 = row2.createCell(disciplineCounter);
                                cell4.setCellValue("+");
                            }
                        }
                        disciplineCounter++;
                    }
                    Cell semestrCell = row2.createCell(1);
                    semestrCell.setCellValue(discipline.getSemesterNumbers());
                    count++;
                }
            }
        }
    }
}
