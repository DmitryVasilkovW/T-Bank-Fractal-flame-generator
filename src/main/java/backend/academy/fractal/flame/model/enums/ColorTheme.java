package backend.academy.fractal.flame.model.enums;

import lombok.Getter;

@Getter
public enum ColorTheme {
    BLACK_AND_WHITE("Black and White"),
    RANDOM("Random"),
    ORANGE("Orange"),
    NEON("Neon"),
    PINK("Pink"),
    AZURE("Azure"),
    BLUE("Blue");

    private final String displayName;
    private static final String ERROR_MESSAGE = "No enum constant with display name: ";

    ColorTheme(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static ColorTheme fromDisplayName(String displayName) {
        for (ColorTheme theme : ColorTheme.values()) {
            if (theme.displayName().equalsIgnoreCase(displayName)) {
                return theme;
            }
        }
        throw new IllegalArgumentException(ERROR_MESSAGE + displayName);
    }
}
