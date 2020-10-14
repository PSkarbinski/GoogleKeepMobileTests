package com.twoupdigital.googlekeep.utils;

import io.appium.java_client.MobileElement;

import java.util.List;

public class Finders {

    public static MobileElement findElementContaining(String phrase, List<MobileElement> list) {
        if (!list.isEmpty()) {
            return list
                    .stream()
                    .filter(element -> element
                            .getText()
                            .contains(phrase))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

}
