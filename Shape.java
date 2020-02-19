public class Shape implements ShapeInterface
{
    private int global_count=0;
    private ArrayList1<Point> all_points = new ArrayList1<>();
    private ArrayList1<Edge> all_edges = new ArrayList1<>();
    private ArrayList1<Triangle> all_triangles = new ArrayList1<>();
    Trie<Point> points_trie = new Trie<>();
    Trie<Edge> edges_trie = new Trie<>();
    Trie <Triangle> triangles_trie = new Trie<>();
    public float[] direction(Point A, Point B){
        float[] array = new float[3];
        array[0] = B.X - A.X;
        array[1] = B.Y - A.Y;
        array[2] = B.Z - A.Z;
        return array;
    }

    public float magnitude(float[] vec){
        float mag = (float) Math.sqrt(vec[0]*vec[0] + vec[1]*vec[1] + vec[2]*vec[2]);
        return mag;
    }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public boolean ADD_TRIANGLE(float [] triangle_coord){
        Point A = new Point(triangle_coord[0],triangle_coord[1], triangle_coord[2]);
        Point B = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        Point C = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        if(A.compareTo(B)==0 || B.compareTo(C)==0 || C.compareTo(A) == 0)
            return false;
        float[] vec_AB = direction(A, B);
        float[] vec_AC = direction(A, C);
        float result = (vec_AB[0]*vec_AC[0] + vec_AB[1]*vec_AC[1] + vec_AB[2]*vec_AC[2])/(magnitude(vec_AB)*magnitude(vec_AC));
        if(Math.abs(result)>0.999999)
            return false;
        Triangle triangle_obj = new Triangle(global_count);
        global_count++;
        all_triangles.add(triangle_obj);
        ///////////

        String key_A = A.toString();
        String key_B = B.toString();
        String key_C = C.toString();
        TrieNode<Point> point_node_A = points_trie.search(key_A);
        if(point_node_A!=null){
            triangle_obj.A = point_node_A.value;
            point_node_A.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.A = A;
            points_trie.insert(key_A,A);
            all_points.add(A);
            A.face_neighbours.add_sorted(triangle_obj);
        }
        TrieNode<Point> point_node_B = points_trie.search(key_B);
        if(point_node_B!=null){
            triangle_obj.B = point_node_B.value;
            point_node_B.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.B = B;
            points_trie.insert(key_B,B);
            all_points.add(B);
            B.face_neighbours.add_sorted(triangle_obj);
        }
        TrieNode<Point> point_node_C = points_trie.search(key_C);
        if(point_node_C!=null){
            triangle_obj.C = point_node_C.value;
            point_node_C.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.C = C;
            points_trie.insert(key_C,C);
            all_points.add(C);
            C.face_neighbours.add_sorted(triangle_obj);
        }
        /////////

        /*
        boolean A_exist= false;
        boolean B_exist = false;
        boolean C_exist = false;
        int Size = all_points.size();
        for(int j=0; j<Size; j++){
            Point point_obj = all_points.get(j);
            if(point_obj.compareTo(A)==0){
                A_exist = true;
                triangle_obj.A = point_obj;
//                point_obj.face_neighbours.add_triangle(triangle_obj);
                point_obj.face_neighbours.add_sorted(triangle_obj);
            }
            else if(point_obj.compareTo(B)==0){
                B_exist = true;
                triangle_obj.B = point_obj;
//                point_obj.face_neighbours.add_triangle(triangle_obj);
                point_obj.face_neighbours.add_sorted(triangle_obj);
            }
            else if(point_obj.compareTo(C)==0){
                C_exist = true;
                triangle_obj.C = point_obj;
//                point_obj.face_neighbours.add_triangle(triangle_obj);
                point_obj.face_neighbours.add_sorted(triangle_obj);
            }
        }

        if(!A_exist){
            triangle_obj.A = A;
            all_points.add(A);
//            A.face_neighbours.add_triangle(triangle_obj);
            A.face_neighbours.add_sorted(triangle_obj);
        }
        if(!B_exist){
            triangle_obj.B = B;
//            B.face_neighbours.add_triangle(triangle_obj);
            B.face_neighbours.add_sorted(triangle_obj);
            all_points.add(B);
        }
        if(!C_exist){
            triangle_obj.C = C;
//            C.face_neighbours.add_triangle(triangle_obj);
            C.face_neighbours.add_sorted(triangle_obj);
            all_points.add(C);
        }

        */
//        int size_edge_list  = all_edges.size();
        Edge AB = new Edge(triangle_obj.A,triangle_obj.B);
        Edge BC = new Edge(triangle_obj.B,triangle_obj.C);
        Edge CA = new Edge(triangle_obj.C,triangle_obj.A);
        /////////
        String key_AB = AB.toString();
        String key_BC = BC.toString();
        String key_CA = CA.toString();
        TrieNode<Edge> node_AB = edges_trie.search(key_AB);
        if (node_AB!=null){
            triangle_obj.AB = node_AB.value;
            node_AB.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.AB = AB;
            all_edges.add(AB);
            edges_trie.insert(key_AB,AB);
//            AB.face_neighbours.add_triangle(triangle_obj);
            AB.face_neighbours.add_sorted(triangle_obj);
            triangle_obj.A.edge_neighbours.add(triangle_obj.AB);
            triangle_obj.B.edge_neighbours.add(triangle_obj.AB);
            triangle_obj.A.point_neighbours.add(B);
            triangle_obj.B.point_neighbours.add(A);
        }

        TrieNode<Edge> node_BC = edges_trie.search(key_BC);
        if (node_BC!=null){
            triangle_obj.BC = node_BC.value;
            node_BC.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.BC = BC;
            all_edges.add(BC);
            edges_trie.insert(key_BC,BC);
//            AB.face_neighbours.add_triangle(triangle_obj);
            BC.face_neighbours.add_sorted(triangle_obj);
            triangle_obj.B.edge_neighbours.add(triangle_obj.BC);
            triangle_obj.C.edge_neighbours.add(triangle_obj.BC);
            triangle_obj.C.point_neighbours.add(B);
            triangle_obj.B.point_neighbours.add(C);
        }
        TrieNode<Edge> node_CA = edges_trie.search(key_CA);
        if (node_CA!=null){
            triangle_obj.CA = node_CA.value;
            node_CA.value.face_neighbours.add_sorted(triangle_obj);
        }
        else{
            triangle_obj.CA = CA;
            all_edges.add(CA);
            edges_trie.insert(key_CA,CA);
//            AB.face_neighbours.add_triangle(triangle_obj);
            CA.face_neighbours.add_sorted(triangle_obj);
            triangle_obj.A.edge_neighbours.add(triangle_obj.CA);
            triangle_obj.C.edge_neighbours.add(triangle_obj.CA);
            triangle_obj.A.point_neighbours.add(C);
            triangle_obj.C.point_neighbours.add(A);
        }

        /*
        boolean AB_exist=false;
        boolean BC_exist=false;
        boolean CA_exist= false;
        for(int i=0; i<size_edge_list; i++){
            Edge obj = all_edges.get(i);
            if(obj.isEquals(AB)){
                AB_exist= true;
                triangle_obj.AB = obj;
//                obj.face_neighbours.add_triangle(triangle_obj);
                obj.face_neighbours.add_sorted(triangle_obj);
            }
            else if(obj.isEquals(BC)){
                BC_exist= true;
                triangle_obj.BC = obj;
//                obj.face_neighbours.add_triangle(triangle_obj);
                obj.face_neighbours.add_sorted(triangle_obj);
            }
            else if(obj.isEquals(CA)){
                CA_exist= true;
                triangle_obj.CA = obj;
//                obj.face_neighbours.add_triangle(triangle_obj);
                obj.face_neighbours.add_sorted(triangle_obj);
            }
        }
        if(triangle_obj.AB==null){
            triangle_obj.AB = AB;
            all_edges.add(AB);
//            AB.face_neighbours.add_triangle(triangle_obj);
            AB.face_neighbours.add_sorted(triangle_obj);
        }
        if(triangle_obj.BC==null){
            triangle_obj.BC = BC;
            all_edges.add(BC);
//            BC.face_neighbours.add_triangle(triangle_obj);
            BC.face_neighbours.add_sorted(triangle_obj);
        }
        if(triangle_obj.CA==null){
            triangle_obj.CA = CA;
            all_edges.add(CA);
//            CA.face_neighbours.add_triangle(triangle_obj);
            CA.face_neighbours.add_sorted(triangle_obj);
        }
        if(!AB_exist){
            triangle_obj.A.edge_neighbours.add(triangle_obj.AB);
            triangle_obj.B.edge_neighbours.add(triangle_obj.AB);
            triangle_obj.A.point_neighbours.add(B);
            triangle_obj.B.point_neighbours.add(A);
        }
        if(!BC_exist){
            triangle_obj.C.edge_neighbours.add(triangle_obj.BC);
            triangle_obj.B.edge_neighbours.add(triangle_obj.BC);
            triangle_obj.C.point_neighbours.add(B);
            triangle_obj.B.point_neighbours.add(C);
        }
        if(!CA_exist){
            triangle_obj.A.edge_neighbours.add(triangle_obj.CA);
            triangle_obj.C.edge_neighbours.add(triangle_obj.CA);
            triangle_obj.A.point_neighbours.add(C);
            triangle_obj.C.point_neighbours.add(A);
        }

        */
        triangles_trie.insert(triangle_obj.toString(),triangle_obj);
        return true;
    }

    //
     public int TYPE_MESH(){
//         if(all_points.size()+ all_triangles.size()- all_edges.size()==2)
//             return 1;
         int x=0;
         int Size = all_edges.size();
         for(int i=0; i<Size; i++){
             Edge edge_obj = all_edges.get(i);
             if(edge_obj.face_neighbours.size()>2){
                 return 3;
             }
             else if(edge_obj.face_neighbours.size()==1)
                 x=2;
         }
         if(x==2)
         return 2;
         return 1;
    }

    //
     public EdgeInterface [] BOUNDARY_EDGES(){
        int Size = all_edges.size();
        ArrayList1<Edge> list = new ArrayList1<>();
        for(int i=0; i<Size; i++){
            Edge edge_obj = all_edges.get(i);
            if(edge_obj.face_neighbours.size()==1)
//                list.add_edge(edge_obj);
                list.add_sorted(edge_obj);
        }
        int list_size = list.size();
         Edge[] arr = new Edge[list_size];
        if(list_size==0)
            return null;

        for(int k=0; k<list_size; k++){
            arr[k] = list.get(k);
        }
//         MergeSort obj = new MergeSort();
//        obj.merge_sort_Edge(arr, 0, list_size-1);
        return arr;}

    //
     public int COUNT_CONNECTED_COMPONENTS(){
        int count =0;
        boolean [] visited = new boolean[all_triangles.size()];
        for(int i=0; i<all_triangles.size(); i++){
            if(!visited[i]){
                count++;
//                Connected obj = new Connected();
                DFS(i, visited);
//                connected_components.add(obj);
            }
        }
        return count;}

    private void DFS(int count, boolean[] visited)
    {
        visited[count] = true;
        Triangle tri_obj = all_triangles.get(count);
//        list.add(tri_obj);
        int size_AB = tri_obj.AB.face_neighbours.size();
        ArrayList1<Triangle> list_AB = tri_obj.AB.face_neighbours;
        for(int i=0; i<size_AB; i++){
            int count1 = list_AB.get(i).count;
            if(!visited[count1])
                DFS(count1,visited);
        }
        int size_BC = tri_obj.BC.face_neighbours.size();
        ArrayList1<Triangle> list_BC = tri_obj.BC.face_neighbours;
        for(int i=0; i<size_BC; i++){
            int count1 = list_BC.get(i).count;
            if(!visited[count1])
                DFS(count1, visited);
        }
        int size_CA = tri_obj.CA.face_neighbours.size();
        ArrayList1<Triangle> list_CA = tri_obj.CA.face_neighbours;
        for(int i=0; i<size_CA; i++){
            int count1 = list_CA.get(i).count;
            if(!visited[count1])
                DFS(count1, visited);
        }
    }

    public void DFS_components(int count, boolean[] visited, ArrayList1<Triangle> list){
        visited[count] = true;
        Triangle tri_obj = all_triangles.get(count);
        list.add(tri_obj);
        int size_AB = tri_obj.AB.face_neighbours.size();
        ArrayList1<Triangle> list_AB = tri_obj.AB.face_neighbours;
        for(int i=0; i<size_AB; i++){
            int count1 = list_AB.get(i).count;
            if(!visited[count1])
                DFS_components(count1,visited, list);
        }
        int size_BC = tri_obj.BC.face_neighbours.size();
        ArrayList1<Triangle> list_BC = tri_obj.BC.face_neighbours;
        for(int i=0; i<size_BC; i++){
            int count1 = list_BC.get(i).count;
            if(!visited[count1])
                DFS_components(count1, visited, list);
        }
        int size_CA = tri_obj.CA.face_neighbours.size();
        ArrayList1<Triangle> list_CA = tri_obj.CA.face_neighbours;
        for(int i=0; i<size_CA; i++){
            int count1 = list_CA.get(i).count;
            if(!visited[count1])
                DFS_components(count1, visited, list);
        }
    }


    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord){
        Point A = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        Point B = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        Point C = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        Triangle check_triangle = new Triangle(A, B, C);
//        int Size = all_triangles.size();
        int count=0;
        Edge[] edge_array = new Edge[3];
        TrieNode<Triangle> node = triangles_trie.search(check_triangle.toString());
        if(node==null)
            return null;
        Triangle triangle_obj = node.value;
        edge_array[0] = (triangle_obj.AB);
        edge_array[1] = (triangle_obj.BC);
        edge_array[2] = (triangle_obj.CA);
        count = triangle_obj.count;
        /*
        for(int i=0; i<Size; i++){
            Triangle triangle_obj = all_triangles.get(i);
            if (triangle_obj.equals(check_triangle)) {
                count = triangle_obj.count;
                edge_array[0] = (triangle_obj.AB);
                edge_array[1] = (triangle_obj.BC);
                edge_array[2] = (triangle_obj.CA);
                break;
            }
        }
        if(count==-1)
            return null;
        */
        ArrayList1<Triangle> neighbours = new ArrayList1<>();
        int s1= edge_array[0].face_neighbours.size();
        for(int m=0; m<s1; m++){
            Triangle t = edge_array[0].face_neighbours.get(m);
            if(t.count!=count)
//                neighbours.add_triangle(t);
                neighbours.add_sorted(t);
        }
        int s2= edge_array[1].face_neighbours.size();
        for(int m=0; m<s2; m++){
            Triangle t = edge_array[1].face_neighbours.get(m);
            if(t.count!=count)
//                neighbours.add_triangle(t);
            neighbours.add_sorted(t);
        }
        int s3= edge_array[2].face_neighbours.size();
        for(int m=0; m<s3; m++){
            Triangle t = edge_array[2].face_neighbours.get(m);
            if(t.count!=count)
//                neighbours.add_triangle(t);
                neighbours.add_sorted(t);
        }
        int Size_neighbour = neighbours.size();
        Triangle[] neighbour_array = new Triangle[Size_neighbour];
        if(Size_neighbour==0)
            return neighbour_array;

        for(int m=0; m<Size_neighbour; m++){
            neighbour_array[m] = neighbours.get(m);
        }
//        MergeSort obj = new MergeSort();
//        obj.merge_sort_Triangle(neighbour_array,0, Size_neighbour-1);
        return neighbour_array;
    }


    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
     public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
         Point A = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
         Point B = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
         Point C = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
         Triangle check_triangle = new Triangle(A, B, C);
