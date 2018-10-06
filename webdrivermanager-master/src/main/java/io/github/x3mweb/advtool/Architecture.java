package io.github.x3mweb.advtool;

/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public enum Architecture {
    DEFAULT, X32, X64;

    @Override
    public String toString() {
        return this.name().contains("X") ? this.name().replace("X", "")
                : this.name();
    }

}
