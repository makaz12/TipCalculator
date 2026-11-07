package com.example.tipcalculator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.tipcalculator.AppProperties.SCREEN_HEIGHT;
import static com.example.tipcalculator.AppProperties.SCREEN_WIDTH;

public class TipCalculator extends Application {
    byte perValue = 0;
    Label billTotal = new Label("Bill Amount:");
    TextField tfbillTotal = new TextField("");
    Label tipPercent = new Label("Tip Percent: " + perValue);
    Slider percent = new Slider(0,100,10);

    Label tipAmount = new Label("Tip amount:");
    TextField tfTipAmount = new TextField("");
    Label totalWithTip = new Label("Total With Tip");
    TextField tfTotalWithTip = new TextField("");
    Button calculateTotal = new Button("Calculate");
    @Override
    public void start(Stage primaryStage) throws IOException {
        GridPane pane = new GridPane(10, 10);

        Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);


        pane.setPadding(new Insets(10,10,10,10));
        pane.setAlignment(Pos.TOP_CENTER);
        pane.add(billTotal, 0, 0);
        pane.add(tfbillTotal, 1, 0);
        pane.add(tipPercent, 0, 1);
        pane.add(percent, 1, 1);
        pane.add(tipAmount, 0, 2);
        pane.add(tfTipAmount, 1, 2);
        pane.add(totalWithTip, 0, 3);
        pane.add(tfTotalWithTip, 1, 3);
        pane.add(calculateTotal, 0,4, 2, 1);

        tfbillTotal.setPromptText("0.00");
        tfTipAmount.setPromptText("0.00");
        tfTotalWithTip.setPromptText("0.00");
        calculateTotal.setMinWidth(300);
        percent.setShowTickLabels(true);
        percent.setShowTickMarks(true);
        tfTotalWithTip.setEditable(false);
        tfTotalWithTip.setFocusTraversable(false);
        tfTipAmount.setEditable(false);
        tfTipAmount.setFocusTraversable(false);


        class CalculateTotalHandler implements EventHandler<ActionEvent> {

            @Override
            public void handle(ActionEvent event) {
                double bill = 0.0;
                try {
                    bill = Double.parseDouble(tfbillTotal.getText());
                }catch (NumberFormatException e){
                    bill = 0;
                }
                double tipAmount = percent.getValue()/100*bill;
                tfTipAmount.setText(String.format("$%.2f", tipAmount));
                tfTotalWithTip.setText(String.format("$%.2f", tipAmount+bill));
            }
        }

        percent.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                perValue = (byte) percent.getValue();
            }
        });

        calculateTotal.setOnAction(new CalculateTotalHandler());


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