//         int Size = all_triangles.size();
//         int count = -1;
         Edge[] edge_array = new Edge[3];
         TrieNode<Triangle> node = triangles_trie.search(check_triangle.toString());
         if(node==null)
             return null;
         Triangle triangle_obj = node.value;
         edge_array[0] = (triangle_obj.AB);
         edge_array[1] = (triangle_obj.BC);
         edge_array[2] = (triangle_obj.CA);
         /*
         for (int i = 0; i < Size; i++) {
             Triangle triangle_obj = all_triangles.get(i);
             if (triangle_obj.equals(check_triangle)) {
                 count = triangle_obj.count;
                 edge_array[0] = (triangle_obj.AB);
                 edge_array[1] = (triangle_obj.BC);
                 edge_array[2] = (triangle_obj.CA);
                 break;
             }
         }
         */
//         if(count!=-1)
         return edge_array;
//         else return null;
     }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
     public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
         Point A = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
         Point B = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
         Point C = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
         Triangle check_triangle = new Triangle(A, B, C);
//         int Size = all_triangles.size();
//         int count = -1;
         Point[] point_array = new Point[3];
         TrieNode<Triangle> node = triangles_trie.search(check_triangle.toString());
         if(node==null)
             return null;
         Triangle triangle_obj = node.value;
         point_array[0] = (triangle_obj.A);
         point_array[1] = (triangle_obj.B);
         point_array[2] = (triangle_obj.C);
         /*
         for (int i = 0; i < Size; i++) {
             Triangle triangle_obj = all_triangles.get(i);
             if (triangle_obj.equals(check_triangle)) {
                 count = triangle_obj.count;
                 point_array[0] = (triangle_obj.A);
                 point_array[1] = (triangle_obj.B);
                 point_array[2] = (triangle_obj.C);
                 break;
             }

         }
         if (count == -1)
             return null;
         */
         return point_array;
     }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
     public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord){
         Point A = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
         Point B = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
         Point C = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
         Triangle check_triangle = new Triangle(A, B, C);
//         int Size = all_triangles.size();
         int count = -1;
         Point[] point_array = new Point[3];
         TrieNode<Triangle> node = triangles_trie.search(check_triangle.toString());
         if(node==null)
             return null;
         Triangle triangle_obj = node.value;
         point_array[0] = (triangle_obj.A);
         point_array[1] = (triangle_obj.B);
         point_array[2] = (triangle_obj.C);
         count = triangle_obj.count;
         /*
         for (int i = 0; i < Size; i++) {
             Triangle triangle_obj = all_triangles.get(i);
             if(triangle_obj.equals(check_triangle)){
                 count = triangle_obj.count;
                 point_array[0] = (triangle_obj.A);
                 point_array[1] = (triangle_obj.B);
                 point_array[2] = (triangle_obj.C);
                 break;
             }

         }
         if(count==-1)
             return null;
         */
         ArrayList1<Triangle> extended_neighbours = new ArrayList1<>();
         for(int k=0; k<3;k++){
             int s1= point_array[k].face_neighbours.size();
             for(int m=0; m<s1; m++){
                 Triangle t = point_array[k].face_neighbours.get(m);
                 if(t.count!=count)
                     extended_neighbours.add_triangle_uniquely(t);
             }
         }

         int Size_neighbour = extended_neighbours.size();
         if(Size_neighbour==0)
             return null;
         Triangle[] extended_neighbour_array = new Triangle[Size_neighbour];
         for(int m=0; m<Size_neighbour; m++) {
             extended_neighbour_array[m] = extended_neighbours.get(m);
         }
//         MergeSort obj = new MergeSort();
//         obj.merge_sort_Triangle(extended_neighbour_array,0, Size_neighbour-1);
         return extended_neighbour_array;
        }


    //INPUT [x,y,z]
     public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates){
         Point A = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
//         int size = all_points.size();
//         int count= -1;
         TrieNode<Point> node = points_trie.search(A.toString());
         if(node==null)
             return null;
         Point point = node.value;
         A= point;
         /*
         for(int i=0; i<size; i++){
             Point point = all_points.get(i);
             if(point.compareTo(A)==0)
             {
                 A = point;
                 count =0;
                 break;
             }
         }
         if(count==-1)
         {
//             System.out.println("fire");
             return null;
         }
         */
        int s = A.face_neighbours.size();
//        System.out.println("size of face_neighbours list: " + s);
        Triangle[] incident_triangles = new Triangle[s];
        for(int j=0; j<s; j++){
            incident_triangles[j] = A.face_neighbours.get(j);
//            System.out.println(A.face_neighbours.get(j));
        }
//        MergeSort obj = new MergeSort();
//        obj.merge_sort_Triangle(incident_triangles, 0, s-1);
        return incident_triangles;
     }


    // INPUT [x,y,z]
     public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates){
         Point A = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
//         int size = all_points.size();
//         int count= -1;
         TrieNode<Point> node = points_trie.search(A.toString());
         if(node==null)
             return null;
         A = node.value;
         /*
         for(int i=0; i<size; i++){
             Point point = all_points.get(i);
             if(point.compareTo(A)==0)
             {
                 A = point;
                 count =0;
                 break;
             }
         }
         if(count==-1)
        return null;
         */
         int s= A.point_neighbours.size();
         Point[] point_neighbours = new Point[s];
         for(int j= 0; j<s; j++){
             point_neighbours[j] = A.point_neighbours.get(j);
         }
         return point_neighbours;
     }


    // INPUT[x,y,z]
     public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates){
         Point A = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
//         int size = all_points.size();
//         int count= -1;
         TrieNode<Point> node = points_trie.search(A.toString());
         if(node==null)
             return null;
         A = node.value;
         /*
         for(int i=0; i<size; i++){
             Point point = all_points.get(i);
             if(point.compareTo(A)==0)
             {
                 A = point;
                 count =0;
                 break;
             }
         }
         if(count==-1){
//             System.out.println("fired");
             return null;
         }
        */
         int s= A.edge_neighbours.size();
         Edge[] Edge_neighbours = new Edge[s];
         for(int j= 0; j<s; j++){
             Edge_neighbours[j] = A.edge_neighbours.get(j);
         }
         return Edge_neighbours;
     }


    // INPUT[x,y,z]
     public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates){
        return INCIDENT_TRIANGLES(point_coordinates);}

        // INPUT // [xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 , xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3]   where xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 are 3 coordinates of first triangle and xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3 are coordinates of second triangle as given in specificaition.
    public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2){
//        if(1< connected_components.size())
//            return false;
//        return true;
        Point A1 = new Point(triangle_coord_1[0], triangle_coord_1[1], triangle_coord_1[2]);
        Point B1 = new  Point(triangle_coord_1[3], triangle_coord_1[4], triangle_coord_1[5]);
        Point C1 = new Point(triangle_coord_1[6], triangle_coord_1[7], triangle_coord_1[8]);
        Point A2 = new Point(triangle_coord_2[0], triangle_coord_2[1], triangle_coord_2[2]);
        Point B2 = new Point(triangle_coord_2[3], triangle_coord_2[4], triangle_coord_2[5]);
        Point C2 = new Point(triangle_coord_2[6], triangle_coord_2[7], triangle_coord_2[8]);
        Triangle t1 = new Triangle(A1, B1, C1);
        Triangle t2 = new Triangle(A2, B2, C2);
        int countOf1=-1; int  countOf2=-1;
        TrieNode<Triangle> node1 = triangles_trie.search(t1.toString());
        if(node1 ==null)
        {
//            System.out.println("T1 does not exist");
            return false;
        }
        TrieNode<Triangle> node2 = triangles_trie.search(t2.toString());
        if(node2==null)
        {
//            System.out.println("T2 does not exist");
            return false;
        }
        countOf1= node1.value.count;
        countOf2 = node2.value.count;
        /*
        int x=0;
        for(int i=0; i<all_triangles.size(); i++){
            Triangle obj = all_triangles.get(i);
            if(obj.equals(t1))
            {
                countOf1=i;;
                x++;
            }
            else if(obj.equals(t2))
            {
                countOf2 = i;
                x++;
            }
            if(x==2)
                break;
        }
        if(countOf1==-1)
           { System.out.println("T1 does not exist");
            return false;
           }
        if(countOf2==-1)
        {
            System.out.println("T2 does not exist");
            return false;
        }
        */
        boolean [] arr = new boolean[all_triangles.size()];
        DFS(countOf1,arr);
        if(!arr[countOf2])
        return false;
        return true;
    }

    // INPUT [x1,y1,z1,x2,y2,z2] // where (x1,y1,z1) refers to first end point of edge and  (x2,y2,z2) refers to the second.
     public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates){
        Point A = new Point(edge_coordinates[0], edge_coordinates[1], edge_coordinates[2]);
        Point B = new Point(edge_coordinates[3], edge_coordinates[4], edge_coordinates[5]);
        Edge edge = new Edge(A,B);
        int check = -1;
        TrieNode<Edge> node = edges_trie.search(edge.toString());
        if(node==null)
            return null;
        edge = node.value;

//        for(int i=0; i<all_edges.size(); i++){
//            Edge edge_obj = all_edges.get(i);
//            if(edge_obj.isEquals(edge)){
//                edge = edge_obj;
//                check = 0;
//                break;
//            }
//        }
//        if(check==-1)
//            return null;

        int size = edge.face_neighbours.size();
        Triangle[] array = new Triangle[size];
        for(int i=0; i<size; i++){
            array[i] = edge.face_neighbours.get(i);
        }
        return array;}

    public int BFS(Triangle obj1){
        boolean [] visited = new boolean[all_triangles.size()];
        ArrayList1<Triangle> triangles_queue = new ArrayList1<>();
//        Triangle null_triangle = new Triangle(null, null ,null);
        int number = 0;
        visited[obj1.count] = true;
        triangles_queue.add(obj1);
//        list_of_triangles.add(null_triangle);
        triangles_queue.add(null);
        int j=0;
        while(true){
          Triangle obj = triangles_queue.get(j);
          if(obj==null && j== triangles_queue.size()-1)
              break;
            else if( obj!=null){
//                System.out.println(obj);
                for(int i=0; i<obj.AB.face_neighbours.size(); i++){
                    Triangle obj2 = obj.AB.face_neighbours.get(i);
                    if(!visited[obj2.count])
                    {
                        triangles_queue.add(obj2);
                        visited[obj2.count] = true;
                    }
                }
                for(int i=0; i<obj.BC.face_neighbours.size(); i++){
                    Triangle obj2 = obj.BC.face_neighbours.get(i);
                    if(!visited[obj2.count])
                    {
                        triangles_queue.add(obj2);
                        visited[obj2.count] = true;
                    }
                }
                for(int i=0; i<obj.CA.face_neighbours.size(); i++){
                    Triangle obj2 = obj.CA.face_neighbours.get(i);
                    if(!visited[obj2.count])
                    {
                        triangles_queue.add(obj2);
                        visited[obj2.count] = true;
                    }
                }
//
            }
            else{
//              System.out.println("Level " + number + " executed");
              triangles_queue.add(null);
              number++;
          }
            j++;
        }
//        while(0< list_of_triangles.size()){
//            System.out.println("Initial size: " + list_of_triangles.size());
//
//            Triangle obj = list_of_triangles.remove(0);
//
//            System.out.println("Final size: " + list_of_triangles.size());
//            if( obj!=null){
////                System.out.println(obj);
//                for(int i=0; i<obj.AB.face_neighbours.size(); i++){
//                    Triangle obj1 = obj.AB.face_neighbours.get(i);
//                    if(!visited[obj1.count])
//                    {
//                        list_of_triangles.add(obj1);
//                        visited[obj1.count] = true;
//                    }
//                }
//                for(int i=0; i<obj.BC.face_neighbours.size(); i++){
//                    Triangle obj1 = obj.BC.face_neighbours.get(i);
//                    if(!visited[obj1.count])
//                    {
//                        list_of_triangles.add(obj1);
//                        visited[obj1.count] = true;
//                    }
//                }
//                for(int i=0; i<obj.CA.face_neighbours.size(); i++){
//                    Triangle obj1 = obj.CA.face_neighbours.get(i);
//                    if(!visited[obj1.count])
//                    {
//                        list_of_triangles.add(obj1);
//                        visited[obj1.count] = true;
//                    }
//                }
////
//            }
//            else if(list_of_triangles.size()>0)
//            {System.out.println("null");
//                System.out.println("Level " + number + " executed");
//                list_of_triangles.add(null_triangle);
//                number++;
//            }
//        }
        return number;
    }

     public int MAXIMUM_DIAMETER() {
         ArrayList1<Connected> connected_components = new ArrayList1<>();
         int diameter = 0;
         boolean[] visited = new boolean[all_triangles.size()];
         for (int i = 0; i < all_triangles.size(); i++) {
             if (!visited[i]) {
//                System.out.println("fire");
//                 ArrayList1<Triangle> list = new ArrayList1<>();
                 Connected obj = new Connected();
                 DFS_components(i, visited, obj.triangle_components);
                 connected_components.add_sorted(obj);
             }
         }
            ArrayList1<Triangle> biggest_component = connected_components.get(connected_components.size()-1).triangle_components;
            for(int i=0; i<biggest_component.size(); i++){
                int depth = BFS(biggest_component.get(i));
                if(depth>diameter)
                    diameter =depth;
            }
            return diameter;
     }

    public void DFS_for_Points(int count,boolean[] visited, ArrayList1<Point> list) {
        visited[count] = true;
        Triangle tri_obj = all_triangles.get(count);
        if(tri_obj!=null){
            list.add_uniquely(tri_obj.A);
            list.add_uniquely(tri_obj.B);
            list.add_uniquely(tri_obj.C);
        }

        int size_AB = tri_obj.AB.face_neighbours.size();
        ArrayList1<Triangle> list_AB = tri_obj.AB.face_neighbours;
        for (int i = 0; i < size_AB; i++) {
            int count1 = list_AB.get(i).count;
            if (!visited[count1])
                DFS_for_Points(count1, visited, list);
        }
        int size_BC= tri_obj.BC.face_neighbours.size();
        ArrayList1<Triangle> list_BC = tri_obj.BC.face_neighbours;
        for (int i = 0; i < size_BC; i++) {
            int count1 = list_BC.get(i).count;
            if (!visited[count1])
                DFS_for_Points(count1, visited, list);
        }
        int size_CA = tri_obj.CA.face_neighbours.size();
        ArrayList1<Triangle> list_CA = tri_obj.CA.face_neighbours;
        for (int i = 0; i < size_CA; i++) {
            int count1 = list_CA.get(i).count;
            if (!visited[count1])
                DFS_for_Points(count1, visited, list);
        }

    }
