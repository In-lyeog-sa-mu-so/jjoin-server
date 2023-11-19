package org.dongguk.jjoin.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DependentType {
    MAJOR("학과"), CENTER("중앙"), UNION("연합");

    private final String description;

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static DependentType from(String sub) {
        for (DependentType dependentType : DependentType.values()) {
            if (dependentType.description.equals(sub)) {
                return dependentType;
            }
        }
        throw new IllegalArgumentException();
    }
}
