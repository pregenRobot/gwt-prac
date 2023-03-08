package com.mycompany.mywebapp.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.logging.Logger;

public abstract class PhasePane extends Composite{
    
    VerticalPanel mainPanel;
    String phaseTitle;
    Logger logger;
    

    PhasePane(String phaseTitle){
        VerticalPanel mainPanel = new VerticalPanel();
        this.mainPanel = mainPanel;
        initWidget(mainPanel);
        mainPanel.addStyleName("dialogVPanel");
        HTML titleElement = new HTML();
        this.phaseTitle = phaseTitle;
        titleElement.setHTML("<h1>" + this.phaseTitle + " Implementation</h1>");
        mainPanel.add(titleElement);
        this.logger = Logger.getLogger("MyWebApp");
    }
    
    @Override
    public int hashCode(){
        return phaseTitle.hashCode();
    }
}
