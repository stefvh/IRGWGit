package main;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Stef
 */


public class MainModel {
    
    public final static String appName = "Indivision's Recorder Game Watcher";
    
    private final String path = "C:\\Program Files (x86)\\Microsoft Games\\Age of Mythology\\";
    
    private final String patchPath = "Voobly Mods\\AOMT\\Data Mods\\";
    
    private final String patchFolder = "Voobly Balance Patch 1.01";
    
    private final String backUpFolder = "backup";
    
    private final String[] directories = {"data", "rm2", "god powers"};
    
    public MainModel(){
       
    }
    
    public void launch() throws IOException{
        new ProcessBuilder(path + "aomxnocd.exe").start();
        new Thread() {
            public void run() {
                try { Thread.sleep(30000); } catch (InterruptedException ex) {}
                new File("vooblylaunch2.txt").delete();
            }
        }.start();
    }
    
    public void turnPatchOff() throws IOException {
        initBackUp();
        for(String dir : directories){
            File patchDir = new File(path + patchPath + patchFolder + "\\" + dir);
            File[] files = patchDir.listFiles();
            for(File f : files){
                File backUpFile = new File(path + patchPath + backUpFolder + "\\" + dir + "\\" + f.getName());
                File patchFile = new File(path + dir + "\\" + f.getName());
                if(backUpFile.exists()){
                    FileUtils.copyFile(backUpFile, patchFile);
                } else {
                    patchFile.delete();
                }
            }
        }
        deleteXMBs();
    }
    
    public void turnPatchOn() throws IOException {
        initBackUp();
        for(String dir : directories){
            File src = new File(path + patchPath + patchFolder + "\\" + dir);
            File dest = new File(path + dir);
            FileUtils.copyDirectory(src, dest);
        }
    }
    
    public void initBackUp() throws IOException{
        File backUp = new File(path + patchPath + backUpFolder);
        if(!backUp.exists()){
            backUp.mkdir();
            for(String dir : directories){
                File src = new File(path + dir);
                File dest = new File(path + patchPath + backUpFolder + "\\" + dir);
                FileUtils.copyDirectory(src, dest);
            }
        }
    }
    
    private void deleteXMBs(){
        new File(path + "data\\protox.XMB").delete();
        new File(path + "data\\techtreex.XMB").delete();
    }
    
}
