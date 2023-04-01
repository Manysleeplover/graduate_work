package ru.romanov.tests.views.test;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import ru.romanov.tests.services.FGOSUploadService;
import ru.romanov.tests.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Загрузка ФГОС")
@Route(value = "/", layout = MainLayout.class)
public class FGOSUploadView extends VerticalLayout {


    private final MemoryBuffer memoryBuffer = new MemoryBuffer();
    private final Upload upload = new Upload(memoryBuffer);
    private final Button processFileUploadComponentButton = new Button();
    private final FGOSUploadService fgosUploadService;
    private final TextArea textArea = new TextArea();

    public FGOSUploadView(FGOSUploadService fgosUploadService) {
        this.fgosUploadService = fgosUploadService;
        add(configureFGOSUploadFields());
    }


    private VerticalLayout configureFGOSUploadFields() {
        textArea.setReadOnly(true);

        Div uploadDiv = new Div();
        processFileUploadComponentButton.setText("Загрузить файл");
        processFileUploadComponentButton.addClickListener(event -> {
            textArea.setValue(fgosUploadService.uploadFileByComponent(memoryBuffer));
        });
        uploadDiv.add(upload);

        Div processButtonDiv = new Div();
        processButtonDiv.add(processFileUploadComponentButton);

        VerticalLayout mainUploadDiv = new VerticalLayout();
        mainUploadDiv.add(uploadDiv, processButtonDiv, textArea);
        mainUploadDiv.setAlignItems(Alignment.CENTER);

        return mainUploadDiv;
    }
}
