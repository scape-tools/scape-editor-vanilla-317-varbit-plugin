package plugin

import scape.editor.fs.io.RSBuffer
import scape.editor.gui.plugin.PluginDescriptor
import scape.editor.gui.plugin.extension.config.ConfigExtension

@PluginDescriptor(name = "Vanilla 317 Varbit Plugin", authors = ["Nshusa"], version = "1.1.0")
class Plugin : ConfigExtension() {

    private var high: Int = 0
    private var low: Int = 0
    private var setting: Int = 0

    override fun getFileName(): String {
        return "varbit"
    }

    override fun decode(currentIndex: Int, buffer: RSBuffer) {
        while (true) {
            val opcode = buffer.readUByte()

            if (opcode == 0) {
                break
            }

            if (opcode == 1) {
                setting = buffer.readUShort()
                low = buffer.readUByte()
                high = buffer.readUByte()
            } else if (opcode == 10) {
                buffer.readString10()
            } else if (opcode == 3 || opcode == 4) {
                buffer.readInt()
            } else if (opcode != 2) {
                println("Error unrecognised config code: $opcode")
            }
        }
    }

    override fun encode(buffer: RSBuffer) {
        buffer.writeByte(1)
        buffer.writeShort(setting)
        buffer.writeByte(low)
        buffer.writeByte(high)
        buffer.writeByte(0)
    }

    override fun fxml(): String {
        return "scene.fxml"
    }

    override fun stylesheets(): Array<String> {
        return arrayOf("css/style.css")
    }

    override fun applicationIcon(): String {
        return "icons/icon.png"
    }

}