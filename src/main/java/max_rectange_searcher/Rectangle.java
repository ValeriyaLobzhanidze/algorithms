package max_rectange_searcher;

import java.util.Objects;

public class Rectangle {

    private int startX;
    private int endX;
    private int height;

    public Rectangle() {
    }

    public Rectangle(int startX, int endX, int height) {
        this.startX = startX;
        this.endX = endX;
        this.height = height;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getWidth() {
        return endX - startX + 1;
    }

    public boolean intersectsWith(Rectangle rectangle) {
        return oneEdgeInAnother(this, rectangle) || oneEdgeInAnother(rectangle, this);
    }

    private boolean oneEdgeInAnother(Rectangle first, Rectangle second) {
        return (first.startX >= second.startX && first.startX <= second.endX)
                || (first.endX >= second.startX && first.endX <= second.endX);
    }

    public boolean inside(Rectangle rectangle) {
        return (startX >= rectangle.startX && startX <= rectangle.endX)
                && (endX >= rectangle.startX && endX <= rectangle.endX);
    }

    public boolean hasTheSameXCordsWith(Rectangle rectangle) {
        return startX == rectangle.startX && endX == rectangle.endX;
    }

    public int getSquare() {
        return getWidth() * height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return startX == rectangle.startX &&
                endX == rectangle.endX &&
                height == rectangle.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX, endX, height);
    }
}


