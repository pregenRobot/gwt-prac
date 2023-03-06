package com.mycompany.mywebapp.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import java.io.PrintWriter;
import java.io.StringWriter;
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

public class Phase2 extends PhasePane {

  private static final String SERVER_ERROR =
      "An error occurred while " + "attempting to contact the server. Please check your network "
          + "connection and try again.";


  VerticalPanel logVPanel;
  Integer pid;
  Button registrationButton;
  boolean registered = false;


  private void log(String message, String color) {
    HTML logMessage = new HTML("<div style='color:" + color + ";'>" + message + " </div>");

    logVPanel.add(logMessage);
  }

  @Override
  protected void onUnload() {
    // TODO Auto-generated method stub
    super.onUnload();
    

  }

  public Phase2(TimeAndClockServiceAsync timeAndClock) {

    super("Phase 2 Message Passing System");


    final Button sendButton = new Button("Send");
    final Button receiveButton = new Button("Receive");
    registrationButton = new Button("Register");



    final TextBox messageTextBox = new TextBox();
    final HTML messageTextBoxLabel = new HTML("<h4>Message to send</h4>");
    messageTextBox.setText("Hello!");

    final TextBox processIdTextBox = new TextBox();
    final HTML processIdTextBoxLabel = new HTML("<h4>Target process ID</h4>");


    DialogBox clientLogDialogBox = new DialogBox();
    clientLogDialogBox.setText("Client Log");
    clientLogDialogBox.setAnimationEnabled(true);
    logVPanel = new VerticalPanel();
    clientLogDialogBox.add(logVPanel);

    final HorizontalPanel buttons = new HorizontalPanel();
    buttons.add(sendButton);
    buttons.add(receiveButton);



    mainPanel.add(registrationButton);
    mainPanel.add(messageTextBoxLabel);
    mainPanel.add(messageTextBox);
    mainPanel.add(processIdTextBoxLabel);
    mainPanel.add(processIdTextBox);
    mainPanel.add(buttons);
    mainPanel.add(clientLogDialogBox);


    registrationButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        timeAndClock.registration(new AsyncCallback<String>() {
          @Override
          public void onSuccess(String result) {
            log("Registered with the Server", "green");
            registrationButton.setText("My Pid: " + Integer.valueOf(result));
            registrationButton.setEnabled(false);
            registered = true;
            pid = Integer.valueOf(result);
          }

          @Override
          public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub
            log(caught.getMessage() + caught.getCause(), "red");
            pid = -1;
          }
        });
      }

    });

    receiveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        timeAndClock.fetchMyMessage(pid, new AsyncCallback<TACSMessage>() {

          @Override
          public void onFailure(Throwable caught) {
            log(caught.getMessage() + caught.getCause(), "red");
          }

          @Override
          public void onSuccess(TACSMessage result) {
            log(result.getLogMessage(), "black");
          }
        });
      }
    });

    sendButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if (!registered) {
          log("Please register with the server first by clicking the register button", "red");
          return;
        }


        TACSMessage tacsMessage = new TACSMessage();
        tacsMessage.message = messageTextBox.getText();
        tacsMessage.sourcePid = pid;
        Integer targetPid;

        try {
          targetPid = Integer.parseInt(processIdTextBox.getText());
          tacsMessage.targetPid = targetPid;

          long sendTime = System.currentTimeMillis() / 1000L;
          tacsMessage.sendTime = sendTime;
          timeAndClock.sendMessage(tacsMessage, new AsyncCallback<String>() {

            public void onFailure(Throwable caught) {

              log("Unable to send. Exception Raised: " + caught, "red");
            }

            public void onSuccess(String result) {

              log("Sent message at @" + sendTime, "green");
            }
          });
        } catch (Exception e) {
          log("Target Pid cannot be coersced into an Integer", "red");
        }
      }
    });
  }

}
