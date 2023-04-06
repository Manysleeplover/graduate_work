package ru.romanov.tests.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import ru.romanov.tests.entity.Competence;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.CompetenceUploadService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;

@PageTitle("Загрузка списка компетенций")
@Route(value = "/", layout = MainLayout.class)
public class CompetenceUploadView extends VerticalLayout {

    /**
     * Сервис обработчик загрузки
     */
    private final CompetenceUploadService competenceUploadService;
    /**
     * Буфер для загрузки файла
     */
    private final MemoryBuffer memoryBuffer = new MemoryBuffer();
    /**
     * Компонент под загрузку файла
     */
    private final Upload upload = new Upload(memoryBuffer);
    /**
     * Кнопка события обработки файла
     */
    private final Button processFileUploadComponentButton = new Button();
    private final Select<String> levelOfTrainSelector = new Select<>();
    private final Select<StudyDirection> studyDirectionSelector = new Select<>();
    private final Grid<Competence> competenceGrid = new Grid<>(Competence.class, false);


    public CompetenceUploadView(CompetenceUploadService competenceUploadService) {
        this.competenceUploadService = competenceUploadService;
        configureGrid();
        configureAllSelectors();
        add(configureFGOSUploadFields());
    }


    private VerticalLayout configureFGOSUploadFields() {
        Div uploadDiv = new Div();
        processFileUploadComponentButton.setText("Загрузить файл");
        processFileUploadComponentButton.addClickListener(event -> {
            competenceGrid.setItems(competenceUploadService.uploadFileByComponent(memoryBuffer, studyDirectionSelector.getValue()));
        });
        uploadDiv.add(upload);

        Div processButtonDiv = new Div();
        processButtonDiv.add(processFileUploadComponentButton);

        VerticalLayout mainUploadDiv = new VerticalLayout();
        mainUploadDiv.add(uploadDiv, processButtonDiv, levelOfTrainSelector, studyDirectionSelector, competenceGrid);
        mainUploadDiv.setAlignItems(Alignment.CENTER);

        return mainUploadDiv;
    }

    private void configureAllSelectors() {
        levelOfTrainSelector.setLabel("Выбрать уровень подготовки");
        levelOfTrainSelector.setItems(Stream.of("Бакалавриат", "Специалитет", "Магистратура", "Аспирантура"));

        studyDirectionSelector.setLabel("Выбрать направление обучения");
        levelOfTrainSelector.addValueChangeListener(event -> {
            studyDirectionSelector.setItems(competenceUploadService.getAllStudyDirection(levelOfTrainSelector.getValue()));
        });
    }

    private void configureGrid() {
        competenceGrid.setItems(competenceUploadService.getCompetence());
        competenceGrid.addColumn(Competence::getId).setHeader("Id").setWidth("7%");
        competenceGrid.addColumn(Competence::getStudyDirection).setHeader("Направление обучения").setWidth("14%");
        competenceGrid.addColumn(Competence::getCompetenceList).setHeader("Список компетенций по направлению").setWidth("79%");
        competenceGrid.setItemDetailsRenderer(createPersonDetailsRenderer());
    }

    private static ComponentRenderer<PersonDetailsFormLayout, Competence> createPersonDetailsRenderer() {
        return new ComponentRenderer<>(
                PersonDetailsFormLayout::new,
                PersonDetailsFormLayout::setCompetence);
    }

    private static class PersonDetailsFormLayout extends FormLayout {
        private final TextField studyDirections = new TextField("Направление подготовки");
        private final Paragraph listOfCompetence = new Paragraph();

        public PersonDetailsFormLayout() {
            Stream.of(studyDirections, listOfCompetence).forEach(this::add);
            studyDirections.setReadOnly(true);

            listOfCompetence.getElement().getStyle().set("white-space", "pre");

            setResponsiveSteps(new ResponsiveStep("0", 2));
            setColspan(studyDirections, 3);
            setColspan(listOfCompetence, 3);
        }

        public void setCompetence(Competence  competence) {
            studyDirections.setValue(competence.getStudyDirection().getStudyDirectionCode());
            listOfCompetence.setText(competence.getCompetenceList());
        }
    }

}
