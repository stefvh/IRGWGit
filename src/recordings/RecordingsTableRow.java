package recordings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Stef
 */
public class RecordingsTableRow {
    
    public RecordingsTableRow(RecordedGame recordedGame){
        this.recordedGame = recordedGame;
        this.name.set(recordedGame.getFileName());
        this.patch.set(recordedGame.getPatch());
        
    }
    
    private RecordedGame recordedGame;
    
    public RecordedGame getRecordedGame(){
        return recordedGame;
    }
    
    public void setRecordedGame(RecordedGame recordedGame){
        this.recordedGame = recordedGame;
    }
    
    private StringProperty name = new SimpleStringProperty();
    
    public String getName(){
        return name.get();
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    
    private StringProperty patch = new SimpleStringProperty();
    
    public String getPatch(){
        return patch.get();
    }
    
    public void setPatch(String patch){
        this.patch.set(patch);
    }

}
