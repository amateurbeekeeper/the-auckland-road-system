import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class A {
  Map<Node, ANode> visited;
  PriorityQueue<ANode> queue;
  Node start;
  Node target;
  boolean time = false;

  /**
   * Distance: Euclidean distance
   * Time: distance divided by speed limit 
   * Admissable: (never over estimates)
   */
  public float heuristic(Node n, Edge e) {

    float distance = (float) n.getLocation().distance(target.getLocation());
    float s = 130;

    if (time && e != null) { 
      s = e.getRoad().getSpeed();
      
      Float[] cc = new Float[] {(float) 0.7, (float)0.9, (float)1.0,(float) 1.1, (float)1.3};
      int roadClass = e.getRoad().getRoadclass();
      
      //should be lower.
      
      // multiplies speed by road class as able to go faster on a 
      // road with a higher class
   

      
      
      if (s == 0)
        s = 5 * cc[roadClass];
      else if (s == 1)
        s = 20 * cc[roadClass];
      else if (s == 2)
        s = 40 * cc[roadClass];
      else if (s == 3)
        s = 60 * cc[roadClass];
      else if (s == 4)
        s = 80 * cc[roadClass];
      else if (s == 5)
        s = 100 * cc[roadClass];
      else if (s == 6)
        s = 110 * cc[roadClass];
      else if (s == 7)
        s = 130 * cc[roadClass];

      distance = distance/s;

    }

    return distance;
  }

  public ArrayList<Node> findPath(Node start, Node target) {
    queue = new PriorityQueue<>();
    visited = new HashMap<Node, ANode>();
    ArrayList<Node> path = null;
    ANode anode = null;
    Node node = null;
    
    if(start.getAllEdges().size() == 1 
        && start.getAllEdges().get(0).getRoad().getNotforcar() == 1) return path;

    if (start.equals(target))
      return path;

    this.start = start;
    this.target = target;
    anode = new ANode(start, null, 0, heuristic(start, null));

    queue.add(anode);

    while (!queue.isEmpty()) {
      anode = queue.poll();
      node = anode.getNode();

      // IF NOT VISITED
      if (visited.get(node) == null) {
        // MARK AS VISITED
        visited.put(node, anode);

        // IF TARGET - BREAK
        if (node.equals(target))
          break;

        // FOR NEIGH.
        for (Node neigh : node.getNeighbours()) {
          Edge edge = null;

          if (neigh.equals(node))
            continue;

          // GET EDGE : LENGTH
          for (Edge e : neigh.getAllEdges()) {
            // IF EDGE CONTAINS NODE AND NEIGH. IT IS CURRENT EDGE
            if (e.getNodes().contains(node) && e.getNodes().contains(neigh))
              edge = e;
          }

          // CARS PATH ONLY
          if (edge.getRoad().getNotforcar() == 1) break;

          // RESTRICTION : given previous node and next node - 227 total
          if (node.restriction(visited.get(node).getPrev(), neigh)) break;

          // ONE WAY

          if (visited.get(neigh) == null) {
            // CALCULATE VALUES
            float g = anode.g() + edge.getLength();
            float f = g + heuristic(neigh, edge);
            // ADD TO QUEUE
            queue.add(new ANode(neigh, node, g, f));
          }
        }

      } else { // VISITED: update costs, check target
        if (node.equals(target))
          break;
      }

    }

    return getPath(node);
  }

  public void printQueue(PriorityQueue<ANode> f) {
    System.out.println("----------------------------- QUEUE");
    for (int i = 0; i < f.toArray().length; i++) {
      System.out.println(f.toArray()[i]);
    }
    System.out.println("-----------------------------");
    System.out.println("LOWEST: " + queue.peek());
    System.out.println("----------------------------- QUEUE");

  }

  public ArrayList<Node> getPath(Node n) {
    ArrayList<Node> nodes = new ArrayList<>();

    Node node = n;

    try {
      while (node != null) {
        // ADD NODE
        nodes.add(node);

        // GET PREV NODE FROM ASTAR OF CURRENT NODE
        node = visited.get(node).getPrev();
      }

    } catch (NullPointerException e) {

      return nodes;
    }
    return nodes;
  }

}
