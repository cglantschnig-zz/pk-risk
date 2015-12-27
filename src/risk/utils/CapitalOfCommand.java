package risk.utils;

public class CapitalOfCommand extends Command {
    private String country;
    private int x = 0;
    private int y = 0;
    public CapitalOfCommand(Command cmd) throws InvalidCommandException{
        super(cmd.original);
        try {
            this.country = "";
            int start = 0;
            boolean noException = true;
            while (noException) {
                try {
                    Integer.parseInt(this.parameters[start]);
                    noException = false;
                } catch (NumberFormatException e) {
                    this.country += " " + this.parameters[start];
                    start++;
                }
            }
            this.country = this.country.trim();
            for (int i = start; (i + 1) < this.parameters.length; i += 2) {
                this.x = Integer.parseInt(this.parameters[i]);
                this.y = Integer.parseInt(this.parameters[i + 1]);
            }
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    public String getCountry() {
        return this.country;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
