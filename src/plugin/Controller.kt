package plugin

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import scape.editor.fs.RSArchive
import scape.editor.fx.TupleCellFactory
import scape.editor.gui.App
import scape.editor.gui.controller.BaseController
import scape.editor.gui.event.LoadCacheEvent
import scape.editor.gui.model.KeyModel
import scape.editor.gui.model.NamedValueModel
import scape.editor.gui.model.ValueModel
import scape.editor.gui.plugin.PluginManager
import scape.editor.gui.plugin.extension.ConfigExtension
import scape.editor.gui.util.FXDialogUtil
import java.net.URL
import java.util.*

class Controller : BaseController() {

    @FXML
    lateinit var listView: ListView<KeyModel>

    @FXML
    lateinit var tableView: TableView<NamedValueModel>

    val indexes = FXCollections.observableArrayList<KeyModel>()

    @FXML
    lateinit var nameCol : TableColumn<NamedValueModel, String>

    @FXML
    lateinit var valueCol: TableColumn<NamedValueModel, ValueModel>

    val data = FXCollections.observableArrayList<NamedValueModel>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        listView.items = indexes
        listView.selectionModel.selectedItemProperty().addListener { _, _, newValue ->

            if (newValue == null || newValue.id < 0) {
                return@addListener
            }

            data.clear()

            for (set in newValue.map.entries) {
                data.add(NamedValueModel(set.key, ValueModel(newValue, set.key, set.value)))
            }

        }

        nameCol.setCellValueFactory { it.value.nameProperty }
        valueCol.setCellValueFactory { it.value.valueProperty }
        valueCol.setCellFactory { TupleCellFactory() }
        tableView.items = data
    }

    override fun onPopulate() {
        data.clear()
        indexes.clear()

        PluginManager.post(LoadCacheEvent(App.fs))

        try {
            val archive = App.fs.getArchive(RSArchive.CONFIG_ARCHIVE)

            val currentPlugin = this.currentPlugin

            if (currentPlugin is ConfigExtension) {
                try {
                    currentPlugin.onLoad(indexes, archive)
                } catch(ex: Exception) {
                    ex.printStackTrace()
                    FXDialogUtil.showException(ex)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onClear() {
        data.clear()
        indexes.clear()
    }

    @FXML
    private fun goBack() {
        switchScene("StoreScene")
    }

    @FXML
    fun onSave() {
        try {
            val archive = App.fs.getArchive(RSArchive.CONFIG_ARCHIVE)
            val currentPlugin = this.currentPlugin

            if (currentPlugin is ConfigExtension) {
                currentPlugin.onSave(indexes, archive)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            FXDialogUtil.showException(ex)
        }

    }

}