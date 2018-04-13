package com.adonis.costAnalyzer.ui.calculator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.v7.ui.OptionGroup;

import java.util.Spliterator;
import java.util.function.Consumer;

public class CalculatorView extends VerticalLayout implements View{
    private double result = 0;
    private double n1 = 0;
    private double n2 = 0;
    private String type = "+";
    // User interface components
    private final Label display = new Label("0.0");
    public CalculatorView(String text) {
        addComponent(new Label(text));
        final TextField number1 = new TextField();
        number1.setCaption("First number:");

        final TextField number2 = new TextField();
        number2.setCaption("Second number:");

        final OptionGroup select = new OptionGroup("Choose operation");
        select.addStyleName("horizontal");
        select.addItems("+", "-", "*", "/");

        select.addValueChangeListener(e -> {
            type = String.valueOf(e.getProperty().getValue());
        });


        Button button = new Button("Result");
        button.addClickListener( e -> {
            n1 = Double.parseDouble(number1.getValue());
            n2 = Double.parseDouble(number2.getValue());
            calculate();
            addComponent(new Label( n1 + " " + type + " "  + n2 + " = " + result));
        });

        addComponents(number1, select, number2, button);
        setMargin(true);
        setSpacing(true);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }


    public void calculate(){
        switch (type) {
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "*":
                result = n1 * n2;
                break;
            case "/":
                result = n1 / n2;
                break;
            case "%":
                result = n1 % n2;
                break;
        }
    }}
