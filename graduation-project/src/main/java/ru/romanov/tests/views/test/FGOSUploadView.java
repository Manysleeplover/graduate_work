package ru.romanov.tests.views.test;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.FGOSUploadService;
import ru.romanov.tests.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;
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

    private final Select<String> studyDirectionSelector = new Select<>();


    public FGOSUploadView(FGOSUploadService fgosUploadService) {
        this.fgosUploadService = fgosUploadService;
        add(configureFGOSUploadFields());
    }


    private VerticalLayout configureFGOSUploadFields() {
        configureAllSelectors();

        Div uploadDiv = new Div();
        processFileUploadComponentButton.setText("Загрузить файл");
        processFileUploadComponentButton.addClickListener(event -> {
            fgosUploadService.uploadFileByComponent(memoryBuffer);
        });
        uploadDiv.add(upload);

        Div processButtonDiv = new Div();
        processButtonDiv.add(processFileUploadComponentButton);

        VerticalLayout mainUploadDiv = new VerticalLayout();
        mainUploadDiv.add(uploadDiv, processButtonDiv, levelOfTrainSelector, studyDirectionSelector);
        mainUploadDiv.setAlignItems(Alignment.CENTER);

        return mainUploadDiv;
    }

    private void configureAllSelectors() {
        levelOfTrainSelector.setItems(Stream.of("Бакалавриат", "Специалитет", "Магистратура", "Аспирантура"));
        levelOfTrainSelector.setValue("Бакалвриат");
        levelOfTrainSelector.addValueChangeListener(event -> {
            studyDirectionSelector.setItems(fgosUploadService.getAllStudyDirection(levelOfTrainSelector.getValue())
                    .stream().map(StudyDirection::getStudyDirectionCode).collect(Collectors.toList()));
        });


    }
}
