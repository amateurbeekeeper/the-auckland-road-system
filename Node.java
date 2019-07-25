import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jdk.nashorn.internal.ir.SetSplitState;

public class Node {
  private static final int SIZE = 4;
  int id;
  ArrayList<Edge> incoming;
  ArrayList<Edge> outgoing;
  Color colour;
  float latitude;
  float longitude;
  Point pixel;
  Location location;
  int x;
  int y;

  // path finding..
  int depth = Integer.MAX_VALUE;
  public boolean visited = false;
  ArrayList<Restriction> restrictions;

  public Node(int iD, float lat, float lon) {
    id = iD;
    latitude = lat;
    longitude = lon;

    location = Location.newFromLatLon(latitude, longitude);

    incoming = new ArrayList<Edge>();
    outgoing = new ArrayList<Edge>();
    restrictions = new ArrayList<Restriction>();

    colour = Color.RED;
  }

  public void addIncomingEdge(Edge edge) {
    incoming.add(edge);
  }

  public void visit() {
    this.visited = true;
  }

  public void addOutgoingEdge(Edge edge) {
    outgoing.add(edge);
  }

  public void draw(Graphics g, Location origin, double scale) {
    pixel = location.asPoint(origin, scale);

    x = (int) pixel.getX();
    y = (int) pixel.getY();

    g.setColor(colour);
    g.fillRect(x, y, SIZE, SIZE);
  }

  public Location getLocation() {
    return location;
  }

  public void unhighlight() {
    colour = Color.RED;
  }

  public void highlight(Color color) {
    this.colour = color;
  }

  public void highlight() {
    colour = Color.GREEN;
  }

  public int getID() {
    return id;
  }

  public ArrayList<Integer> getRoadIDs() {
    ArrayList<Integer> roadIDs = new ArrayList<Integer>();

    for (Edge e : outgoing)
      roadIDs.add(e.getID());

    for (Edge e : incoming)
      roadIDs.add(e.getID());

    return roadIDs;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((colour == null) ? 0 : colour.hashCode());
    result = prime * result + id;
    result = prime * result + ((incoming == null) ? 0 : incoming.hashCode());
    result = prime * result + Float.floatToIntBits(latitude);
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + Float.floatToIntBits(longitude);
    result = prime * result + ((outgoing == null) ? 0 : outgoing.hashCode());
    result = prime * result + ((pixel == null) ? 0 : pixel.hashCode());
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  public boolean equals(Node obj) {
    if (obj == null)
      return false;

    Node other = (Node) obj;
    if (id == other.id)
      return true;

    return false;
  }

  public ArrayList<Edge> getAllEdges() {
    Set<Edge> neighboursSet = new HashSet<>();
    neighboursSet.addAll(incoming);
    neighboursSet.addAll(outgoing);

    ArrayList<Edge> neighbours = new ArrayList<Edge>(neighboursSet);

    return neighbours;

  }

  public ArrayList<Node> getNeighbours() {
    ArrayList<Node> nodes = new ArrayList<>();
    for (Edge e : outgoing) {
      nodes.add(e.getTo());
    }
    for (Edge e : incoming) {
      nodes.add(e.getFrom());
    }

    return nodes;

  }

  @Override
  public String toString() {
    return "Node [id=" + id + "]";
  }

  public ArrayList<Edge> getOutgoing() {
    // TODO Auto-generated method stub
    return outgoing;
  }

  public ArrayList<Edge> getIncoming() {
    // TODO Auto-generated method stub
    return incoming;
  }

  public void setDepth(int i) {
    // TODO Auto-generated method stub
    depth = i;
  }

  public int getDepth() {
    return depth;
  }

  public void addRestriction(Restriction restriction) {
    // TODO Auto-generated method stub

    this.restrictions.add(restriction);

  }
  
  public boolean restriction(Node fromNode, Node toNode) {
    
    if(fromNode == null) return false;
    
    for(Restriction res : restrictions) {
      if(res.check(fromNode, toNode))
        return true;
      
    }
    
    return false;
    
  }

  public Restriction getRestriction(Node fromNode, Node toNode) {
        
    for(Restriction res : restrictions) {
      if(res.check(fromNode, toNode))
        return res;
      
    }
    
    return null;
  }

  public void setUnvisited() {
    // TODO Auto-generated method stub
    this.visited = false;
  }

}
