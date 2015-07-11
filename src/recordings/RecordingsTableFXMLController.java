package recordings;

import exceptions.ExceptionGenerateRecordedGamesDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.MainFXMLController;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class RecordingsTableFXMLController implements EventHandler<ActionEvent>{
    
    public TableView<RecordingsTableRow> tableView;
    
    public TableColumn<RecordingsTableRow,String> nameColumn;
    
    public TableColumn<RecordingsTableRow,String> patchColumn;

    private MainFXMLController correspond;
    
    private List<File> files;
    
    private List<RecordedGame> recordings = new ArrayList<>();
    
    public void initialize(){
        tableView.setPlaceholder(new Label("Uploading ..."));
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patchColumn.setCellValueFactory(new PropertyValueFactory<>("patch"));
        
        RecordingsDisplayTableRowFactory<RecordingsTableRow> rdtrf =
                new RecordingsDisplayTableRowFactory<>();
        rdtrf.setOnAction(this);
        tableView.setRowFactory(rdtrf);
    }
    
    
    public void setCorrespond(MainFXMLController correspond) {
        this.correspond = correspond;
    }
    
    public void setFiles(List<File> files){
        this.files = files;
    }
    
    public void start(){
        List<GenerateRecordedGame> generating = new ArrayList<>();
        for(File file : files){
            generating.add(new GenerateRecordedGame(file));
        }
        new GenerateRecordingsTable(generating);
    }

    @Override
    public void handle(ActionEvent t) {
        TableRow tr = (TableRow) t.getSource();
        displayRecordedGame(tableView.getItems().get(tr.getIndex()).getRecordedGame());
    }
    
    private void displayRecordedGame(RecordedGame rec){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(rec.getFileName());
        alert.setHeaderText(rec.getFileName());
        
        Map<String, List<Player>> map = new HashMap<>();
        for(Player p : rec.getPlayers()){
            if(!map.containsKey(p.getTeam())){
                map.put(p.getTeam(), new ArrayList<>());
            }
            map.get(p.getTeam()).add(p);
        }
        String content = "Game Mode: " + rec.getGameMode() + "\n";
        int c = 1;
        for(String s : map.keySet()){
            content += "Team " + c++ + ": " + map.get(s).get(0);
            for(int j = 1; j < map.get(s).size(); j++){
                content += ", " + map.get(s).get(j);
            }
            content += "\n";
        }
        alert.setContentText(content + "Map: " + rec.getMap());
        alert.showAndWait();
    }
    
    private class GenerateRecordedGame extends Thread {
        
        private final File file;
        
        private Map<String,String> map;
    
        GenerateRecordedGame(File file){
            this.file = file;
            map = new HashMap<>();
            
            map.put("alfheim", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("anatolia", "rmSetObjectDefMaxDistance(startingTowerID, 25");
            map.put("ghost lake", "rmSetObjectDefMaxDistance(startingGoldID, 20");
            map.put("marsh", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("mediterranean", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("midgard", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("oasis", "rmSetObjectDefMaxDistance(startingTowerID, 22");
            map.put("savannah", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("tundra", "rmSetObjectDefMinDistance(startingTowerID, 20");
            map.put("watering hole", "rmSetObjectDefMinDistance(startingTower2ID, 25");
            
            start();
        }
        
        @Override
        public void run(){
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.skip(8);
                InflaterInputStream iis = new InflaterInputStream(fis);
                File inflated = new File(file.getName() + "_INFLATED");
                FileOutputStream fos = new FileOutputStream(inflated);
                
                doCopy(iis, fos, 60 * 1000);
                BufferedReader in = new BufferedReader(new FileReader(inflated));
                
                String s = null;
                int c = 0;
                RecordedGame rec = new RecordedGame(file.getName());
                Pattern pattern = Pattern.compile(">(.*)<");
                boolean xml = true;
                boolean stop = false;
                while ((s = in.readLine()) != null && !stop) {
                    if(xml){
                        if(s.contains("<")){ c++; }
                        Matcher matcher = pattern.matcher((s = new String(s.getBytes(), "UTF-16")));
                        if(matcher.find()){
                            rec.put(c, matcher.group(1));
                        }
                        if(s.contains("</GameSettings>")){
                            rec.setSize(c);
                            xml = false;
                            if((s = in.readLine()).contains("PATCH")){
                                rec.setPatch("PATCH");
                                recordings.add(rec);
                                stop = true;
                            }
                            if(!map.keySet().contains(rec.getMap().toLowerCase())){
                                rec.setPatch("NO PATCH");
                                recordings.add(rec);
                                stop = true;
                            }
                        }
                    } else {
                        if(s.contains(map.get(rec.getMap().toLowerCase()))){
                            rec.setPatch("PATCH");
                            recordings.add(rec);
                            stop = true;
                        }
                    }
                }
                if(s == null){
                    rec.setPatch("NO PATCH");
                    recordings.add(rec);
                }
                in.close();
                inflated.delete();
            } catch (IOException ex) {
                ExceptionGenerateRecordedGamesDialog.showDialog();
            }
        }
        
        private void doCopy(InputStream is, OutputStream os, int limit) {
            try {
                int c = 0;
                int oneByte;
                while ((oneByte = is.read()) != -1 && c < limit) {
                    os.write(oneByte);
                    c++;
                }
                os.close();
                is.close();
            } catch (IOException ex) {
                ExceptionGenerateRecordedGamesDialog.showDialog();
            }
        }
        
    }
    
    private class GenerateRecordingsTable extends Thread {
        
        private List<GenerateRecordedGame> generating;
        
        public GenerateRecordingsTable(List<GenerateRecordedGame> generating){
            this.generating = generating;
            start();
        }
        
        @Override
        public void run(){
            for(GenerateRecordedGame g : generating){
                try {
                    g.join();
                } catch (InterruptedException ex) {
                    
                }
            }
            Platform.runLater(new Runnable(){
                public void run(){
                    ObservableList<RecordingsTableRow> model = FXCollections.observableArrayList();
                    for(RecordedGame rec : recordings){
                        model.add(new RecordingsTableRow(rec));
                    }
                    tableView.setItems(model);
                    correspond.enableshowRecordedGamesDetailsButton();
                }
            });
        }
        
    }
    
}
