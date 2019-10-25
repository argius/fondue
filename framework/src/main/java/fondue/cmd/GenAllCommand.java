package fondue.cmd;

public final class GenAllCommand {

    private GenAllCommand() {
    }

    public static void main(String[] args) {
        GenModelFromMyBatisCommand.main(args);
        GenCrudCommand.main(args);
    }
}
