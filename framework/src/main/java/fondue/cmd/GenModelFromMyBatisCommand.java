package fondue.cmd;

import fondue.gen.ModelFromMyBatisGenerator;

@Deprecated
public final class GenModelFromMyBatisCommand {

    private GenModelFromMyBatisCommand() {
    }

    public static void main(String[] args) {
        ModelFromMyBatisGenerator.generate();
    }
}
