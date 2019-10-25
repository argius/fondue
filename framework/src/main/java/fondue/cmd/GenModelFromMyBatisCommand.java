package fondue.cmd;

import fondue.gen.ModelFromMyBatisGenerator;

public final class GenModelFromMyBatisCommand {

    private GenModelFromMyBatisCommand() {
    }

    public static void main(String[] args) {
        ModelFromMyBatisGenerator.generate();
    }
}
