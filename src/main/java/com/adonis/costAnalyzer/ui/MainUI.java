package com.adonis.costAnalyzer.ui;

import com.adonis.costAnalyzer.data.persons.Person;
import com.adonis.costAnalyzer.data.service.PersonService;
import com.adonis.costAnalyzer.ui.calculator.CalculatorView;
import com.adonis.costAnalyzer.ui.login.AccessControl;
import com.adonis.costAnalyzer.ui.login.BasicAccessControl;
import com.adonis.costAnalyzer.ui.login.LoginView;
import com.adonis.costAnalyzer.ui.persons.PersonUI;
import com.adonis.costAnalyzer.ui.persons.PersonsCrudView;
import com.adonis.costAnalyzer.ui.persons.RegistrationUI;
import com.vaadin.annotations.*;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;
import org.vaadin.teemusa.sidemenu.SideMenu.MenuRegistration;

import javax.annotation.PostConstruct;
import javax.servlet.Registration;
import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("SideMenu Add-on Demo")
@Viewport("user-scalable=no,initial-scale=1.0")
@SpringUI
@Widgetset("AppWidgetset")
public class MainUI extends UI {
    @Autowired
    public PersonService personService;
    public static PersonsCrudView personsCrudView;
    public static Person loginPerson;
    PersonUI personView;
    public LoginView loginView;
    RegistrationUI registrationUI;
    private AccessControl accessControl = new BasicAccessControl();

    private final class CategoryView extends VerticalLayout implements View {

        public CategoryView(String text) {
            addComponent(new Label(text));
        }

        @Override
        public void enter(ViewChangeEvent event) {
        }
    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private SideMenu sideMenu = new SideMenu();
    private boolean logoVisible = true;
    private ThemeResource logo = new ThemeResource("images/cost.png");
    private String menuCaption = "Cost Analyzer";

    @PostConstruct
    void load() {
        if(personService.findTotalCustomer()==0) {
            personService.loadData();
        }

        personView = new PersonUI(personService, false, null);
        loginView = new LoginView(personService, new LoginView.LoginListener() {
            @Override
            public void loginSuccessful() {
                showMainView();
            }
        });
        registrationUI = new RegistrationUI(personService);

        personsCrudView = new PersonsCrudView(personService);
    }


    @Override
    protected void init(VaadinRequest request) {
        setContent(sideMenu);
        Navigator navigator = new Navigator(this, sideMenu);
        setNavigator(navigator);

        // NOTE: Navigation and custom code menus should not be mixed.
        // See issue #8

        navigator.addView("", new CategoryView("Main UI"));
        navigator.addView("Category", new CategoryView("Expenditure category"));
        navigator.addView("Calculator", new CalculatorView("Calculator"));
        navigator.addView(PersonUI.NAME, personView);
        navigator.addView(LoginView.NAME, loginView);
        navigator.addView(RegistrationUI.NAME, registrationUI);
        navigator.addView(PersonsCrudView.NAME, personsCrudView);
        if (!accessControl.isUserSignedIn()) {
            getNavigator().navigateTo(LoginView.NAME);
        } else {
            showMainView();
        }

        // Since we're mixing both navigator and non-navigator menus the
        // navigator state needs to be manually triggered.
        navigator.navigateTo("");

        sideMenu.setMenuCaption(menuCaption, logo);

        // Navigation examples
//        sideMenu.addNavigation("Initial View", "");
        sideMenu.addNavigation("Category", VaadinIcons.COMBOBOX, "Category");
        sideMenu.addNavigation("Users", VaadinIcons.USER, PersonsCrudView.NAME);
        sideMenu.addNavigation("Calculator", VaadinIcons.CALC, "Calculator");
        sideMenu.addNavigation(PersonUI.NAME, VaadinIcons.USER, PersonUI.NAME);
        sideMenu.addNavigation(RegistrationUI.NAME, VaadinIcons.USER, RegistrationUI.NAME);

        // User menu controls
        sideMenu.addMenuItem("Show/Hide user menu", VaadinIcons.USER, () -> sideMenu.setUserMenuVisible( !sideMenu.isUserMenuVisible()));

        setUser("Guest", VaadinIcons.MALE);
    }
    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        getNavigator().navigateTo("");
    }

    private void setUser(String name, Resource icon) {
        sideMenu.setUserName(name);
        sideMenu.setUserIcon(icon);

        sideMenu.clearUserMenu();
        sideMenu.addUserMenuItem("Settings", VaadinIcons.WRENCH, () -> Notification.show("Showing settings", Type.TRAY_NOTIFICATION));
        sideMenu.addUserMenuItem("Sign out", () -> Notification.show("Logging out..", Type.TRAY_NOTIFICATION));

        sideMenu.addUserMenuItem("Hide logo", () -> {
            if (!logoVisible) {
                sideMenu.setMenuCaption(menuCaption, logo);
            } else {
                sideMenu.setMenuCaption(menuCaption);
            }
            logoVisible = !logoVisible;
        });
    }

    public PersonService getPersonService() {
        return personService;
    }
}