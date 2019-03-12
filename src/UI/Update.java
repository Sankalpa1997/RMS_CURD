package UI;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;

public class Update extends Form implements CommandListener {

    private Command update, view, create, search;
    private TextField acc, dataTxt;

    public Update(String title) {
        super(title);
        inti();
    }

    private void inti() {
        acc = new TextField("Account ", null, 15, TextField.NUMERIC);
        dataTxt = new TextField("Data ", null, 30, TextField.ANY);

        update = new Command("Update", Command.OK, 0);
        view = new Command("View", Command.OK, 1);
        create = new Command("Create", Command.OK, 2);
        search = new Command("Search", Command.OK, 6);

        addCommand(update);
        addCommand(create);
        addCommand(view);
        addCommand(search);
        setCommandListener(this);

        append(acc);
        append(dataTxt);

    }

    public void commandAction(Command c, Displayable d) {
        if (c == search) {
            try {
                RecordStore rs = RecordStore.openRecordStore(Midlet.Midlet.RECORDSTORE_NAME, false);
                boolean isFound = false;
                for (int i = 0; i < rs.getNextRecordID(); i++) {
                    try {
                        if (acc.getString().equals(String.valueOf(i))) {

                            byte[] data = rs.getRecord(i);
                            isFound = true;
                            String data_str = new String(data);
                            System.out.println(data_str + " found");
                            dataTxt.setString(data_str);
                            break;

                        } else {
                            System.out.println("not found");
                            isFound = false;
                        }
                    } catch (InvalidRecordIDException e) {
//                    e.printStackTrace();
                    }
                }
                if (!isFound) {
                    dataTxt.setString(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (c == update) {
            try {
                RecordStore rs = RecordStore.openRecordStore(Midlet.Midlet.RECORDSTORE_NAME, false);
                byte[] data = dataTxt.getString().getBytes();
                rs.setRecord(Integer.parseInt(acc.getString()), data, 0, data.length);
                dataTxt.setString(null);
                System.out.println(acc.getString() + " updated");
            } catch (InvalidRecordIDException e) {
//                    e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else if (c == view) {
            Midlet.Midlet.display.setCurrent(new View("VIEW"));
        } else if (c == create) {
            Midlet.Midlet.display.setCurrent(Midlet.Midlet.create);

        }
    }

}
