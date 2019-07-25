import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Edge {
  int id;

  float length;
  Node from;
  Node to;
  Road road;

  List<Location> locations;

  int y;
  int x;
  Color color;

  public Edge(int id, float length, List<Float> coords) {
    color = Color.BLACK;
    locations = new ArrayList<Location>();

    this.id = id;
    this.length = length;

    for (int i = 0; i < coords.size(); i += 2) {
      locations.add(Location.newFromLatLon(coords.get(i), coords.get(i + 1)));
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Road getRoad() {
    return road;
  }

  public void setLength(float length) {
    this.length = length;
  }

  public void setRoad(Road road) {
    this.road = road;
  }

  public ArrayList<Node> getNodes() {
    ArrayList nodes = new ArrayList<Node>();
    nodes.add(from);
    nodes.add(to);

    return nodes;
  }

  public float getLength() {
    return length;
  }

  public int getID() {
    return id;
  }

  public void highlight() {
    color = Color.GREEN;
  }

  public void draw(Graphics g, Location origin, double scale) {
    g.setColor(color);

    for (int i = 0; i < locations.size() - 1; i++) {
      g.drawLine((int) locations.get(i).asPoint(origin, scale).getX(),
          (int) locations.get(i).asPoint(origin, scale).getY(),
          (int) locations.get(i + 1).asPoint(origin, scale).getX(),
          (int) locations.get(i + 1).asPoint(origin, scale).getY());
    }

  }

  public Location getLocation() {
    return locations.get(0);
  }

  public void unhighlight() {
    color = Color.BLACK;

  }

  public Node getFrom() {
    return from;
  }

  public void setFrom(Node from) {
    this.from = from;
  }

  public Node getTo() {
    return to;
  }

  public void setTo(Node to) {
    // System.out.println("Adding a node to this edge with the following ID: " +
    // to.getID());

    this.to = to;
  }

}
