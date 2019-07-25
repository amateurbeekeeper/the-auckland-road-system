import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {
  List<String> files;
  Map<Integer, Road> roads;
  Map<Integer, Node> nodes;
  ArrayList<Edge> edges;
  ArrayList<Restriction> restrictions;

  public Reader(File nodesFile, File roadsFile, File edgesFile, File restrictionsFile) {
    roads = new HashMap<>();
    nodes = new HashMap<>();
    edges = new ArrayList<>();
    restrictions = new ArrayList<>();

    read(nodesFile);
    read(roadsFile);
    read(edgesFile);
    if(restrictionsFile != null)
      readRestrictions(restrictionsFile);

  }

  public void readRestrictions(File file) {
    String lineJustFetched = null;
    String[] line;
    Boolean firstLine = true;

    try {
      BufferedReader buf = new BufferedReader(new FileReader(file));

      while (true) {
        lineJustFetched = buf.readLine();

        if (firstLine) {
          firstLine = false;
          continue;
        }

        if (lineJustFetched == null) {
          break;
        } else {

          line = lineJustFetched.split("\t");

          // nodeID-1, roadID-1, nodeID, roadID-2, nodeID-2.
          // Restriction restriction = new Restriction(
          // nodes.get(Integer.parseInt(line[0])), // nodeID-1
          // roads.get(Integer.parseInt(line[1])), // roadID-1
          // nodes.get(Integer.parseInt(line[2])), // nodeID
          // roads.get(Integer.parseInt(line[3])), // roadID-2
          // nodes.get(Integer.parseInt(line[4]))); // nodeID-2

          Restriction restriction = new Restriction(nodes.get(Integer.parseInt(line[0])),
              nodes.get(Integer.parseInt(line[4]))); // nodeID-2

          // add road obj to edge
          nodes.get(Integer.parseInt(line[2])).addRestriction(restriction);

        }
      }
      buf.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void read(File file) {
    String lineJustFetched = null;
    String[] line;
    Boolean firstLine = true;

    if (file.getName().equals("nodeID-lat-lon.tab")) {
      firstLine = false;
    }

    try {
      BufferedReader buf = new BufferedReader(new FileReader(file));

      while (true) {
        lineJustFetched = buf.readLine();

        if (firstLine) {
          firstLine = false;
          continue;
        }

        if (lineJustFetched == null) {
          break;
        } else {

          line = lineJustFetched.split("\t");

          // NODES
          if (file.getName().equals("nodeID-lat-lon.tab")) {
            Node n = new Node(Integer.parseInt(line[0]), Float.parseFloat(line[1]), Float.parseFloat(line[2]));
            nodes.put(Integer.parseInt(line[0]), n);

            // EDGES
          } else if (file.getName().equals("roadSeg-roadID-length-nodeID-nodeID-coords.tab")) {
            // ADD COORDS TO A LIST
            List<Float> coords = new ArrayList<Float>();
            for (int i = 4; i < line.length; i++) {
              coords.add(Float.parseFloat(line[i]));
            }

            Edge e = new Edge(Integer.parseInt(line[0]), Float.parseFloat(line[1]), coords);
            edges.add(e);

            // add road obj to edge
            e.setRoad(roads.get(Integer.parseInt(line[0])));

            // add edges to node
            nodes.get(Integer.parseInt(line[2])).addOutgoingEdge(e);
            if (line[3] != null) {
              nodes.get(Integer.parseInt(line[3])).addIncomingEdge(e);
            }

            // add nodes to edge
            e.setFrom(nodes.get(Integer.parseInt(line[2])));
            if (line[3] != null) {
              e.setTo(nodes.get(Integer.parseInt(line[3])));
            }

            // ROADS
          } else if (file.getName().equals("roadID-roadInfo.tab")) {
            Road r = new Road(Integer.parseInt(line[0]), Integer.parseInt(line[1]), line[2], line[3],
                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]),
                Integer.parseInt(line[7]), Integer.parseInt(line[8]), Integer.parseInt(line[9]));

            roads.put(Integer.parseInt(line[0]), r);
          }
        }
      }
      buf.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Restriction> getRestrictions() {
    return restrictions;
  }

  public ArrayList<Edge> getEdges() {
    return edges;
  }

  public Map<Integer, Node> getNodes() {
    return nodes;
  }

  public Map<Integer, Road> getRoads() {
    return roads;
  }
}
