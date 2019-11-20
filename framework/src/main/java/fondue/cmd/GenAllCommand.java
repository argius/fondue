package fondue.cmd;

@Deprecated
public final class GenAllCommand {

    private GenAllCommand() {
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        GenModelFromMyBatisCommand.main(args);
        GenCrudCommand.main(args);
    }
}
