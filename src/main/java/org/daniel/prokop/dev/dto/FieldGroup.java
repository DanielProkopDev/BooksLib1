package org.daniel.prokop.dev.dto;

public enum FieldGroup {
    FIRSTNAME,
    LASTNAME,
    USERNAME,
    RegisteredIN;

    public static FieldGroup getField(String field){
        return FieldGroup.valueOf(field.toUpperCase());
    }
}