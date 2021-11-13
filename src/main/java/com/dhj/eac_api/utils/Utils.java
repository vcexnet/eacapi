package com.dhj.eac_api.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public abstract class Utils {
    public static final Charset CHARSET = java.nio.charset.StandardCharsets.UTF_8;
    public static final String CHARSETNAME = "UTF-8";
    public static final Charset ASCII = java.nio.charset.StandardCharsets.US_ASCII;
    public static final java.util.Random RANDOM = new java.util.Random();
    public static final char[] BASE = "0123456789abcdef".toCharArray();

    public static String hex(byte b) {
        int a = Byte.toUnsignedInt(b);
        return "" + BASE[a / 16] + BASE[a % 16];
    }

    public static String getAsHex(byte[] data) {
        StringBuilder rts = new StringBuilder();
        if (data == null) {
            return rts.toString();
        }
        for (byte datum : data) {
            rts.append(hex(datum));
        }
        return rts.toString();
    }

    public static byte[] sha256(byte[] data) {
        MessageDigest hasher;
        try {
            hasher = MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException ex) {
            return null;
        }
        return hasher.digest(data);
    }

    public static byte[] double_sha256(byte[] data) {
        MessageDigest hasher;
        try {
            hasher = MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException ex) {
            return null;
        }
        return hasher.digest(hasher.digest(data));
    }

    public static byte[] reverse(byte[] data) {
        byte[] rts = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            rts[i] = data[data.length - 1 - i];
        }
        return rts;
    }

    public static long timeNow() {
        return java.time.Instant.now().getEpochSecond();
    }

    public static long timePrecise() {
        return java.time.Instant.now().toEpochMilli();
    }


    public static int checksum(byte[] data) {
        byte[] hash = Utils.double_sha256(data);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.put(hash, 0, 4);
        byteBuffer.flip();
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static int cmdHash(String s) {
        byte[] hash = Utils.sha256(s.getBytes(ASCII));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.put(hash, 0, 4);
        byteBuffer.flip();
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static int passIntParam(String[] params, int n, int defaultRts) {
        if (!(params.length > n)) {
            return defaultRts;
        }
        try {
            int rts = Integer.parseInt(params[n]);
            return Math.max(rts, 0);
        } catch (NumberFormatException e) {
            return defaultRts;
        }
    }

    public static long passLongParam(String[] params, int n, long defaultRts) {
        if (!(params.length > n)) {
            return defaultRts;
        }
        try {
            long rts = Long.parseLong(params[n]);
            return (rts > 0) ? rts : 0;
        } catch (NumberFormatException e) {
            return defaultRts;
        }
    }

    public static double passDoubleParam(String[] params, int n, double defaultRts) {
        if (!(params.length > n)) {
            return defaultRts;
        }
        try {
            double rts = Double.parseDouble(params[n]);
            return (rts > 0) ? rts : 0;
        } catch (NumberFormatException e) {
            return defaultRts;
        }
    }

    public static String passStringParam(String[] params, int n) {
        if (!(params.length > n)) {
            return "";
        }
        try {
            return URLDecoder.decode(params[n], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}


