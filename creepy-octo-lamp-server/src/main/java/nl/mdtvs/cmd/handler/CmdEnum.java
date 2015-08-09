package nl.mdtvs.cmd.handler;

public enum CmdEnum {

    REGISTER_DEVICE(0), UNREGISTER_DEVICE(1);

    private final int hashKey;

    CmdEnum(int i) {
        hashKey = i;
    }

    public int getHashKey() {
        return hashKey;
    }
}
