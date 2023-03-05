package com.mycompany.mywebapp.client;

import com.mycompany.mywebapp.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class MyWebApp implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  /**
   * This is the entry point method.
   */



  public void onModuleLoad() {
    final Button sendButton = new Button("Send");
    sendButton.addStyleName("sendButton");

    final Button stopButton = new Button("Stop All");
    stopButton.getElement().setId("stopButton");

    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    nameField.setFocus(true);
    nameField.selectAll();

    final Label errorLabel = new Label();

    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("errorLabelContainer").add(errorLabel);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("stopButtonContainer").add(stopButton);

    final DialogBox sendDialogBox = new DialogBox();
    sendDialogBox.setText("Remote Procedure Call");
    sendDialogBox.setAnimationEnabled(true);
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    final Button sendCloseButton = new Button("Close");
    sendCloseButton.getElement().setId("closeButton");
    final Label sendTextToServerLabel = new Label();
    final HTML sendServerResponseLabel = new HTML();
    dialogVPanel.add(sendTextToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(sendServerResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(sendCloseButton);
    sendDialogBox.setWidget(dialogVPanel);

    final DialogBox stopDialogBox = new DialogBox();
    stopDialogBox.setText("Stop Remote Procedure Call");
    stopDialogBox.setAnimationEnabled(true);
    VerticalPanel stopVPanel = new VerticalPanel();
    stopVPanel.addStyleName("dialogVPanel");
    stopVPanel.add(new HTML("Server responded:"));
    final Button stopCloseButton = new Button("Close");
    stopCloseButton.getElement().setId("stopCloseButton");
    final HTML stopServerResponseLabel = new HTML();
    stopVPanel.add(stopServerResponseLabel);
    stopVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    stopVPanel.add(stopCloseButton);
    stopDialogBox.setWidget(stopVPanel);


    // Add a handler to close the DialogBox
    sendCloseButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        sendDialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    stopCloseButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event){
        stopDialogBox.hide();
        stopButton.setEnabled(true);
      }
    });



    class StopHandler implements ClickHandler, KeyUpHandler{
      private void tellServerToStopAllGreetings(){
        stopButton.setEnabled(false);
        greetingService.stopAllGreetings(new AsyncCallback<String>() {
          public void onFailure(Throwable caught){
            stopDialogBox.setText("Remote procedure call - Failure");
            stopServerResponseLabel.addStyleName("serverResponseLabelError");
            stopServerResponseLabel.setHTML(SERVER_ERROR);
            stopDialogBox.center();
            stopCloseButton.setFocus(true);
          }

          public void onSuccess(String result){
            sendDialogBox.setText("Remote Procedure Call");
            stopServerResponseLabel.removeStyleName("serverResponseLabelError");
            //stopServerResponseLabel.setHTML("STOPPED!");
            stopServerResponseLabel.setHTML(result);
            stopDialogBox.center();
            stopCloseButton.setFocus(true);
          }
        });
      }

      public void onClick(ClickEvent event){
        tellServerToStopAllGreetings();
      }

      public void onKeyUp(KeyUpEvent event){
        if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
          tellServerToStopAllGreetings();
        }
      }

    }

    // Create a handler for the sendButton and nameField
    class SendHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
        sendButton.setEnabled(false);
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */


      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        sendTextToServerLabel.setText(textToServer);
        sendServerResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            sendDialogBox.setText("Remote Procedure Call - Failure");
            sendServerResponseLabel.addStyleName("serverResponseLabelError");
            sendServerResponseLabel.setHTML(SERVER_ERROR);
            sendDialogBox.center();
            sendCloseButton.setFocus(true);
          }

          public void onSuccess(String result) {
            sendDialogBox.setText("Remote Procedure Call");
            sendServerResponseLabel.removeStyleName("serverResponseLabelError");
            sendServerResponseLabel.setHTML(result);
            sendDialogBox.center();
            sendCloseButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    SendHandler sendHandler = new SendHandler();
    StopHandler stopHandler = new StopHandler();
    sendButton.addClickHandler(sendHandler);
    stopButton.addClickHandler(stopHandler);
    nameField.addKeyUpHandler(sendHandler);
  }
}