//    public void DFS_point(Point point, ArrayList1<Point> list){
//        point.visited =true;
//        list.add(point);
//        for(int i=0; i<point.edge_neighbours.size(); i++){
//            Edge edge = point.edge_neighbours.get(i);
//            if(edge.source.compareTo(point)==0)
//                if(!edge.destination.visited)
//                DFS_point(edge.destination, list);
//            else
//                if(!edge.source.visited)
//                DFS_point(edge.source, list);
//        }
//    }

     public PointInterface [] CENTROID (){
//        ArrayList<Point> list_of_centriods = new ArrayList<>();
//        for(int i=0; i<connected_components.size(); i++){
//            ArrayList<Point> list_points = new ArrayList<>();
//            for(int j=0; j<connected_components.get(i).connected_triangles.size(); j++){
//                Triangle triangle = connected_components.get(i).connected_triangles.get(j);
//                list_points.add_uniquely(triangle.A);
//                list_points.add_uniquely(triangle.B);
//                list_points.add_uniquely(triangle.C);
//            }
//            float X=0; float Y=0; float Z=0;
//            for(int j=0; j<list_points.size(); j++){
//                X += list_points.get(j).X;
//                    Y += list_points.get(j).Y;
//                    Z += list_points.get(j).Z;
//            }
//            X = X/list_points.size();
//            Y = Y/list_points.size();
//            Z = Z/list_points.size();
//            Point centriod = new Point(X, Y, Z);
//            list_of_centriods.add(centriod);
//        }
//        Point[] array = new Point[list_of_centriods.size()];
//        for(int i=0; i<list_of_centriods.size(); i++){
//            array[i] = list_of_centriods.get(i);
//        }
//        MergeSort obj = new MergeSort();
//        obj.merge_sort_Point(array, 0, list_of_centriods.size()-1);
//        return array;
        boolean [] visited= new boolean[all_triangles.size()];
        ArrayList1<Point> centriod_list = new ArrayList1<>();
        for(int i=0; i< all_triangles.size(); i++){
            if(!visited[i]){
                ArrayList1<Point> list = new ArrayList1<>();
                DFS_for_Points(i, visited, list);
                float X = 0; float Y = 0; float Z = 0;
                for( int j=0; j<list.size(); j++){
                    X += list.get(j).X;
                    Y += list.get(j).Y;
                    Z += list.get(j).Z;
                }
                X = X/list.size();
                Y = Y/list.size();
                Z = Z/list.size();
                Point centriod = new Point(X, Y, Z);
//                centriod_list.add_point(centriod);
                centriod_list.add_sorted(centriod);
//                System.out.println("centriod: " +centriod);
                for(int vi=0; vi< centriod_list.size(); vi++){
//                    System.out.println("elements are: " + centriod_list.get(vi));
                }
            }
       }
        Point [] array = new Point[centriod_list.size()];
//        System.out.println("size of centriod list: " + centriod_list.size());
        for(int i=0; i< centriod_list.size(); i++){
            array[i] = centriod_list.get(i);
        }
//        MergeSort obj = new MergeSort();
//        Point[] new_array = obj.merge_sort_Point(array, 0, centriod_list.size()-1);
//         return new_array;
//         System.out.println("size of array of centriod: " + array.length);
         return array;
    }

    // INPUT [x,y,z]
     public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates){
//        System.out.println("list of all points:");
//        for(int i=0; i<all_points.size(); i++){
//            System.out.println("ont of the points:" + all_points.get(i));
//        }
//        System.out.println("end of all points");
        Point A = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
//         System.out.println("face neighbours of A are: " + A.face_neighbours.size());
        for(int i=0; i< all_points.size(); i++){
            Point point = all_points.get(i);
            if(point.compareTo(A)==0){
                A= point;
                break;
            }
        }
        Triangle first_triangle = A.face_neighbours.get(0);
//        System.out.println("face neighbours of A are: " + A.face_neighbours.size());
        ArrayList1<Point> list = new ArrayList1<>();
        boolean [] visited= new boolean[all_triangles.size()];
        DFS_for_Points(first_triangle.count, visited, list);
         float X = 0; float Y = 0; float Z = 0;
         for( int j=0; j<list.size(); j++){
             X += list.get(j).X;
             Y += list.get(j).Y;
             Z += list.get(j).Z;
         }
         X = X/list.size();
         Y = Y/list.size();
         Z = Z/list.size();
         return new Point(X, Y, Z);
    }

