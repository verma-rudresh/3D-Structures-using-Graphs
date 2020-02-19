public class Point implements PointInterface, Comparable<Point> {
    float X,Y,Z;
//    ArrayList<Edge> edge_list;
    public ArrayList1<Point> point_neighbours;
    public ArrayList1<Edge> edge_neighbours;
    public ArrayList1<Triangle> face_neighbours;
    public Point(float X, float Y, float Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
//        this.edge_list = new ArrayList<>();
        this.edge_neighbours = new ArrayList1<>();
        this.face_neighbours = new ArrayList1<>();
        this.point_neighbours = new ArrayList1<>();
    }
    public boolean visited = false;

    @Override
    public float getX() {
        return X;
    }

    @Override
    public float getY() {
        return Y;
    }

    @Override
    public float getZ() {
        return Z;
    }

    @Override
    public float[] getXYZcoordinate() {
        float [] coordinate_array = new float[3];
        coordinate_array[0] = X;
        coordinate_array[1] = Y;
        coordinate_array[2] = Z;
        return coordinate_array;
    }

    @Override
    public int compareTo(Point point) {
        if(this.X==point.X && this.Y==point.Y && this.Z==point.Z)
        return 0;
        if((this.X < point.X) || (this.X==point.X && this.Y < point.Y) || (this.X==point.X && this.Y==point.Y && this.Z < point.Z))
            return -1;
        return 1;
    }

    @Override
    public String toString() {
        String line = "(" + X + "," + Y + "," + Z + ")";
        return line;
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        if(compareTo(point)==0)
            return true;
        return false;
    }
}
