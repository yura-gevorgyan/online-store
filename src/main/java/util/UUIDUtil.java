package util;

import java.util.UUID;

public class UUIDUtil {

    public static String randomId() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String[] split = uuidStr.split("-");
        return split[0];
    }

}
