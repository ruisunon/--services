package com.rycoding.ecommerce.utils;

import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

@NoArgsConstructor
public class Utils {
    public static byte[] uuidToBytes(UUID uuid) {
        byte[] buffer = new byte[16];
        return ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits()).array();
    }
}
