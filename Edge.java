public class Edge implements EdgeInterface,Comparable<Edge> {
    Point source;
    Point destination;
    public ArrayList1<Triangle> face_neighbours;
    public Edge(Point source, Point destination){
        this.source = source;
        this.destination = destination;
        this.face_neighbours = new ArrayList1<>();
    }
    @Override
    public PointInterface[] edgeEndPoints() {
        PointInterface [] array = new PointInterface[2];
        array [0] = source;
        array [0] = destination;
        return array;
    }

    public float length(){
        float x = source.X - destination.X;
        float y = source.Y - destination.Y;
        float z = source.Z - destination.Z;
        float length = (float) Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        return length;
    }

    @Override
    public int compareTo(Edge edge) {
        if( edge.length() > this.length())
            return -1;
        else if(edge.length() < this.length())
            return  1;
        return 0;
    }
    public boolean isEquals(Edge edge){
        if(this.source.compareTo(edge.source)==0 && this.destination.compareTo(edge.destination)==0)
            return true;
        if(this.source.compareTo(edge.destination)==0 && this.destination.compareTo(edge.source)==0)
            return true;
        return false;
    }

    @Override
    public String toString() {
        String line="";
        if(source.compareTo(destination)<0)
         line = "[" + source.toString() + ", " + destination.toString() + "]";
        else
            line = "[" + destination.toString() + ", " + source.toString() + "]";
        return line;
    }
}
