package ru.romanov.tests.utils;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;

public class ViewUtils {
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
