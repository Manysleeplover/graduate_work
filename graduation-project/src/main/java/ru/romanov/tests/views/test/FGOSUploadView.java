package ru.romanov.tests.views.test;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import ru.romanov.tests.entity.Competence;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.FGOSUploadService;
import ru.romanov.tests.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;

@PageTitle("Загрузка списка компетенций")
@Route(value = "/", layout = MainLayout.class)
public class FGOSUploadView extends VerticalLayout {

    /**
     * Сервис обработчик загрузки
     */
    private final FGOSUploadService fgosUploadService;
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
    private final Grid<Competence> competenceGrid = new Grid<>(Competence.class, true);


    public FGOSUploadView(FGOSUploadService fgosUploadService) {
        this.fgosUploadService = fgosUploadService;
        configureGrid();
        configureAllSelectors();
        add(configureFGOSUploadFields());
    }


    private VerticalLayout configureFGOSUploadFields() {
        Div uploadDiv = new Div();
        processFileUploadComponentButton.setText("Загрузить файл");
        processFileUploadComponentButton.addClickListener(event -> {
            competenceGrid.setItems(fgosUploadService.uploadFileByComponent(memoryBuffer, studyDirectionSelector.getValue()));
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
        levelOfTrainSelector.setItems(Stream.of("Бакалавриат", "Специалитет", "Магистратура", "Аспирантура"));
        levelOfTrainSelector.setValue("Бакалвриат");
        levelOfTrainSelector.addValueChangeListener(event -> {
            studyDirectionSelector.setItems(fgosUploadService.getAllStudyDirection(levelOfTrainSelector.getValue()));
        });
    }

    private void configureGrid() {
        competenceGrid.setItems(fgosUploadService.getCompetence());
    }
}
