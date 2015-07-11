package recordings;

/**
 *
 * @author Stef
 */
public class Player {
    
    private String name;
    
    private String team;
    
    private String god;
    
    Player(String name, String team, String god){
        this.name = name;
        if(name.contains("[TSM_]")){ this.name += "(noob zionist TSM)"; }
        this.team = team;
        this.god = god;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getGod() {
        return god;
    }

    public void setGod(String god) {
        this.god = god;
    }
    
    @Override
    public String toString(){
        return name + " (" + god + ")";
    }
    
}
