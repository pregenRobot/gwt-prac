package com.mycompany.mywebapp.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import org.eclipse.jdt.internal.compiler.lookup.SplitPackageBinding;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.thirdparty.guava.common.collect.Table;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ibm.icu.impl.CalendarAstronomer.Horizon;
import com.mycompany.mywebapp.client.GreetingServiceAsync;
import com.mycompany.mywebapp.shared.FieldVerifier;

public class Phase3 extends PhasePane{

    HorizontalPanel splitPlanel;
    VerticalPanel processPanel;

    VerticalPanel godViewPanel;
    DialogBox godViewLogs;

    TextBox proposalTextBox;
    TextBox delayTextBox;
    Button proposeButton;
    Button addDelayButton;

    TextBox finalResultTextBox;

    Button registerButton;

    public Phase3(GreetingServiceAsync greetingService){
        super("Phase 3 Paxos Synod Algorithm");

        
        splitPlanel = new HorizontalPanel();
        godViewPanel = new VerticalPanel();
        godViewLogs = new DialogBox();
        godViewLogs.setText("Logs");
        

        processPanel = new VerticalPanel();

        HorizontalPanel delayPanel = new HorizontalPanel();
        delayTextBox = new TextBox();
        addDelayButton = new Button("Add Delay");
        delayPanel.add(new HTML("<h4>Delay(s)</h4>"));
        delayPanel.add(delayTextBox);
        delayPanel.add(addDelayButton);

        HorizontalPanel proposePanel = new HorizontalPanel();
        proposalTextBox = new TextBox();
        proposeButton = new Button("Propose");
        proposePanel.add(new HTML("<h4>Proposal Message: </h4>"));
        proposePanel.add(proposalTextBox);
        proposePanel.add(proposeButton);

        HorizontalPanel finalResultPanel = new HorizontalPanel();
        finalResultTextBox = new TextBox();
        finalResultTextBox.setEnabled(false);
        finalResultPanel.add(new HTML("<h4>Final Result</h4>"));
        finalResultPanel.add(finalResultTextBox);

        registerButton = new Button("Register");

        godViewPanel.add(new HTML("<h4>God View </h4>"));
        godViewPanel.add(godViewLogs);
        processPanel.add(new HTML("<h4>Process Panel </h4>"));
        processPanel.add(registerButton);
        processPanel.add(finalResultPanel);
        processPanel.add(delayPanel);
        processPanel.add(proposePanel);


        splitPlanel.add(godViewPanel);
        splitPlanel.add(processPanel);
        
        super.mainPanel.add(splitPlanel);
    }
}
