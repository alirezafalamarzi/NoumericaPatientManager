package com.noumerica.noumericahealth.views.customelement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class Panel extends VerticalLayout {
    public Panel(String titleText, List<Button> buttons, Component content) {
        this.setClassName("custom-panel");
        HorizontalLayout panelHeader = new HorizontalLayout();
        Scroller panelContent = new Scroller();
        panelContent.setWidthFull();
        panelHeader.setWidthFull();
        panelHeader.setClassName("panel-header");
        panelContent.setClassName("panel-content");

        H5 headerText = new H5(titleText);
        headerText.setClassName("panel-header-text");
        panelHeader.add(headerText);

        if(buttons != null) {
            for (Button b : buttons) {
                panelHeader.add(b);
//                b.getStyle().set("height", "25px");
//                b.getStyle().set("width", "25px");
            }
        }
        panelHeader.expand(headerText);
        panelContent.setContent(content);
        add(panelHeader);
        add(panelContent);
    }
}
