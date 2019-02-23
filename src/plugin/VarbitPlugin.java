package plugin;

import scape.editor.fs.io.RSBuffer;
import scape.editor.gui.plugin.Plugin;
import scape.editor.gui.plugin.extension.config.VarbitExtension;

@Plugin(name="Vanilla 317 Varbit Plugin", authors = "Nshusa", version = "1.1.0")
public class VarbitPlugin extends VarbitExtension {

    private int high;
    private int low;
    private int setting;

    @Override
    protected String getFileName() {
        return "varbit";
    }

    @Override
    protected void decode(int currentIndex, RSBuffer buffer) {
        while(true) {
            int opcode = buffer.readUByte();

            if (opcode == 0) {
                break;
            }

            if (opcode == 1) {
                setting = buffer.readUShort();
                low = buffer.readUByte();
                high = buffer.readUByte();
            } else if (opcode == 10) {
                buffer.readString10();
            } else if (opcode == 3 || opcode == 4) {
                buffer.readInt();
            } else if (opcode != 2) {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        }
    }

    @Override
    protected void encode(RSBuffer buffer) {
        buffer.writeByte(1);
        buffer.writeShort(setting);
        buffer.writeByte(low);
        buffer.writeByte(high);
        buffer.writeByte(0);
    }

    @Override
    public String fxml() {
        return "VarbitScene.fxml";
    }

    @Override
    public String[] stylesheets() {
        return new String[] {
                "css/style.css",
                "css/layout.css",
                "css/theme.css"
        };
    }

    @Override
    public String applicationIcon() {
        return "icons/icon.png";
    }

}