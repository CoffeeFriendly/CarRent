package com.vehco.carrent.enums;

public enum Role {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
