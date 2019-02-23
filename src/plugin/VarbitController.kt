package plugin

import javafx.fxml.FXML
import scape.editor.fs.RSArchive
import scape.editor.gui.App
import scape.editor.gui.controller.NonNamedConfigController
import scape.editor.gui.event.LoadVarbitEvent
import scape.editor.gui.event.SaveVarbitEvent
import scape.editor.gui.plugin.PluginManager

class VarbitController : NonNamedConfigController() {

    override fun onPopulate() {
        try {
            val archive = App.fs.getArchive(RSArchive.CONFIG_ARCHIVE)
            PluginManager.post(LoadVarbitEvent(this.currentPlugin, archive, this.indexes))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    @FXML
    fun onSave() {
        try {
            val archive = App.fs.getArchive(RSArchive.CONFIG_ARCHIVE)
            PluginManager.post(SaveVarbitEvent(this.currentPlugin, archive, this.indexes))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

}