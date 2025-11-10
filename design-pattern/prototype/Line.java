package prototype;

public class Line implements Shape, Prototype{
    private Point startPt;
    private Point endPt;

    public Line setStartPt(Point startPt) {
        this.startPt = startPt;
        return this;
    }

    public Line setEndPt(Point endPt) {
        this.endPt = endPt;
        return this;
    }

    public Point getStartPt() {
        return startPt;
    }

    public Point getEndPt() {
        return endPt;
    }

    @Override
    public Object copy() {
        Line newLine = new Line();

        newLine.startPt = (Point)startPt.copy();
        newLine.endPt = (Point)endPt.copy();

        return newLine;
    }

    @Override
    public String draw() {
        return "LINE("+startPt.draw()+","+endPt.draw()+")";
    }

    @Override
    public void moveOffset(int dx, int dy) {
        startPt.moveOffset(dx, dy);
        endPt.moveOffset(dx, dy);
    }
}
