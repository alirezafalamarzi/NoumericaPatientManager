package com.noumerica.noumericahealth.views.dashboard;


import com.noumerica.noumericahealth.data.Log;
import com.noumerica.noumericahealth.data.Patient;
import com.noumerica.noumericahealth.security.AuthenticatedUser;
import com.noumerica.noumericahealth.services.LogService;
import com.noumerica.noumericahealth.services.PatientService;
import com.noumerica.noumericahealth.services.UserService;
import com.noumerica.noumericahealth.views.MainLayout;
import com.noumerica.noumericahealth.views.customelement.Panel;
import com.noumerica.noumericahealth.views.dashboard.ServiceHealth.Status;
import com.noumerica.noumericahealth.views.login.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import java.util.List;

@PageTitle("Dashboard")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "dashboard")
@AnonymousAllowed
public class DashboardView extends VerticalLayout {

    private AuthenticatedUser authUser;
    private UserService userService;
    private final LogService logService;
    private final PatientService patientService;
    /**
     * Our dashboard, if no user is detected by our system,
     * it prompts you to login, otherwise show patient panel
     * and syslog depending on what type of user logs on.
     */
    @Autowired
    public DashboardView(PatientService patientService, AuthenticatedUser authUser, UserService userService, LogService logService) {
        this.patientService = patientService;
        this.authUser = authUser;
        this.userService = userService;
        this.logService = logService;

        /**
         * Check if an authenticated user is present
         * if not, return to login page.
         */
        authUser.get().ifPresentOrElse(
                user -> {
                    // Logic to execute when user is logged in
                    H2 welcomeHeader = new H2("Welcome " + user.getName());
                    welcomeHeader.addClassName(Margin.Bottom.LARGE);


                    Panel recentPatients = createRecentPatientPanel();

                    Panel recentSysLog = createSysLogPanel();

                    HorizontalLayout panelsLayout = new HorizontalLayout();
                    if (userService.isAdmin()) { //Which user?
                        panelsLayout.add(recentPatients, recentSysLog);
                    }
                    else {
                        panelsLayout.add(recentPatients);
                    }
                    panelsLayout.setSizeFull();
                    panelsLayout.expand(recentPatients, recentSysLog);

                    add(welcomeHeader, panelsLayout);
                },
                () -> {
                    // Logic to execute when no user is logged in
                    UI.getCurrent().navigate(LoginView.class);
                }
        );

        setSizeFull();
    }

    /**
     * creating a grid for recent patients, displayed in neat format
     *
     * @return recent patient panel
     */
    private Panel createRecentPatientPanel() {
        List<Patient> patients = patientService.getPatients();
        Grid<Patient> patientGrid = new Grid<>(Patient.class);
        patientGrid.setItems(patients);
        patientGrid.setColumns("firstName","lastName","healthNumber","dateOfBirth");
        patientGrid.getColumnByKey("dateOfBirth").setHeader("Date of Birth");


        return new Panel("Recently added patients", null, patientGrid);
    }

    /**
     * Create a grid for system logs, display in neat panel format.
     *
     * @return syslog panel
     */
    private Panel createSysLogPanel() {
        List<Log> logs = logService.getRecentLogs();

        Grid<Log> logGrid = new Grid<>(Log.class);
        logGrid.setItems(logs);
        logGrid.setColumns("timeAccessed", "operation");
        VerticalLayout logsContent = new VerticalLayout();
        logsContent.add(new Span("System started"));
        logsContent.add(new Span("New patient record added."));

        return new Panel("Recent System Logs", null, logGrid);
    }

}
