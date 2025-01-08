package ronapps;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Data2 (
        int id,
        String description,
        String status,
        String updatedAt,
        String timeCreated
) {
    @JsonCreator
    public Data2 {}

    public Data2(int id, String description, String createdAt) {
        this(id, description, "todo", null, createdAt);
    }
}

