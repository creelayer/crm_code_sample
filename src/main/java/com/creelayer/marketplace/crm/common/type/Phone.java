package com.creelayer.marketplace.crm.common.type;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "phone"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class Phone {

    @JsonValue
    private String value;

    public Phone(String phone) {

        if(phone == null)
            throw new IllegalStateException("Invalid phone");

        if (!phone.matches("^[0-9]{12}$"))
            throw new IllegalStateException("Invalid phone format");

        this.value = phone;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