//    public void DFS_for_distance(int count, boolean []visited, ArrayList<Triangle> list){
//        visited[count] = true;
//        Triangle tri_obj = all_triangles.get(count);
//        list.add(tri_obj);
//        int size_AB = tri_obj.AB.face_neighbours.size();
//        ArrayList<Triangle> list_AB = tri_obj.AB.face_neighbours;
//        for(int i=0; i<size_AB; i++){
//            int count1 = list_AB.get(i).count;
//            if(!visited[count1])
//                DFS(count1,visited);
//        }
//        int size_BC = tri_obj.BC.face_neighbours.size();
//        ArrayList<Triangle> list_BC = tri_obj.BC.face_neighbours;
//        for(int i=0; i<size_BC; i++){
//            int count1 = list_BC.get(i).count;
//            if(!visited[count1])
//                DFS(count1, visited);
//        }
//        int size_CA = tri_obj.CA.face_neighbours.size();
//        ArrayList<Triangle> list_CA = tri_obj.CA.face_neighbours;
//        for(int i=0; i<size_CA; i++){
//            int count1 = list_CA.get(i).count;
//            if(!visited[count1])
//                DFS(count1, visited);
//        }
//    }


     public 	PointInterface [] CLOSEST_COMPONENTS(){
         ArrayList1<Connected> connected_components = new ArrayList1<>();
        Point[] array = new Point[2];
        float shortest_distance = (float) Double.POSITIVE_INFINITY;
        boolean[] visited =  new boolean[all_triangles.size()];
        for(int i=0; i<all_triangles.size(); i++){
            if(!visited[i]){
//                ArrayList<Point> list = new ArrayList<>();
                Connected obj = new Connected();
                DFS_for_Points(i, visited, obj.point_components);
                connected_components.add(obj);
            }
        }
//         System.out.println("displaying components");
//        for(int i=0; i<connected_components.size(); i++){
//            ArrayList<Point> list = connected_components.get(i);
//            for (int j=0; j<list.size(); j++){
//                System.out.print(list.get(j) + " ");
//            }
//            System.out.println();
//        }
//         System.out.println("end display");
         if(connected_components.size()==1)
             return null;
        for(int first=0; first<connected_components.size()-1; first++){
//            System.out.println("entered in first loop");
            ArrayList1<Point> first_component = connected_components.get(first).point_components;
            for(int second = first+1; second < connected_components.size(); second++){
//                System.out.println("entered in second loop");
                ArrayList1<Point> second_component = connected_components.get(second).point_components;
                for(int first_count = 0; first_count<first_component.size(); first_count++){
//                    System.out.println("entered in third loop");
                        Point first_point = first_component.get(first_count);
                        for(int second_count= 0; second_count<second_component.size(); second_count++){
//                            System.out.println("entered in fourth loop");
                            Point second_point = second_component.get(second_count);
                            float [] vec = new float[3];
                            vec[0] = first_point.X - second_point.X;
                            vec[1]= first_point.Y - second_point.Y;
                            vec[2]= first_point.Z - second_point.Z;
                            float distance = magnitude(vec);
//                            System.out.println("one of the distance is " + distance);
                            if(distance<shortest_distance){
                                shortest_distance = distance;
                                array[0] = first_point;
                                array[1] = second_point;
                            }
                        }
                }
            }
        }
        return array;
    }


}

