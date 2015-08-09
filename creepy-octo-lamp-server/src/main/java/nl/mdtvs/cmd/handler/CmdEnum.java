package nl.mdtvs.cmd.handler;

public enum CmdEnum {

    REGISTER_SESSION(0), UNREGISTER_SESSION(1),
    REGISTER_DEVICE(2), UNREGISTER_DEVICE(3);

    private final int hashKey;

    CmdEnum(int i) {
        hashKey = i;
    }

    public int getHashKey() {
        return hashKey;
    }

}
