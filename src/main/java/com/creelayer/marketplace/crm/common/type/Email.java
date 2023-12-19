package com.creelayer.marketplace.crm.common.type;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "email"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class Email {

    @JsonValue
    private String value;

    public Email(String email) {

        if (email != null && !email.matches("^(.+)@(\\S+)$"))
            throw new IllegalStateException("Invalid email format");

        this.value = email;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
