package com.mycompany.mywebapp.client;

import com.mycompany.mywebapp.client.widgets.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class MyWebApp implements EntryPoint {
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  PhasePane currPane;
  Button currPhaseButton;

  HashMap<PhasePane,Button> phasepaneButtonmap = new HashMap<>();

  class ButtonClickToPaneSwitchHandler implements ClickHandler{

    @Override
    public void onClick(ClickEvent event) {
      
      for(Map.Entry<PhasePane,Button> entry: phasepaneButtonmap.entrySet()){
        RootPanel switcher = RootPanel.get("switcher");
        RootPanel contentArea = RootPanel.get("contentArea");

        Button clickedButton = (Button)event.getSource();
        if(clickedButton.equals(entry.getValue())){
          switcher.remove(entry.getValue());
          contentArea.remove(currPane);
          switcher.add(currPhaseButton);
          contentArea.add(entry.getKey());

          currPane = entry.getKey();
          currPhaseButton = entry.getValue();
        }
      }
    }

  }


  public void onModuleLoad() {
    

    Button phase1Button = new Button("Phase 1");
    phase1Button.addStyleName("switcherButton");
    Button phase2Button = new Button("Phase 2");
    phase2Button.addStyleName("switcherButton");
    Button phase3Button = new Button("Phase 3");
    phase3Button.addStyleName("switcherButton");

    RootPanel.get("switcher").add(phase1Button);
    RootPanel.get("switcher").add(phase2Button);
    RootPanel.get("switcher").add(phase3Button);

    PhasePane phase1Widget = new Phase1(greetingService);
    PhasePane phase2Widget = new Phase2(greetingService);
    PhasePane phase3Widget = new Phase3(greetingService);


    phasepaneButtonmap.put(phase1Widget,phase1Button);
    phasepaneButtonmap.put(phase2Widget,phase2Button);
    phasepaneButtonmap.put(phase3Widget,phase3Button);


    currPane = phase1Widget;
    currPhaseButton = phasepaneButtonmap.get(currPane);
    RootPanel.get("contentArea").add(currPane);
    RootPanel.get("switcher").remove(currPhaseButton);

    phase1Button.addClickHandler(new ButtonClickToPaneSwitchHandler());
    phase2Button.addClickHandler(new ButtonClickToPaneSwitchHandler());
    phase3Button.addClickHandler(new ButtonClickToPaneSwitchHandler());
  }
}
