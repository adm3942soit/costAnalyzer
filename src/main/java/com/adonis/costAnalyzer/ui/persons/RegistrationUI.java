package com.adonis.costAnalyzer.ui.persons;

import com.adonis.costAnalyzer.data.persons.Person;
import com.adonis.costAnalyzer.data.service.PersonService;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageForm;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by oksdud on 04.04.2017.
 */
public class RegistrationUI extends CustomComponent implements com.vaadin.navigator.View {

    private PersonService service;

    public static final String NAME = "RegistrationUI";

    PersonView editor = new PersonView(this::savePerson, null, this::addPerson, false);//this::deletePerson, this::addPerson, false);

    public RegistrationUI (PersonService personService){
        setPrimaryStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        this.service = personService;
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.addComponent(editor);
        setCompositionRoot(viewLayout);

    }
    private void savePerson(Person person) {
        MessageForm.winSave(service, person);
    }

    private void deletePerson(Person person) {
        service.delete(person);
        MessageForm.winDefault("Person saved successfully!");

    }
    private void addPerson(Person person){
        Person person1 = service.insert(person);
        if(person1!=null) {
            Notification.show("Person inserted successfully!");
            MessageForm.winInsert(service, person);

        }
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
}
