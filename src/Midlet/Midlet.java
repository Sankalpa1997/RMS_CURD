package Midlet;

import UI.Create;
import UI.Update;
import UI.View;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public class Midlet extends MIDlet {
    
    public static Display display;
    public static Create create;
    public static Update update;

    
    public static final String RECORDSTORE_NAME = "bankDemo";
    
    private void init() {
        
        display = Display.getDisplay(this);
        create = new Create("CREATE");
        update = new Update("UPDATE");

        
    }
    
    public void startApp() {
        init();
        display.setCurrent(create);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
