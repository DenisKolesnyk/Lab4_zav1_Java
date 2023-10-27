class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class LineSegment {
    Point startPoint;
    Point endPoint;

    public LineSegment(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double length() {
        double deltaX = endPoint.x - startPoint.x;
        double deltaY = endPoint.y - startPoint.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}

class Square {
    LineSegment[] sides;
    String color;

    public Square(Point topLeft, double sideLength, String color) {
        this.color = color;

        Point topRight = new Point(topLeft.x + sideLength, topLeft.y);
        Point bottomLeft = new Point(topLeft.x, topLeft.y + sideLength);
        Point bottomRight = new Point(topLeft.x + sideLength, topLeft.y + sideLength);

        sides = new LineSegment[4];
        sides[0] = new LineSegment(topLeft, topRight);
        sides[1] = new LineSegment(topRight, bottomRight);
        sides[2] = new LineSegment(bottomRight, bottomLeft);
        sides[3] = new LineSegment(bottomLeft, topLeft);
    }

    public void changeSize(double newSideLength) {
        double scaleFactor = newSideLength / getSideLength();

        for (LineSegment side : sides) {
            side.endPoint.x = side.startPoint.x + (side.endPoint.x - side.startPoint.x) * scaleFactor;
            side.endPoint.y = side.startPoint.y + (side.endPoint.y - side.startPoint.y) * scaleFactor;
        }
    }

    public void stretch(double factorX, double factorY) {
        for (LineSegment side : sides) {
            side.endPoint.x = side.startPoint.x + (side.endPoint.x - side.startPoint.x) * factorX;
            side.endPoint.y = side.startPoint.y + (side.endPoint.y - side.startPoint.y) * factorY;
        }
    }

    public void rotate(double angleInDegrees) {
        double angleInRadians = Math.toRadians(angleInDegrees);
        Point center = getCenter();

        for (LineSegment side : sides) {
            double deltaX = side.startPoint.x - center.x;
            double deltaY = side.startPoint.y - center.y;
            side.startPoint.x = center.x + deltaX * Math.cos(angleInRadians) - deltaY * Math.sin(angleInRadians);
            side.startPoint.y = center.y + deltaX * Math.sin(angleInRadians) + deltaY * Math.cos(angleInRadians);

            deltaX = side.endPoint.x - center.x;
            deltaY = side.endPoint.y - center.y;
            side.endPoint.x = center.x + deltaX * Math.cos(angleInRadians) - deltaY * Math.sin(angleInRadians);
            side.endPoint.y = center.y + deltaX * Math.sin(angleInRadians) + deltaY * Math.cos(angleInRadians);
        }
    }

    public void changeColor(String newColor) {
        this.color = newColor;
    }

    public double getSideLength() {
        return sides[0].length();
    }

    public Point getCenter() {
        double centerX = (sides[0].startPoint.x + sides[2].endPoint.x) / 2;
        double centerY = (sides[0].startPoint.y + sides[2].endPoint.y) / 2;
        return new Point(centerX, centerY);
    }
}

public class Main {
    public static void main(String[] args) {
        Point topLeft = new Point(1.0, 1.0);
        Square square = new Square(topLeft, 3.0, "Red");

        System.out.println("Initial square side length: " + square.getSideLength());

        square.changeSize(5.0);
        System.out.println("Modified square side length: " + square.getSideLength());

        square.stretch(2.0, 1.5);
        square.rotate(45.0);
        square.changeColor("Blue");
    }
}
