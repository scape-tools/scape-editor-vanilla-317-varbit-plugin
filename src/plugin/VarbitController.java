package plugin;

import javafx.fxml.FXML;
import scape.editor.fs.RSArchive;
import scape.editor.gui.App;
import scape.editor.gui.controller.NonNamedConfigController;
import scape.editor.gui.event.LoadVarbitEvent;
import scape.editor.gui.event.SaveVarbitEvent;
import scape.editor.gui.plugin.PluginManager;

public class VarbitController extends NonNamedConfigController {

    @Override
    public void onPopulate() {
        try {
            RSArchive archive = App.Companion.getFs().getArchive(RSArchive.CONFIG_ARCHIVE);
            PluginManager.INSTANCE.post(new LoadVarbitEvent(this.getCurrentPlugin(), archive, this.getIndexes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void onSave() {
        try {
            RSArchive archive = App.Companion.getFs().getArchive(RSArchive.CONFIG_ARCHIVE);
            PluginManager.INSTANCE.post(new SaveVarbitEvent(this.getCurrentPlugin(), archive, this.getIndexes()));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}