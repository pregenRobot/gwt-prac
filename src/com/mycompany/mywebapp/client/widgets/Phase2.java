package com.mycompany.mywebapp.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mycompany.mywebapp.client.GreetingServiceAsync;
import com.mycompany.mywebapp.client.TimeAndClockServiceAsync;
import com.mycompany.mywebapp.shared.FieldVerifier;
import com.mycompany.mywebapp.shared.TACSMessage;

public class Phase2 extends PhasePane{

  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";


    DialogBox clientLogDialogBox;

    private void log(String message,String color){
      HTML logMessage = new HTML("<div style='"+color+";'>" + message + " </div>");
      
      clientLogDialogBox.add(logMessage);
    }

    public Phase2(TimeAndClockServiceAsync timeAndClock){

        super("Phase 2 Message Passing System");


        final Button sendButton = new Button("Send");
        final Button receiveButton = new Button("Receive");

        int pid = 10;

        final HTML myProcessIdDisplayer =  new HTML("<h3>My process ID is: " + pid +  "<h3>");

        final TextBox messageTextBox = new TextBox();
        final HTML messageTextBoxLabel = new HTML("<h4>Message to send</h4>");
        messageTextBox.setText("Hello!");

        final TextBox processIdTextBox = new TextBox();
        final HTML processIdTextBoxLabel = new HTML("<h4>Target process ID</h4>");


        clientLogDialogBox = new DialogBox();
        clientLogDialogBox.setText("Client Log");
        clientLogDialogBox.setAnimationEnabled(true);
        VerticalPanel dialogVPanel = new VerticalPanel();
        clientLogDialogBox.add(dialogVPanel);

        final HorizontalPanel buttons = new HorizontalPanel();
        buttons.add(sendButton);
        buttons.add(receiveButton);


        mainPanel.add(myProcessIdDisplayer);
        mainPanel.add(messageTextBoxLabel);
        mainPanel.add(messageTextBox);
        mainPanel.add(processIdTextBoxLabel);
        mainPanel.add(processIdTextBox);
        mainPanel.add(buttons);
        mainPanel.add(clientLogDialogBox);


        receiveButton.addClickHandler(new ClickHandler(){

          @Override
          public void onClick(ClickEvent event){
            //pass
          }

        });

        sendButton.addClickHandler(new ClickHandler(){

          @Override
          public void onClick(ClickEvent event){
            TACSMessage tacsMessage = new TACSMessage();
            tacsMessage.message = messageTextBox.getText();
            tacsMessage.sourcePid = pid;

            Integer targetPid;

            try{
              targetPid = Integer.parseInt(processIdTextBox.getText());
            }catch(Exception e){
              log("Target Pid cannot be coersced into an Integer","red");
            }
          }
        });
    }

}
