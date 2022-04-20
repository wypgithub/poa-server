package com.poa.server.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdUtil {

    public String getIdString(String key) {
        Object id = "";
        String idString = id.toString();
        if (idString.length() < 6) {
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < 6 - idString.length(); i++) {
                prefix.append("0");
            }
            idString = prefix.toString() + idString;
        }
        // add '-'
        String sub1 = idString.substring(0, 3);
        String sub2 = idString.substring(3);

        return sub1 + "-" + sub2;
    }


}
