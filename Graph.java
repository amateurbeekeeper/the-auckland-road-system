import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph extends GUI {
  static int ZOOM = 5;
  static int MOVE = 1;

  Map<Integer, Node> nodes;
  ArrayList<Edge> edges;
  Map<Integer, Road> roads;
  Reader reader;

  double scale;
  Location origin;
  List<Location> clicks;

  Trie trie;

  Articulation articulation;

  Node start;
  Node target;

  public Graph() {
    trie = new Trie();
    scale = 200.0;
    origin = new Location(-2.012, 11.515); // 200.0
    nodes = new HashMap<Integer, Node>();

    start = null;
    target = null;

    File edgesF = new File("../The Auckland Road System/src/data/large/roadSeg-roadID-length-nodeID-nodeID-coords.tab");
    File roadsF = new File("../The Auckland Road System/src/data/large/roadID-roadInfo.tab");
    File nodesF = new File("../The Auckland Road System/src/data/large/nodeID-lat-lon.tab");
    File restrictionsF = new File("../The Auckland Road System/src/data/large/restrictions.tab");

    reader = new Reader(nodesF, roadsF, edgesF, restrictionsF);
    edges = reader.getEdges();
    nodes = reader.getNodes();
    roads = reader.getRoads();

    for (Road road : roads.values())
      trie.add(road.getName().toCharArray(), road);
  }

  @Override
  protected void redraw(Graphics g) {
    if (reader == null)
      return;

    for (Node n : nodes.values()) {
      n.draw(g, origin, scale);
    }

    for (Edge e : edges) {
      e.draw(g, origin, scale);
    }
  }

  public void unhighlight() {
    for (Node n : nodes.values()) {
      n.unhighlight();
    }
    for (Edge edge : edges) {
      edge.unhighlight();
    }
  }

  public void articulate() {
    articulation = new Articulation(nodes.values());

    Set<Node> points = articulation.getPoints();

    getTextOutputArea().setText("Articulation Points: " + points.size());

    for (Node n : points) {
      if (n != null)
        n.highlight(Color.MAGENTA);
    }
  }

  @Override
  protected void onClick(MouseEvent e) {
    Location loc = Location.newFromPoint(e.getPoint(), origin, scale);

    if (edges == null)
      return;

    unhighlight(); // UNHIGHLIGHT EVERYTHING

    if (getNode(loc) == null)
      return;

    if (start == null) {

      getNode(loc).highlight(Color.CYAN);
      start = getNode(loc);

    } else if (target == null) {
      ArrayList<Node> path;

      getNode(loc).highlight(Color.CYAN);
      target = getNode(loc);

      if (target == null | start == null)
        return;

      path = new A().findPath(start, target);

      if (path != null) {
        highlightPath(path);
        displayInformation(path, start, target);

      }

      target = null;
      start = null;

    }

  }

  public void displayInformation(ArrayList<Node> path, Node s, Node f) {
    Map<String, Double> info = new HashMap<String, Double>();
    String infoStr = "Path From: " + s.getID() + " to " + f.getID() + "\n";
    Double totalDistance = 0.0;

    // FOR THE NODES OF THE PATH
    for (int i = 0; i < path.size(); i++) {
      Node n = path.get(i);
      // FOR THE INCOMING EDGES OF THIS NODE
      for (Edge e : n.getIncoming()) {
        // GET THE NODE @ THE OTHER END
        Node neigh = e.getFrom();

        // IF THAT NODE IS FROM THE PATH
        if (path.contains(neigh)) {
          String name = roads.get(e.getID()).getName();

          if (info.containsKey(name)) { // UPDATE LENGTH
            Double newLength = info.get(name).doubleValue() + (double) e.getLength();

            info.put(name, newLength);
          } else { // CREATE ENTRY
            info.put(name, (double) e.getLength());
          }
        }
      }
    }

    for (Map.Entry<String, Double> entry : info.entrySet()) {
      String name = entry.getKey();
      Double distance = entry.getValue();
      totalDistance += distance;
      DecimalFormat df = new DecimalFormat("#.##");
      distance = Double.valueOf(df.format(distance));

      infoStr += "- " + name + ": " + distance + "km\n";
    }

    DecimalFormat df = new DecimalFormat("#.##");
    totalDistance = Double.valueOf(df.format(totalDistance));
    infoStr += "Total Distance: " + totalDistance + "km";

    getTextOutputArea().setText(infoStr);
  }

  public void highlightPath(ArrayList<Node> path) {
    // NOTE: PATH ORDER FROM TARGET TO START

    // FOR THE NODES OF THE PATH
    for (int i = 0; i < path.size(); i++) {
      Node n = path.get(i);
      n.highlight();
      // FOR THE INCOMING EDGES OF THIS NODE
      // for (Edge e : n.getIncoming()) {
      // GET ALL NEIGHBOUR NODES
      for (Node neigh : n.getNeighbours()) {

        // IF PATH HAS THAT NEIGH - HIGHLIGHT THAT EDGE BETWEEN THE TWO
        if (path.contains(neigh)) {
          Edge edge = null;

          // GET EDGE BETWEEN NODE AND NEIGH
          for (Edge e : neigh.getAllEdges()) {
            // EDGE HAS CURRENT NODE AND NEIGHBOUR
            if (e.getNodes().contains(n) && e.getNodes().contains(neigh)) {
              // THIS IS THE JOINING EDGE
              edge = e;
              break;
            }
          }
          edge.highlight();

        }

      }

    }

  }

  public Node getNode(Location loc) {
    for (Node n : nodes.values()) {
      if (n.getLocation().isClose(loc, 0.1)) {
        return n;
      }
    }

    return null;
  }

  @Override
  protected void onSearch() {
    ArrayList<Road> roads = (ArrayList<Road>) trie.getAll(getSearchBox().getText().toCharArray());
    ArrayList<String> roadNames = new ArrayList<>();
    ArrayList<Integer> roadIDs = new ArrayList<>();
    boolean exactMatch = false;

    if (roads == null)
      return;

    // GET ROAD NAMES & IDS
    for (Road road : roads) {
      roadIDs.add(road.getID());
      if (!roadNames.contains(road.getName()))
        roadNames.add(road.getName());
    }

    // IF EXACT MATCH WITH ADDITIONAL SUGGESTIONS
    if (roadNames.contains(getSearchBox().getText().toString())) {
      exactMatch = true;

      roadNames.clear();
      roadNames.add(getSearchBox().getText().toString());
      roadIDs.clear();

      for (Road road : roads) {
        if (road.getName().equals(getSearchBox().getText().toString()))
          roadIDs.add(road.getID());
      }

    }

    // IF EXACT MATCH WITHOUT ADDITIONAL SUGGESTIONS
    if (roadNames.size() == 1 && getSearchBox().getText().equals(roads.get(0).getName()) || exactMatch) {

      for (Edge edge : edges) {
        edge.unhighlight();
        for (int id : roadIDs) {
          if (edge.getID() == id) {
            edge.highlight();
            scale = 200;
            origin = edge.getLocation();
          }
        }
      }
      // HIGHLIGHT PREFIXES
    } else {
      for (Edge edge : edges) {
        edge.unhighlight();
        for (int id : roadIDs) {
          if (edge.getID() == id) {
            edge.highlight();

          }
        }
      }
    }

    getTextOutputArea().setText(roadNames.toString());

  }

  @Override
  protected void onMove(Move m) {
    if (m == Move.ZOOM_OUT && scale >= 3.0) {
      scale -= ZOOM;
    } else if (m == Move.ZOOM_IN) {
      scale += ZOOM;
    }

    if (m == Move.NORTH) {
      origin = origin.moveBy(0, MOVE);

    } else if (m == Move.SOUTH) {
      origin = origin.moveBy(0, -MOVE);

    } else if (m == Move.EAST) {
      origin = origin.moveBy(MOVE, 0);

    } else if (m == Move.WEST) {
      origin = origin.moveBy(-MOVE, 0);
    }
  }

  @Override
  protected void onLoad(File nodesFile, File roadsFile, File edgesFile, File polygonsFile) {

    articulate();

    // reader = new Reader(nodesFile, roadsFile, edgesFile, polygonsFile);

    // edges = reader.getEdges();
    // nodes = reader.getNodes();
    // roads = reader.getRoads();
    //
    // for (Road road : roads.values())
    // trie.add(road.getName().toCharArray(), road);
  }

  public static void main(String[] args) {
    new Graph();
  }
}
