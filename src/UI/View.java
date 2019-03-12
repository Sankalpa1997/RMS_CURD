package UI;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;

public class View extends Form implements CommandListener {

    private Command create, update;
    private ChoiceGroup c;

    public View(String title) {
        super(title);
        init();
    }

    private void init() {
        try {
            c = new ChoiceGroup("Select & Delete", ChoiceGroup.EXCLUSIVE);

            RecordStore rs = RecordStore.openRecordStore(Midlet.Midlet.RECORDSTORE_NAME, false);

            for (int i = 1; i < rs.getNextRecordID(); i++) {
                try {

                    System.out.println(i);
                    byte[] data = rs.getRecord(i);
                    String data_str = new String(data);
                    System.out.println(data_str);
                    StringItem s = new StringItem("ACCOUNT NO ", data_str);

                    c.append(i + "", null);

                    append(s);

                } catch (InvalidRecordIDException e) {
//                    e.printStackTrace();
                }
            }
            append(c);

            setItemStateListener(new ItemStateListener() {

                public void itemStateChanged(Item item) {

                    if (item == c) {
                        for (int i = 0; i < c.size(); i++) {
                            if (c.isSelected(i)) {
                                System.out.println(c.getString(i) + " selected");
                                try {
                                    RecordStore rs = RecordStore.openRecordStore(Midlet.Midlet.RECORDSTORE_NAME, false);
                                    for (int j = 1; j < rs.getNextRecordID(); j++) {
                                        try {
                                            if (c.getString(i).equals(String.valueOf(j))) {
                                                rs.deleteRecord(j);
                                                System.out.println("deleted");                               
                                                Midlet.Midlet.display.setCurrent(new View("VIEW"));

                                            }
                                        } catch (InvalidRecordIDException e) {
//                                            e.printStackTrace();
                                        }

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        create = new Command("Create", Command.OK, 3);
        update = new Command("Update", Command.OK, 1);
        

        addCommand(create);
        addCommand(update);
        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == create) {

            Midlet.Midlet.display.setCurrent(Midlet.Midlet.create);

        } else if (c == update) {
            Midlet.Midlet.display.setCurrent(Midlet.Midlet.update);

        }
    }

}
