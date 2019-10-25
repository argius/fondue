package fondue.cmd;

import fondue.gen.CrudGenerator;

public final class GenCrudCommand {

    private GenCrudCommand() {
    }

    public static void main(String[] args) {
        try {
            CrudGenerator.generateAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
