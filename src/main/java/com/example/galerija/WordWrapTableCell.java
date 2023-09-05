package com.example.galerija;

import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class WordWrapTableCell<S, T> extends TableCell<S, T> {

    private TextFlow textFlow;

    public WordWrapTableCell() {
        textFlow = new TextFlow();
        setGraphic(textFlow);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            Text text = new Text(String.valueOf(item));
            text.setWrappingWidth(getTableColumn().getWidth());
            textFlow.getChildren().setAll(text);
            setGraphic(textFlow);
        }
    }
}