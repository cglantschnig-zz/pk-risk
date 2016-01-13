package risk.utils.command;

import risk.utils.command.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CommandParser {

    private String path = "src/assets/squares.map";
    private ArrayList<Command> commands = new ArrayList<>();

    public CommandParser() {
        this.readFile();
    }

    public CommandParser(String path) {
        this.path = path;
        this.readFile();
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    private void readFile() {
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(this.path));

            while ((sCurrentLine = br.readLine()) != null) {
                if (!sCurrentLine.isEmpty()) {
                    this.commands.add(new Command(sCurrentLine));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
