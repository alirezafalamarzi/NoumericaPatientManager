package com.noumerica.noumericahealth.views.patient;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Patient;
import com.noumerica.noumericahealth.views.MainLayout;
import com.noumerica.noumericahealth.views.customelement.Panel;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

@PageTitle("Image Gallery")
@Route(value = "image-gallery", layout = MainLayout.class)
@AnonymousAllowed
public class GalleryPage extends Div {


    private PatientView parent;
    private OrderedList imageContainer;
    private ScanView scanView;
    private VerticalLayout galleryLayout;

    private ArrayList<ImageWidget> imageWidgets;

    private VerticalLayout GroupButtonLayout;

    private static ImageWidget currentImage;

    private Patient patient;

    public GalleryPage(PatientView parent, Patient patient) {
        this.parent = parent;
        this.patient = patient;
        constructUI();
    }

    public void setParent(PatientView parent) {
        this.parent = parent;
    }

    private void constructUI() {
        addClassNames("image-gallery-view");
//        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2(patient.getFirstName());


        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);

        Button backButton = new Button("Back");
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.addClickListener(event -> parent.showSearchView());

        headerContainer.add(header);
        Panel infoPanel = new Panel("Info", null, headerContainer);


        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Alphabetical", "Newest first", "Oldest first");
        sortBy.setValue("Alphabetical");

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);


        imageWidgets = new ArrayList<>();

        List<ImageGroup> imageGroups = parent.getImageGroupService().getImageGroupByPatientId(patient.getId());
        for(ImageGroup imageGroup: imageGroups) {
            imageWidgets.add(new ImageWidget(
                    "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
                    "Image Group 1", "Unsplash Photo: Snow mountains under stars", imageGroup));
        }


        Div imageContainerDiv = new Div();
        imageContainerDiv.add(imageContainer);
        imageContainerDiv.setHeight("450px");
        imageContainerDiv.setMaxWidth("100%");

        Panel imageContainerPanel = new Panel("Image Groups", null, imageContainerDiv);
        imageContainerPanel.setMaxHeight("50%");
        for(int i = 0; i < imageWidgets.size(); i++) {
            imageContainer.add(imageWidgets.get(i));
            imageWidgets.get(i).addClickListener((event) -> {
                scanView = new ScanView(parent.getImageGroupService(), parent.getNoteService(), ((ImageWidget)event.getSource()).getImageGroup());
                scanView.setParentView(this);
                this.remove(galleryLayout);
                this.remove(GroupButtonLayout);
                this.add(scanView);
            });
        }
        Button uploadContainer = getGroupButton();
        container.add(sortBy, uploadContainer);
        galleryLayout = new VerticalLayout();
        galleryLayout.setHeight("100%");
        galleryLayout.setMaxWidth("100%");
        galleryLayout.getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");
        galleryLayout.add(backButton, infoPanel, container, imageContainerPanel);
        add(galleryLayout);
    }

    public static void setImageWidget(ImageWidget imageWidget) {
        currentImage = imageWidget;
    }

    public static ImageWidget getImageWidget() {
        return currentImage;
    }

    protected void goToGalleryView() {
        this.remove(scanView);
        this.add(galleryLayout);
    }

    private Button getGroupButton() {
        AtomicBoolean check = new AtomicBoolean(false);
        Button addGroupButton = new Button("Add Image Group");
        addGroupButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        TextField url = new TextField("Url");
        TextField title = new TextField("Title");
        TextField desc = new TextField("Description");
        Paragraph info = new Paragraph("https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80");

        FormLayout formLayout = new FormLayout(url, title, desc);
        formLayout.setResponsiveSteps(new ResponsiveStep("0", 2));
        formLayout.setColspan(url, 2);

        Button submitGroup = new Button("Submit");
        submitGroup.addClickListener(clickEvent -> {
            ImageWidget newImageWidget = new ImageWidget(url.getValue(), title.getValue(), desc.getValue(), new ImageGroup());
            imageWidgets.add(newImageWidget);
            imageContainer.add(newImageWidget);
            newImageWidget.addClickListener(event -> {
                this.remove(galleryLayout);
                this.remove(GroupButtonLayout);
                this.remove(info);
                this.add(scanView);
            });
            this.remove(GroupButtonLayout);
            this.remove(info);
            this.add(galleryLayout);
            check.set(false);
        });
        submitGroup.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelGroup = new Button("Cancel");
        cancelGroup.addClickListener(clickEvent2 -> {
            this.remove(GroupButtonLayout);
            this.remove(info);
            this.add(galleryLayout);
            check.set(false);
        });

        HorizontalLayout submitCancel = new HorizontalLayout(submitGroup, cancelGroup);
        GroupButtonLayout = new VerticalLayout(submitCancel, formLayout);

        addGroupButton.addClickListener(clickEvent3 -> {
            if (!check.get()) {
                this.remove(galleryLayout);
                this.add(GroupButtonLayout, info);
                check.set(true);
            }
        });
        return addGroupButton;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

