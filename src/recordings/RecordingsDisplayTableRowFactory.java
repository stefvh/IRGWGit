/**
 * ************************************
 ** Stef - UGent - Informatica - 2015 **
**************************************
 */
package recordings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Stef
 */

public class RecordingsDisplayTableRowFactory<S> implements Callback<TableView<S>, TableRow<S>>  {

    private EventHandler<ActionEvent> actionEventHandler;

    public void setOnAction(EventHandler<ActionEvent> actionEventHandler) {
        this.actionEventHandler = actionEventHandler;
    }
    
    @Override
    public final TableRow<S> call(TableView<S> p) {
        return createTableRow(p);
    }
    
    protected TableRow<S> createTableRow(TableView<S> column) {
        return new  RecordingsDisplayTableRow<> ();
    }

    private class RecordingsDisplayTableRow<S> extends TableRow<S>
            implements EventHandler<MouseEvent> {

        public RecordingsDisplayTableRow() {
            setOnMouseClicked(this);
        }

        @Override
        public void handle(MouseEvent t) {
            if (t.getClickCount() > 1) {
                ActionEvent ae = new ActionEvent(this, null);
                actionEventHandler.handle(ae);
            }
        }
    }
}
