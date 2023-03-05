package com.mycompany.mywebapp.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class PhasePane extends Composite{
    
    VerticalPanel mainPanel;
    String phaseTitle;
    

    PhasePane(String phaseTitle){
        VerticalPanel mainPanel = new VerticalPanel();
        this.mainPanel = mainPanel;
        initWidget(mainPanel);
        mainPanel.addStyleName("dialogVPanel");
        HTML titleElement = new HTML();
        this.phaseTitle = phaseTitle;
        titleElement.setHTML("<h1>" + this.phaseTitle + " Implementation</h1>");
        mainPanel.add(titleElement);
    }
    
    @Override
    public int hashCode(){
        return phaseTitle.hashCode();
    }
}
