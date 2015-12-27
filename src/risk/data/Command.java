package risk.data;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Arrays;

public class Command {

    protected String original;
    protected String name;
    protected String[] parameters;

    public Command(String cmd) {
        this.original = cmd;
        String[] words = cmd.split("\\s+");
        this.name = words[0];
        this.parameters = Arrays.copyOfRange(words, 1, words.length);
    }

    public String getName() {
        return this.name;
    }

    public String[] getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        String params = this.parameters[0] != null ? this.parameters[0] : "";
        for (int i = 1; i < this.parameters.length; i++) {
            params += ", " + this.parameters[i];
        }
        return this.name + "->" + params;
    }
}
