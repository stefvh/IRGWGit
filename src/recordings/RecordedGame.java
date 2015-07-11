package recordings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stef
 */
public class RecordedGame {
    
    private Map<Integer, String> map;
    
    private List<Player> players;
    
    private String fileName;
    
    private String patch;

    private int size = 0;
    
    private int x = 0;
    
    private final String[] modes  = new String[] { "Supremacy", "Lightning", "Conquest", "Deathmatch" };
    
    private final String[] gods = new String[] { "Zeus", "Poseidon", "Hades",
        "Isis", "Ra", "Set",
        "Odin", "Thor", "Loki",
        "Kronos", "Oranos", "Gaia",
        "Random", "Greek", "Norse", "Egyptian", "Atlantean" };

    
    public RecordedGame(String fileName){
        map = new HashMap<>();
        players = new ArrayList<>();
        this.fileName = fileName;
    }
    
    public void put(int i, String s){
        map.put(i, s);
    }
    
    public void setSize(int size){
        this.size = size;
        x = ((size+1) % 2);
        setPlayers();
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public String getPatch(){
        return patch;
    }
    
    public void setPatch(String patch){
        this.patch = patch;
    }
    
    public String getMap(){
        return map.get(2+x);
    }
    
    public String getGameMode(){
        if(map.get(12+x) == null){
            return modes[0];
        } else {
            return modes[Integer.parseInt(map.get(12+x))];
        }
    }
    
    public List<Player> getPlayers(){
        return players;
    }
    
    private void setPlayers(){
        int j = (size - 17 + x)/10;
        for(int i=0; i < j; i++){
            if(map.get(20+x+i*10).equals("0")){
                String god;
                if((god = map.get(24+x+i*10)) == null){
                    god = "12";
                }
                players.add(new Player(map.get(18+x+i*10),
                    map.get(23+x+i*10),
                    gods[Integer.parseInt(god)]
                ));
            }
        }
    }
    
}
