package ru.romanov.tests.views.test;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TextField uploadField = new TextField();

    private final Button processLinkButton = new Button();
    private final Button processFileUploadComponentButton = new Button();
    private final FGOSUploadService fgosUploadService;

    public FGOSUploadView(FGOSUploadService fgosUploadService) {
        this.fgosUploadService = fgosUploadService;
        add(configureFGOSUploadFields());
    }


    private VerticalLayout configureFGOSUploadFields() {
        uploadField.setLabel("Ссылка для скачивания");
        Div uploadTextFiledDiv = new Div();
        processLinkButton.setText("Выкачать по ссылке");
        processLinkButton.addClickListener(event -> {
            fgosUploadService.uploadFileByLink(uploadField.getValue());
        });
        uploadTextFiledDiv.add(uploadField, processLinkButton);

        Div uploadDiv = new Div();
        processFileUploadComponentButton.setText("Загрузить файл");
        processFileUploadComponentButton.addClickListener(event -> {
            fgosUploadService.uploadFileByComponent(memoryBuffer);
        });
        uploadDiv.add(upload);

        Div processButtonDiv = new Div();
        processButtonDiv.add(processFileUploadComponentButton);

        VerticalLayout mainUploadDiv = new VerticalLayout();
        mainUploadDiv.add(uploadTextFiledDiv, uploadDiv, processButtonDiv);
        mainUploadDiv.setAlignItems(Alignment.CENTER);

        return mainUploadDiv;
    }
}
