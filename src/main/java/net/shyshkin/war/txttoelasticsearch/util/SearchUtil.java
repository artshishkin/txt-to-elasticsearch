package net.shyshkin.war.txttoelasticsearch.util;

import net.shyshkin.war.txttoelasticsearch.model.SearchNameType;

public class SearchUtil {

    public static String getSearchName(String fullQuery, SearchNameType searchNameType) {

        switch (searchNameType) {
            case FULL_NAME:
                return getLastFirstName(fullQuery);
            case FIRST_NAME:
                return getFirstName(fullQuery);
            case LAST_NAME:
                return getLastName(fullQuery);
            case TOTAL_QUERY:
            default:
                return fullQuery;
        }
    }

    private static String getLastFirstName(String fullName) {
        String[] nameParts = getNameParts(fullName);
        if (nameParts.length < 3) return fullName;
        return nameParts[0] + " " + nameParts[1];
    }

    private static String getLastName(String fullName) {
        String[] nameParts = getNameParts(fullName);
        if (nameParts.length < 1) return fullName;
        return nameParts[0];
    }

    private static String getFirstName(String fullName) {
        String[] nameParts = getNameParts(fullName);
        if (nameParts.length < 2) return fullName;
        return nameParts[1];
    }

    private static String[] getNameParts(String fullName) {
        return fullName.trim()
                .replaceAll("\\s+", " ")
                .split(" ");
    }
}
