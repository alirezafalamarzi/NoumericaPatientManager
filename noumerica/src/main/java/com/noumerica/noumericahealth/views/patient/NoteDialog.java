package com.noumerica.noumericahealth.views.patient;

import com.noumerica.noumericahealth.data.Note;
import com.noumerica.noumericahealth.services.ImageGroupService;
import com.noumerica.noumericahealth.services.NoteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.*;


public class NoteDialog extends Dialog {
	private ImageGroupService imageGroupService;
	private TextField subject;
	private TextArea content;
	private Button cancelButton;
	private Button saveButton;
	private NoteService noteService;

	public NoteDialog(NoteService noteService, ImageGroupService imageGroupService) {
		this.imageGroupService = imageGroupService;
		this.noteService = noteService;

//		createWidgets();



		this.getElement().setAttribute("aria-label", "Add note");

		this.getHeader().add(createHeaderLayout());
		createFooter();

		VerticalLayout dialogLayout = createDialogLayout();
		this.add(dialogLayout);
		this.setModal(false);
		this.setDraggable(true);
		addButtonOperations();
	}

	private static H2 createHeaderLayout() {
		H2 headline = new H2("Add note");
		headline.addClassName("draggable");
		headline.getStyle().set("margin", "0").set("font-size", "1.5em")
				.set("font-weight", "bold").set("cursor", "move")
				.set("padding", "var(--lumo-space-m) 0").set("flex", "1");

		return headline;
	}

	private VerticalLayout createDialogLayout() {

		this.subject = new TextField("Title");
		this.content = new TextArea("Description");
		VerticalLayout fieldLayout = new VerticalLayout(this.subject, this.content);
		fieldLayout.setSpacing(false);
		fieldLayout.setPadding(false);
		fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		fieldLayout.getStyle().set("width", "500px").set("max-width", "100%");

		return fieldLayout;
	}

	private void createFooter() {
		this.cancelButton = new Button("Cancel");
		this.saveButton = new Button("Add note");
		this.saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		this.getFooter().add(cancelButton);
		this.getFooter().add(saveButton);
	}


//	public void createWidgets() {
//		this.setHeaderTitle("New Note");
//		VerticalLayout dialogLayout = new VerticalLayout();
//		dialogLayout.setWidth("300px");
//		this.subject = new TextField("Subject");
//		this.content = new TextArea("Content");
//		dialogLayout.add(subject);
//		dialogLayout.add(content);
//		add(dialogLayout);
//		this.cancelButton = new Button("Cancel");
//		this.saveButton = new Button("Save");
//		this.saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		this.getFooter().add(this.cancelButton);
//		this.getFooter().add(this.saveButton);
//	}



	public void addButtonOperations() {
		this.saveButton.addClickListener(event -> {
			if(this.content != null && this.content.getValue() != "" && this.subject != null && this.subject.getValue() != "") {
				Note note = new Note();
				note.setImageGroup(imageGroupService.getImageGroupById(2));
				note.setContent(this.content.getValue());
				note.setSubject(this.subject.getValue());
				noteService.update(note);
				this.close();
			}
		});

		this.cancelButton.addClickListener(event -> {
			this.close();
		});
	}

}
