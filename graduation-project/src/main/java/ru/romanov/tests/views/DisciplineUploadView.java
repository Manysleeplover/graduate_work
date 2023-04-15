package ru.romanov.tests.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.romanov.tests.entity.Discipline;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.CompetenceUploadService;
import ru.romanov.tests.services.DisciplineUploadService;

import java.util.stream.Stream;

import static ru.romanov.tests.utils.ViewUtils.createDivForComponents;
import static ru.romanov.tests.utils.ViewUtils.createTextField;

@PageTitle("Назначение дисциплин")
@Route(value = "/disciplines", layout = MainLayout.class)
public class DisciplineUploadView extends VerticalLayout {

    /**
     * Сервис обработчик загрузки
     */
    private final CompetenceUploadService competenceUploadService;
    private final DisciplineUploadService disciplineUploadService;
    private final Select<String> levelOfTraining = new Select<>();
    private final Select<StudyDirection> studyDirectionSelect = new Select<>();

    private TextField listOfCompetence;
    private TextField blockName;
    private TextField partName;
    private TextField typeOfDiscipline;
    private TextField disciplineName;
    private TextField numberOfSemesters;

    private final Button processButton = new Button();

    private Grid<Discipline> disciplineGrid = new Grid<>(Discipline.class, false);


    public DisciplineUploadView(CompetenceUploadService competenceUploadService, DisciplineUploadService disciplineUploadService) {
        this.competenceUploadService = competenceUploadService;
        this.disciplineUploadService = disciplineUploadService;
        configureProcessButton();
        configureAllSelectors();
        configureTextFields();
        configureDisciplineGrid();
        add(createFiledsComponent());
        disciplineGrid.setItems(disciplineUploadService.getAllDiscipline());
        add(disciplineGrid);
    }

    private void configureDisciplineGrid() {
        disciplineGrid.setItems(disciplineUploadService.getAllDiscipline());
        disciplineGrid.addColumn(Discipline::getId).setHeader("Id");
        disciplineGrid.addColumn(Discipline::getDisciplineName).setHeader("Название дисциплины");
        disciplineGrid.addColumn(Discipline::getSemesterNumbers).setHeader("Номера семестров");
        disciplineGrid.addColumn(Discipline::getListOfCompetence).setHeader("Список компетенций");
        disciplineGrid.addColumn(Discipline::getBlockName).setHeader("Название блока");
        disciplineGrid.addColumn(Discipline::getPartName).setHeader("Название части");
        disciplineGrid.addColumn(Discipline::getTypeOfDiscipline).setHeader("Тип дисциплины");
        disciplineGrid.setItemDetailsRenderer(createDisciplineDetailsRender());
    }


    private void configureProcessButton() {
        processButton.setText("Обработать");
        processButton.addClickListener(event -> {
            Discipline discipline = new Discipline();
            discipline.setStudyDirection(studyDirectionSelect.getValue());
            discipline.setListOfCompetence(listOfCompetence.getValue());
            discipline.setDisciplineName(disciplineName.getValue());
            discipline.setSemesterNumbers(numberOfSemesters.getValue());
            discipline.setBlockName(blockName.getValue());
            discipline.setPartName(partName.getValue());
            discipline.setTypeOfDiscipline(typeOfDiscipline.getValue());
            discipline.setIdStudyDirection(studyDirectionSelect.getValue().getId());
            disciplineGrid.setItems(disciplineUploadService.uploadDiscipline(discipline));
        });
    }


    private void configureTextFields() {
        listOfCompetence = createTextField("Список компетенций", false,
                null, "", null, null);

        blockName = createTextField("Название блока", false,
                null, "", null, null);
        partName = createTextField("Название части", false,
                null, "", null, null);
        typeOfDiscipline = createTextField("Тип дисциплины", false,
                null, "", null, null);
        disciplineName = createTextField("Название дисциплины", false,
                null, "", null, null);
        numberOfSemesters = createTextField("Номера семестров", false,
                null, "", null, null);
    }

    private void configureAllSelectors() {
        levelOfTraining.setLabel("Выбрать уровень подготовки");
        levelOfTraining.setItems(Stream.of("Бакалавриат", "Специалитет", "Магистратура", "Аспирантура"));

        studyDirectionSelect.setLabel("Выбрать направление обучения");
        levelOfTraining.addValueChangeListener(event -> studyDirectionSelect.setItems(competenceUploadService.getAllStudyDirection(levelOfTraining.getValue())));

        levelOfTraining.setWidth("20%");
        levelOfTraining.getStyle().set("margin-right", "5%");

        studyDirectionSelect.setWidth("20%");
        studyDirectionSelect.getStyle().set("margin-right", "5%");
    }

    private Component[] createFiledsComponent() {
        Div divData1 = createDivForComponents(levelOfTraining, studyDirectionSelect);
        divData1.getStyle().set("text-align", "left");
        Div divData2 = createDivForComponents(typeOfDiscipline, disciplineName, numberOfSemesters);
        divData2.getStyle().set("text-align", "left");
        Div divData3 = createDivForComponents(listOfCompetence, blockName, partName);
        divData3.getStyle().set("text-align", "left");
        Div divButton = createDivForComponents(processButton);

        return new Component[]{divData1, divData2, divData3, divButton};
    }


    private ComponentRenderer<DisciplineDetailsFormLayout, Discipline> createDisciplineDetailsRender() {
        return new ComponentRenderer<>(
                DisciplineDetailsFormLayout::new,
                DisciplineDetailsFormLayout::setDiscipline);
    }

    private class DisciplineDetailsFormLayout extends FormLayout {
        private final TextField studyDirections = new TextField("Направление подготовки");
        private final TextField disciplineName = new TextField("Название дисциплины");
        private final TextField semesterNumber = new TextField("Номера семестров");
        private final TextField listOfCompetences = new TextField("Список компетенций");
        private final TextField blockName = new TextField("Название Блока");
        private final TextField partName = new TextField("Название части");
        private final TextField typeOfDiscipline = new TextField("Тип дисциплины");
        private final Button deleteButton = new Button("Удалить дисциплину");

        public DisciplineDetailsFormLayout() {
            Stream.of(studyDirections, disciplineName, semesterNumber, listOfCompetences,
                    blockName, partName, typeOfDiscipline).forEach(item -> {
                item.setReadOnly(true);
                add(item);
            });
            studyDirections.setReadOnly(true);
            add(deleteButton);


            setResponsiveSteps(new ResponsiveStep("0", 2));
            setColspan(studyDirections, 1);
            setColspan(disciplineName, 1);
            setColspan(semesterNumber, 1);
            setColspan(listOfCompetences, 1);
            setColspan(blockName, 1);
            setColspan(partName, 1);
            setColspan(typeOfDiscipline, 1);
            setColspan(deleteButton, 1);
        }


        public void setDiscipline(Discipline discipline) {
            studyDirections.setValue(discipline.getStudyDirection().getStudyDirectionCode());
            disciplineName.setValue(discipline.getDisciplineName());
            semesterNumber.setValue(discipline.getSemesterNumbers());
            listOfCompetences.setValue(discipline.getListOfCompetence());
            blockName.setValue(discipline.getBlockName());
            partName.setValue(discipline.getPartName());
            typeOfDiscipline.setValue(discipline.getTypeOfDiscipline());
            deleteButton.addClickListener(event -> {
                disciplineUploadService.deleteDiscipline(discipline);
                disciplineGrid.setItems(disciplineUploadService.getAllDiscipline());
            });
        }
    }
}
