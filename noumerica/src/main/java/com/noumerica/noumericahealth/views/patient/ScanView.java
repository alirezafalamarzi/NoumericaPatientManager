package com.noumerica.noumericahealth.views.patient;

import com.noumerica.noumericahealth.data.Doctor;
import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Note;
import com.noumerica.noumericahealth.services.ImageGroupService;
import com.noumerica.noumericahealth.services.NoteService;
import com.noumerica.noumericahealth.views.MainLayout;
import com.noumerica.noumericahealth.views.customelement.Panel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Scan")
@Route(value = "scan", layout = MainLayout.class)
@AnonymousAllowed
public class ScanView extends VerticalLayout {

    private ImageGroupService imageGroupService;
    private NoteService noteService;

    private ImageGroup imageGroup;

    private VerticalLayout noteContainer;


    private GalleryPage parentView;
    public ScanView(ImageGroupService imageGroupService, NoteService noteService, ImageGroup imageGroup) {
        this.imageGroupService = imageGroupService;
        this.noteService = noteService;
        this.imageGroup = imageGroup;
        this.setHeightFull();
        this.setWidthFull();
        HorizontalLayout top = new HorizontalLayout();
        Panel infoPanel = new Panel("Info", null, createInfoContent());
        Panel notesPanel =  createNotesContent();
        Panel imagesPanel = new Panel("Images", null, createImagesContent());
        top.add(infoPanel);
        top.add(notesPanel);
        top.setWidthFull();
        Button backButton = new Button("Back");
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.addClickListener(event -> {
            parentView.goToGalleryView();
        });
        this.add(backButton);
        this.add(top);
        this.add(imagesPanel);
    }

    public void setParentView(GalleryPage parent) {
        parentView = parent;
    }
    public Component getParentView() {
        return parentView;
    }
    private Component createInfoContent() {
        VerticalLayout layout = new VerticalLayout();
        ImageGroup ig = imageGroupService.getImageGroupById(imageGroup.getId());
        H1 title = new H1(ig.getTitle());
        H1 description = new H1(ig.getDescription());
        H1 number = new H1(String.valueOf(ig.getNumberOfImages()));

        layout.add(title, description, number);
        return layout;
    }

    /** NOTES PANEL STARTS
     Create the panel that holds the notes for an image group of a patient **/
    private Panel createNotesContent() {
        // A vertical list of notes
        noteContainer = new VerticalLayout();

        Div noteContainerDiv = new Div();
        noteContainerDiv.add(noteContainer);
        noteContainerDiv.setHeight("150px");
//        noteContainerDiv.setMaxWidth("100%");

        // Button to add new notes
        Button addNote = new Button(VaadinIcon.PLUS.create());
        addNote.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addNote.addClickListener(deleteEvent -> createNote());
//        noteContainer.add(addNote);

        // A list of notes to add to the vertical layout
        List<Note> notes = imageGroupService.getNotes(imageGroup.getId());
        noteContainer.addClassNames(LumoUtility.Gap.XSMALL, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.MEDIUM, LumoUtility.Padding.NONE);
//        noteContainer.setWidthFull();
        for(Note n: notes) {
            addNoteAndDelete(n);
        }
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(addNote);
//        Button remove = new Button(VaadinIcon.TRASH.create());

//        buttons.add(remove);
        return new Panel("Notes", buttons, noteContainer);
    }

    /** View the content of a note with the ability to edit it **/
    private void viewNote(Note note, HorizontalLayout noteAndDelete) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("View Note");
        dialog.setHeight("50%");
        dialog.setWidth("50%");

        // Layout for new note text fields
        VerticalLayout dialogLayout = new VerticalLayout();

        TextField subjectField = new TextField("Subject");
        subjectField.setWidthFull();
        subjectField.setValue(note.getSubject());
        TextArea contentField = new TextArea("Content");
        contentField.setWidthFull();
        contentField.setHeightFull();
        contentField.setValue(note.getContent());

        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.setWidthFull();
        dialogLayout.setHeightFull();
        dialogLayout.add(subjectField, contentField);

        dialog.add(dialogLayout);

        // Button to save the edited note
        // Adds the new note to the list
        Button saveButton = new Button("Update");
        saveButton.addClickListener(deleteEvent -> {
            note.setSubject(subjectField.getValue());
            note.setContent(contentField.getValue());
            noteContainer.remove(noteAndDelete);
            noteService.update(note);
            addNoteAndDelete(note);
            dialog.close();
        });

        // Button to not save the edited note
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(deleteEvent -> dialog.close());

        // Adding the buttons
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.open();
    }

    /** Create a new note **/
    private void createNote() {
//        Note note = new Note(new Doctor(), "DO_NOT_USE", "DO_NOT_USE");
        Note note = new Note();
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Create Note");
        dialog.setHeight("50%");
        dialog.setWidth("50%");

        // Layout for new note text fields
        VerticalLayout dialogLayout = new VerticalLayout();

        TextField subjectField = new TextField("Subject");
        subjectField.setWidthFull();
        TextArea contentField = new TextArea("Content");
        contentField.setWidthFull();
        contentField.setHeightFull();

        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.setWidthFull();
        dialogLayout.setHeightFull();
        dialogLayout.add(subjectField, contentField);

        dialog.add(dialogLayout);

        // Button to add the new note to the list
        Button saveButton = new Button("Add");
        saveButton.addClickListener(deleteEvent -> {
            note.setSubject(subjectField.getValue());
            note.setContent(contentField.getValue());
            note.setImageGroup(imageGroup);
            noteService.update(note);
            addNoteAndDelete(note);
            dialog.close();
        });

        // Button to not add the new note to the lists
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(deleteEvent -> dialog.close());

        // Adding the buttons
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.open();
    }
    /** Add a new note to the list of notes **/
    private void addNoteAndDelete(Note note) {
        // Each note has a delete button
        HorizontalLayout noteAndDelete = new HorizontalLayout();
        noteAndDelete.setWidthFull();
        // Button that opens a dialogue showing the note

        Button noteSubject = new Button(note.getSubject());
        noteSubject.addClickListener(deleteEvent -> viewNote(note, noteAndDelete));
        noteSubject.addClassNames(LumoUtility.TextAlignment.LEFT);
//        noteSubject.setWidth("90%");
        // Button that deletes the associated note and itself from the vertical layout
        Button deleteNote = new Button(VaadinIcon.TRASH.create());
        deleteNote.addClickListener(deleteEvent -> {
            noteContainer.remove(noteAndDelete);
            noteService.delete(note.getId());
        });
//        deleteNote.setWidth("15%");
        noteAndDelete.add(noteSubject, deleteNote);
        noteAndDelete.setFlexGrow(4, noteSubject);
        noteAndDelete.setFlexGrow(1, deleteNote);
        noteContainer.add(noteAndDelete);
    }

    /** NOTES PANEL ENDS **/

    private Component createImagesContent() {
        VerticalLayout layout = new VerticalLayout();
        H2 images = new H2("Images Content");
        layout.add(images);
        return layout;
    }

}
