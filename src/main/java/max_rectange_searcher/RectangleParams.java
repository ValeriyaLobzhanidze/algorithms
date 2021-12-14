package max_rectange_searcher;

import java.util.Objects;

/**
 * */
public class RectangleParams {

    private final int base;
    private final int square;

    public RectangleParams(int base, int square) {
        this.base = base;
        this.square = square;
    }

    public int getSquare() {
        return square;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectangleParams that = (RectangleParams) o;
        return base == that.base;
    }

    @Override
    public int hashCode() {
        return Objects.hash(base);
    }
}


