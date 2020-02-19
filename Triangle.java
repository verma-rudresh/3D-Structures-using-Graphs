public class Triangle implements TriangleInterface,Comparable<Triangle> {
    public Point A,B,C;
    public Edge AB,BC,CA;
//    public ArrayList<Triangle> neighbours;
    public ArrayList1<Edge> edge_neighbours;
    public ArrayList1<Point> point_neighbours;
    int count;
    public Triangle(Point A, Point B, Point C){
        this.A = A;
        this.B = B;
        this.C = C;
    }
//    public ArrayList<Triangle> extended_neighbours;
    public Triangle(int count){

        this.edge_neighbours = new ArrayList1<>();
        edge_neighbours.add(AB);
        edge_neighbours.add(BC);
        edge_neighbours.add(CA);
        this.point_neighbours = new ArrayList1<>();
        point_neighbours.add(A);
        point_neighbours.add(B);
        point_neighbours.add(C);
        this.count= count;
//        this.extended_neighbours = new ArrayList<>();
//        this.neighbours = new ArrayList<>();
    }
    @Override
    public PointInterface[] triangle_coord() {
        PointInterface[] array = new PointInterface[3];
        array[0] = A;
        array[1] = B;
        array[2] = C;
        return array;
    }

    @Override
    public int compareTo(Triangle triangle) {
        if(this.count>triangle.count)
        return 1;
        else return -1;
    }
//    public boolean isEquals(Point A, Point B, Point C){
//        if (A.compareTo(this.A) == 0 && B.compareTo(this.B) == 0 && C.compareTo(this.C) == 0) {
//            return true;
//        }
//        if (A.compareTo(this.A) == 0 && B.compareTo(this.C) == 0 && C.compareTo(this.B) == 0) {
//            return true;
//        }
//        if (A.compareTo(this.B) == 0 && B.compareTo(this.A) == 0 && C.compareTo(this.C) == 0) {
//            return true;
//        }
//        if (A.compareTo(this.B) == 0 && B.compareTo(this.C) == 0 && C.compareTo(this.A) == 0) {
//            return true;
//        }
//        if (A.compareTo(this.C) == 0 && B.compareTo(this.A) == 0 && C.compareTo(this.B) == 0) {
//            return true;
//        }
//        if (A.compareTo(this.C) == 0 && B.compareTo(this.B) == 0 && C.compareTo(this.A) == 0) {
//            return true;
//        }
//        return false;
//    }

    @Override
    public String toString() {
        String line = "";
        if(A.compareTo(B)<0 && B.compareTo(C)<0)
            line = "[" + A.toString() + ", " + B.toString() + ", " + C.toString() + "]";
        else if(A.compareTo(C)<0 && C.compareTo(B)<0)
            line = "[" + A.toString() + ", " + C.toString() + ", " + B.toString() + "]";
        else if(B.compareTo(A)<0 && A.compareTo(C)<0)
            line = "[" + B.toString() + ", " + A.toString() + ", " + C.toString() + "]";
        else if(B.compareTo(C)<0 && C.compareTo(A)<0)
            line = "[" + B.toString() + ", " + C.toString() + ", " + A.toString() + "]";
        else if(C.compareTo(A)<0 && A.compareTo(B)<0)
            line = "[" + C.toString() + ", " + A.toString() + ", " + B.toString() + "]";
        else if(C.compareTo(B)<0 && B.compareTo(A)<0)
            line = "[" + C.toString() + ", " + B.toString() + ", " + A.toString() + "]";
        return line;
    }

    @Override
    public boolean equals(Object obj) {
        Triangle triangle = (Triangle) obj;
        if (triangle.A.compareTo(this.A) == 0 && triangle.B.compareTo(this.B) == 0 && triangle.C.compareTo(this.C) == 0) {
            return true;
        }
        if (triangle.A.compareTo(this.A) == 0 && triangle.B.compareTo(this.C) == 0 && triangle.C.compareTo(this.B) == 0) {
            return true;
        }
        if (triangle.A.compareTo(this.B) == 0 && triangle.B.compareTo(this.A) == 0 && triangle.C.compareTo(this.C) == 0) {
            return true;
        }
        if (triangle.A.compareTo(this.B) == 0 && triangle.B.compareTo(this.C) == 0 && triangle.C.compareTo(this.A) == 0) {
            return true;
        }
        if (triangle.A.compareTo(this.C) == 0 && triangle.B.compareTo(this.A) == 0 && triangle.C.compareTo(this.B) == 0) {
            return true;
        }
        if (triangle.A.compareTo(this.C) == 0 && triangle.B.compareTo(this.B) == 0 && triangle.C.compareTo(this.A) == 0) {
            return true;
        }
        return false;
    }
}
