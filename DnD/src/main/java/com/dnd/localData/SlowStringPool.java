package com.dnd.localData;

import java.util.function.Supplier;

public class SlowStringPool extends AbstractPool<String> {

    public SlowStringPool() {
        super(buildSupplier(), 10, 20, 99);
    }

    private static Supplier<String> buildSupplier() {
        return () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // nothing
            }
            return String.valueOf(System.currentTimeMillis());
        };
    }

    public SlowStringPool(Supplier<String> supplier) {
        super(supplier);
    }

    @Override
    protected String handleDepleted(Supplier<String> supplier) throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        SlowStringPool mypool = new SlowStringPool();
        Thread.sleep(2000);
        int i = 0;
        while (true) {
            long start = System.nanoTime();
            String s = mypool.borrowItem();
            long durn = System.nanoTime() - start;
            System.out.printf("%4d -> %s in %10.3fms -> %s\n", i++, s, durn / 1000000.0, mypool.toString());
            if (i % 2 == 0) {
                mypool.returnItem(s);
            }
        }
    }

}