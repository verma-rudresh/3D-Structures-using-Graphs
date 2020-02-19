public class Connected implements Comparable<Connected> {
    public ArrayList1<Triangle> triangle_components;
    public ArrayList1<Point> point_components;
    public Connected(){
        this.triangle_components = new ArrayList1<>();
        this.point_components = new ArrayList1<>();
    }

    @Override
    public int compareTo(Connected connected) {
        if(this.triangle_components.size()>connected.triangle_components.size())
        return 1;
        if(this.triangle_components.size()<connected.triangle_components.size())
            return -1;
        return 0;
    }
}
