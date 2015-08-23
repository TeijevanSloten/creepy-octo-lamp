package nl.mdtvs.util;

@FunctionalInterface
public interface ThrowableBiConsumer<T, U> {

    /**
     * BiConsumer that may throw an Exception
     *
     * @param t Type t that can be consumed
     * @param r Type r that can be consumed
     * @throws Exception
     */
    void consume(T t, U r) throws Exception;
}
