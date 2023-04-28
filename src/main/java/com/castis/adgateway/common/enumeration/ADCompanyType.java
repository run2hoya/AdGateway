package com.castis.adgateway.common.enumeration;

public enum ADCompanyType {
    UNKNOWN("UNKNOWN"),
    LGU("LGU"),
    HOME_CHOICE("HOME_CHOICE");

    private final String value;

    private ADCompanyType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ADCompanyType valueof(String value) {
        ADCompanyType[] list = ADCompanyType.values();
        for (ADCompanyType type : list) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return ADCompanyType.UNKNOWN;
    }


}
