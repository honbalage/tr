/**
  * Application.java
  */
package org.crf.tr.application;


import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public final class Application extends javafx.application.Application {
  
  public static final void main(final String[] args) {
    launch( args );
  }

  @Override
  public final void start(final Stage primary) {
    primary.setTitle( "Test Reporter" );

    final Button btn = new Button( );
    btn.setText( "Press Me!" );
    btn.setOnAction((final ActionEvent event) -> {
      System.out.println( "Hello, Successful World!!!" );
    });

    final StackPane root = new StackPane( );
    root.getChildren( ).add( btn );
    primary.setScene(new Scene( root, 310, 250 ));
    primary.show( );
  }
}

