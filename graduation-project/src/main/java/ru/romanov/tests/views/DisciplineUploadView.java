package ru.romanov.tests.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.services.CompetenceUploadService;

import java.util.stream.Stream;

@PageTitle("Назначение дисциплин")
@Route(value = "/disciplines", layout = MainLayout.class)
public class DisciplineUploadView extends VerticalLayout {

    /**
     * Сервис обработчик загрузки
     */
    private final CompetenceUploadService competenceUploadService;
    private final Select<String> levelOfTraining = new Select<>();
    private final Select<StudyDirection> studyDirectionSelect = new Select<>();

    private TextField listOfCompetence;
    private TextField blockName;
    private TextField partName;
    private TextField typeOfDiscipline;
    private TextField disciplineName;
    private TextField numberOfSemesters;

    private final Button processButton = new Button();


    public DisciplineUploadView(CompetenceUploadService competenceUploadService) {
        this.competenceUploadService = competenceUploadService;
        configureProcessButton();
        configureAllSelectors();
        configureTextFields();
        add(createFiledsComponent());
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

    private void configureProcessButton(){
        processButton.setText("Обработать");
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

    public static Div createDivForComponents(Component... components) {
        Div div = new Div();
        div.getStyle().set("text-align", "center");
        div.setWidth("100%");
        div.add(components);
        return div;
    }

    public static <O> TextField createTextField(String label, boolean readOnly, String propertyNameForValidator,
                                                String value, BeanValidationBinder<O> binder,
                                                HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TextField, String>> listener) {
        TextField textField = new TextField(label);
        textField.setReadOnly(readOnly);
        setStandardStyleForComponent(textField, propertyNameForValidator, binder);
        if (listener != null) {
            textField.addValueChangeListener(listener);
        }
        if (value != null) textField.setValue(value);
        return textField;
    }

    public static <O> void setStandardStyleForComponent(Component component, String propertyNameForValidator, BeanValidationBinder<O> binder) {
        if (component instanceof HasSize)
            ((HasSize) component).setWidth("20%");
        if (component instanceof HasStyle)
            ((HasStyle) component).getStyle().set("margin-right", "5%");
        if (component instanceof HasValue && propertyNameForValidator != null && binder != null)
            binder.forField((HasValue<?, ?>) component).bind(propertyNameForValidator);
    }

}
