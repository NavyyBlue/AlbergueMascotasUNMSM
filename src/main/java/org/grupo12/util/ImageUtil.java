package org.grupo12.util;

public class ImageUtil {
    public static String generateUniqueImageName(String originalFileName) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return timeStamp + "_" + Math.abs(originalFileName.hashCode()) + fileExtension;
    }
}
