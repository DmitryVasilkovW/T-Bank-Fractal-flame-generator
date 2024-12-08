package backend.academy.fractal.flame.model.enums;

import lombok.Getter;

@Getter
public enum Transformations {
    COMPLEX_WAVE("Complex Wave"),
    HORSESHOE("Horseshoe"),
    LINEAR("Linear"),
    OSCILLATING("Oscillating"),
    SINUSOIDAL("Sinusoidal"),
    SINUSOIDAL_WARP("Sinusoidal Warp"),
    SPHERICAL("Spherical"),
    STRENGTH_ALONG_X_AND_Y("Strength Along X and Y"),
    SWIRL("Swirl"),
    TANGENT_WARP("Tangent Warp");

    private final String displayName;
    private static final String ERROR_MESSAGE = "No enum constant with display name: ";

    Transformations(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Transformations fromDisplayName(String displayName) {
        for (Transformations t : Transformations.values()) {
            if (t.displayName.equalsIgnoreCase(displayName)) {
                return t;
            }
        }
        throw new IllegalArgumentException(ERROR_MESSAGE + displayName);
    }
}
