package ru.romanov.tests.views;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.CompetenceUploadService;
import ru.romanov.tests.services.MatrixOfCompetenceService;

import java.util.stream.Stream;

import static ru.romanov.tests.utils.ViewUtils.createDivForComponents;

@PageTitle("Создание матрицы компетенций")
@Route(value = "/matrix_of_competence", layout = MainLayout.class)
public class MatrixOfCompetenceView extends VerticalLayout {

    private final CompetenceUploadService competenceUploadService;
    private final MatrixOfCompetenceService matrixOfCompetenceService;

    private final Select<String> levelOfTraining = new Select<>();
    private final Select<StudyDirection> studyDirectionSelect = new Select<>();

    private final Button downloadMatrixButton = new Button();
    public MatrixOfCompetenceView(CompetenceUploadService competenceUploadService, MatrixOfCompetenceService matrixOfCompetenceService) {
        this.competenceUploadService = competenceUploadService;
        this.matrixOfCompetenceService = matrixOfCompetenceService;
        configureAllSelectors();
        configureDownloadMatrixButton();
        add(createFiledsComponent());
    }

    private void configureDownloadMatrixButton(){
        downloadMatrixButton.setText("Сформировать матрицу компетенций");
        downloadMatrixButton.addClickListener(event -> {
            System.out.println(studyDirectionSelect.getValue().getId());
            matrixOfCompetenceService.buildMatrixOfCompetence(studyDirectionSelect.getValue());
        });
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

        Div divData2 = createDivForComponents(downloadMatrixButton);
        divData2.getStyle().set("text-align", "left");

        return new Component[]{divData1, divData2};
    }
}
