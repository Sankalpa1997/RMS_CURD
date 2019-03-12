package UI;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;

public class Create extends Form implements CommandListener {

    private TextField acc, name, ammount;
    private Command add, view, update;

    public Create(String title) {
        super(title);
        init();
    }

    private void init() {

        acc = new TextField("Acc NO", null, 15, TextField.NUMERIC);
        name = new TextField("Name", null, 15, TextField.ANY);
        ammount = new TextField("Ammount", null, 15, TextField.NUMERIC);

        add = new Command("Add", Command.OK, 4);
        view = new Command("View", Command.OK, 1);
        update = new Command("Update", Command.OK, 2);
    
        addCommand(add);
        addCommand(view);
        addCommand(update);


        setCommandListener(this);

        append(acc);
        append(name);
        append(ammount);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == add) {
            try {
                String data = acc.getString() + " - " + name.getString() + " (" + ammount.getString()+".00LKR)";
                RecordStore rs = RecordStore.openRecordStore(Midlet.Midlet.RECORDSTORE_NAME, true);
               int id =  rs.addRecord(data.getBytes(), 0, data.getBytes().length);
                rs.closeRecordStore();

                System.out.println(id+" added");
                Alert alert = new Alert("ADDED!");
                Midlet.Midlet.display.setCurrent(alert);

                clearFields();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (c == view) {

            Midlet.Midlet.display.setCurrent(new View("VIEW"));
        } else if (c == update) {

            Midlet.Midlet.display.setCurrent(Midlet.Midlet.update);
        }
    }

    private void clearFields() {

        acc.setString(null);
        name.setString(null);
        ammount.setString(null);

    }

}
