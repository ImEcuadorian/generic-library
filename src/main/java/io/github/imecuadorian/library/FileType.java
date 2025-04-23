package io.github.imecuadorian.library;

import lombok.*;

@Getter
@AllArgsConstructor
public enum FileType {

    DIRECTORY("DIRECTORY"),
    FILE("FILE");

    private final String type;
}
