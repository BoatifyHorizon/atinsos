package com.wsis.atinsos;

import java.util.UUID;

public class IdGenerator {
    public static int generateNumericId() {
        return (int) Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}
