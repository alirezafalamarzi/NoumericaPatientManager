package com.noumerica.noumericahealth.views.patient;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import java.sql.Timestamp;
import java.util.Date;

public class ImageWidget extends ListItem {
    protected Timestamp timeStamp;
    private ImageGroup imageGroup;

    public ImageWidget(String url, String title, String desc, ImageGroup imageGroup) {
        this.imageGroup = imageGroup;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);

        div.add(image);


        Span spanTitle = new Span();
        spanTitle.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        spanTitle.setText(title);

        Span dateAdded = new Span();
        Date date = new Date();
        timeStamp = new Timestamp(date.getTime());
        dateAdded.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        dateAdded.setText(timeStamp.toString());

        Paragraph description = new Paragraph(desc);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Open Image Group");

        add(div, spanTitle, dateAdded, description, badge);

    }

    public ImageGroup getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(ImageGroup imageGroup) {
        this.imageGroup = imageGroup;
    }
}
