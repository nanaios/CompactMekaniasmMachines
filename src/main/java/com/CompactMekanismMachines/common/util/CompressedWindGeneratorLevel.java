package com.CompactMekanismMachines.common.util;

public enum CompressedWindGeneratorLevel {
    X2(0,2),
    X8(1,8),
    X32(2,32),
    X128(3,128),
    X512(4,512),
    X2048(5,2048),
    X8192(6,8192),
    X32768(7,32768),
    X131072(8,131072),
    X532480(9,532480);

    private final int index;
    private final int multiplier;

    CompressedWindGeneratorLevel(int index, int multiplier) {
        this.index = index;
        this.multiplier = multiplier;
    }

    public int getIndex() {
        return index;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
