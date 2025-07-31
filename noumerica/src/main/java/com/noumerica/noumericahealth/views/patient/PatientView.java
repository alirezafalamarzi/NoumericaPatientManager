package com.noumerica.noumericahealth.views.patient;

import com.noumerica.noumericahealth.data.Operation;
import com.noumerica.noumericahealth.data.Patient;
import com.noumerica.noumericahealth.security.AuthenticatedUser;
import com.noumerica.noumericahealth.services.LogService;
import com.noumerica.noumericahealth.services.PatientService;
import com.noumerica.noumericahealth.services.ImageGroupService;
import com.noumerica.noumericahealth.services.NoteService;
import com.noumerica.noumericahealth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@PageTitle("Patients")
@Route(value = "patients", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
/**
 * PatientView class, creates a formlayout, and if patient is valid, log this action
 * for user and save patient to database.
 * @param authUser
 * @param logService
 * @param ps
 */
public class PatientView extends Div {

    private final Component searchView;
    private GalleryPage galleryView;

    private final LogService logService;

    private final Grid<Patient> dataGrid = new Grid<Patient>(Patient.class);
    private final Dialog patientFormDialog = new Dialog();
    private final PatientService patientService;

    private final ImageGroupService imageGroupService;
    private final NoteService noteService;

    @Autowired
    public PatientView(AuthenticatedUser authUser,
                LogService logService, PatientService ps,
                ImageGroupService imageGroupService, NoteService noteService) {
        this.logService = logService;
        this.patientService = ps;
        this.imageGroupService = imageGroupService;
        this.noteService = noteService;
        this.searchView = createSearchView();
//        this.galleryView = createGalleryView();
        this.add(searchView);
        patientFormDialog.add(patientForm(new Patient()));
    }

    public ImageGroupService getImageGroupService() {
        return this.imageGroupService;
    }

    public NoteService getNoteService() {
        return this.noteService;
    }

    public PatientService getPatientService() {
        return this.patientService;
    }


    public void showGalleryView() {
        this.remove(searchView);
        this.add(galleryView);
    }

    private Component createSearchView() {
        updateList();
        VerticalLayout rootLayout = new VerticalLayout();

        RadioButtonGroup<String> searchByRadioGroup = new RadioButtonGroup<String>();
        searchByRadioGroup.setLabel("Search by");
        TextField searchTextField = new TextField();
        searchTextField.setPlaceholder("Search");
        searchTextField.setWidthFull();

        searchByRadioGroup.setItems("Name", "Health Number", "Date Accessed");
        searchByRadioGroup.addValueChangeListener(event -> {
            searchTextField.setPlaceholder("Search " + searchByRadioGroup.getValue());
        });
        searchByRadioGroup.setValue("Name");

        // set buttons
        Button advanceSearchButton = new Button("Advanced Search");
        advanceSearchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Create Add, Edit, and Remove buttons
        Button addPatientButton = new Button("Add Patient", event -> {
            openPatientForm(new Patient());
        });
        addPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button editPatientButton = new Button("Edit Patient", event -> {
            Patient selectedPatient = dataGrid.asSingleSelect().getValue(); // highlighted patient options
            if (selectedPatient != null) {
                showEditConfirmation(selectedPatient);
            }
        });
        editPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button removePatientButton = new Button("Remove Patient", event -> {
            Patient selectedPatient = dataGrid.asSingleSelect().getValue();
            if (selectedPatient != null) {
                showRemoveConfirmation(selectedPatient);
            }
        });
        removePatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // initially invisible.
        editPatientButton.setEnabled(false);
        removePatientButton.setEnabled(false);
        // Group buttons in a HorizontalLayout
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(addPatientButton, editPatientButton, removePatientButton);


        advanceSearchButton.setText("Advanced Search");
        advanceSearchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        rootLayout.add(searchByRadioGroup, searchTextField, advanceSearchButton, buttonLayout);
        rootLayout.setWidthFull();



        dataGrid.addItemDoubleClickListener(event -> {
            if(event.getItem() != null) {
                this.galleryView = this.createGalleryView(event.getItem());
            }
            this.showGalleryView();
        });


        // if a patient is selected, show edit/remove buttons.
        dataGrid.asSingleSelect().addValueChangeListener(event -> {
            boolean patientSelected = event.getValue() != null;
            editPatientButton.setEnabled(patientSelected);
            removePatientButton.setEnabled(patientSelected);
        });

        rootLayout.add(dataGrid);

        // go to gallery view if clicked on patient
        advanceSearchButton.addClickListener(event -> {
            if(rootLayout.isVisible()) {
                rootLayout.setVisible(false);
                this.galleryView.setVisible(true);
            }
            else {
                rootLayout.setVisible(true);
                this.galleryView.setVisible(false);
            }
        });
        return rootLayout;
    }


    private GalleryPage createGalleryView(Patient patient) {
        return new GalleryPage(this, patient);
    }

    public void showSearchView() {
        this.remove(galleryView);
        this.add(searchView);
    }

    /**
     * remove confirmation layout
     * @param patient to be confirmed into system
     */
    private void showRemoveConfirmation(Patient patient) {
        Dialog confirmationDialog = new Dialog();
        confirmationDialog.add(new Text("Are you sure you want to remove this patient?"));

        Button confirmButton = new Button("Remove", event -> {
            removePatient(patient);
            confirmationDialog.close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button cancelButton = new Button("Cancel", event -> confirmationDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmButton, cancelButton);
        confirmationDialog.add(buttonLayout);
        confirmationDialog.open();
    }

    /**
     * Edit confirmation layout
     * @param patient patient to be confirmed in to system
     */
    private void showEditConfirmation(Patient patient) {
        Dialog confirmationDialog = new Dialog();
        confirmationDialog.add(new Text("Are you sure you want to edit this patient?"));

        Button confirmButton = new Button("Edit", event -> {
            openPatientForm(patient);
            confirmationDialog.close();
        });

        Button cancelButton = new Button("Cancel", event -> confirmationDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmButton, cancelButton);
        confirmationDialog.add(buttonLayout);
        confirmationDialog.open();
    }

    /**
     * opens patient form, edit if existing patient,
     * create new if not.
     * @param patient patient to be edited or created.
     */
    private void openPatientForm(Patient patient) {
        patientFormDialog.removeAll();
        if (patient != null) {
            patientFormDialog.add(patientForm(patient));
        }
        else {
            patientFormDialog.add(patientForm(new Patient()));
        }
        patientFormDialog.open();
    }

    /**
     * Check if patient being removed exists, if so,
     * then remove from database.
     */
    private void removePatient(Patient patient) {
        if (patient != null) {
            patientService.delete(patient.getId());
            updateList();
            logService.logOperation(patient, Operation.REMOVE_PATIENT);
            Notification.show("Patient removed.");
        }
        else {
            Notification.show("No patient selected.");
        }
    }

    /**
     * A patient form that is opened when a user
     * wants to add a new patient. Returns a form layout
     * @return FormLayout
     */
    private FormLayout patientForm(Patient patient) {
        // Create the fields
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField healthNumberField = new TextField("Health Number");
        DatePicker dateOfBirthField = new DatePicker("Date of Birth");

        if (patient != null) {
            if (patient.getFirstName() != null) firstNameField.setValue(patient.getFirstName());
            if (patient.getLastName() != null) lastNameField.setValue(patient.getLastName());
            if (patient.getHealthNumber() != null) healthNumberField.setValue(patient.getHealthNumber());
            if (patient.getDateOfBirth() != null) dateOfBirthField.setValue(patient.getDateOfBirth());
        }

        // Create the 'Save' button
        Button saveButton = new Button("Save", event -> {
            // Get values from the fields
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String healthNumber = healthNumberField.getValue();
            LocalDate dateOfBirth = dateOfBirthField.getValue();

            // Manually validate the input values
            if (validateInput(firstName, lastName, healthNumber, dateOfBirth)) {
                // Create new Patient instance
                Patient newPatient = new Patient();
                newPatient.setFirstName(firstName);
                newPatient.setLastName(lastName);
                newPatient.setHealthNumber(healthNumber);
                newPatient.setDateOfBirth(dateOfBirth);

                // Save the patient
                savePatient(newPatient);
            } else {
                Notification.show("Please correct the errors before saving.");
            }
        });

        Button cancelButton = new Button("Cancel", event -> {
            patientFormDialog.close(); // close our dialog if cancelled
        });

        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        // display horizontally
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        // Create the form layout and add components
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstNameField, lastNameField, healthNumberField, dateOfBirthField, buttonLayout);

        return formLayout;
    }

    /**
     * Validation method for FormLayout, check if any labels are invalid.
     * @param firstName First name patient
     * @param lastName Last name
     * @param healthNumber health num
     * @param dateOfBirth birthdate
     * @return bool isValid
     */
    private boolean validateInput(String firstName, String lastName, String healthNumber, LocalDate dateOfBirth) {
        boolean isValid = true;
        String errorMessage = "";

        if (firstName == null || firstName.trim().isEmpty()) {
            errorMessage += "First name required.\n";
            isValid = false;
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errorMessage += "Last Name is required.\n";
            isValid = false;
        }

        if (healthNumber == null || healthNumber.trim().isEmpty()) {
            errorMessage += "Health Number is required.\n";
            isValid = false;
        }

        if (dateOfBirth == null) {
            errorMessage += "Date of Birth is required.\n";
            isValid = false;
        }
        else {
            LocalDate today = LocalDate.now();
            if (dateOfBirth.isAfter(today) || dateOfBirth.isBefore(today.minusYears(120))) {
                errorMessage += "Date of Birth is not valid.\n";
                isValid = false;
            }
        }

        if (!isValid) {
            Notification.show(errorMessage, 5000, Notification.Position.MIDDLE);
        }

        return isValid;
    }


    /**
     * Save a patient in repo and log this action.
     * @param patient patient to be saved.
     */
    private void savePatient(Patient patient) {
        try {
            Patient existingPatient = dataGrid.asSingleSelect().getValue();
            if (existingPatient != null) {
                patient.setId(existingPatient.getId());
            }
            patientService.update(patient);
            Notification.show("Patient saved successfully.");
            patientFormDialog.close();
            updateList();

            // Log this action:
            // Was it an add or an edit?
            Operation operation = (patient.getId() == null) ? Operation.ADD_PATIENT : Operation.EDIT_PATIENT_INFO;
            logService.logOperation(patient, operation);
        } catch (Exception e) {
            Notification.show("An error occurred while saving the patient.");
            e.printStackTrace();
        }
    }





    private void updateList() {
        dataGrid.setItems(patientService.list(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
    }

}
