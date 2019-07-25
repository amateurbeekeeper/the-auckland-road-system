import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Articulation {

  int MAX_VALUE = Integer.MAX_VALUE;

  int numSubTrees;
  Node root;
  ArrayList<Node> nodes;

  Set<Node> articulationPoints;

  public Articulation(Collection<Node> nodes) {
    this.nodes = new ArrayList<>(nodes);
    this.articulationPoints = new HashSet<>();
    this.numSubTrees = 0;

    // INIT ALL NODES
    for (Node node : nodes) {
      node.setDepth(MAX_VALUE);
      node.setUnvisited();
    }

  }

  /**
   * Get ArtPoints for entire graph
   * 
   * @return
   */
  public Set<Node> getPoints() {
    for (Node n : nodes) {
      if (!n.visited) {
        // SET ROOT TO 0
        n.setDepth(0);
        this.numSubTrees = 0;

        // EXPLORE ALL NEIGHBOURS OF ROOT (DEPTH 1)
        for (Node neighbour : n.getNeighbours()) {
          if (neighbour.getDepth() == MAX_VALUE) {
            iterArtPoints(neighbour, n);
            numSubTrees++;
          }

        }
        if (numSubTrees > 1) {
          articulationPoints.add(n);
        }

      }
      n.visit();
    }

    return articulationPoints;

  }

  /**
   * Get ArtPoints for specific root
   * 
   * @return
   */
  public Set<Node> getPointsFromNode(Node root) {
    this.root = root;

    // SET ROOT TO 0
    root.setDepth(0);
    this.numSubTrees = 0;

    // EXPLORE ALL NEIGHBOURS OF ROOT (DEPTH 1)
    for (Node neighbour : root.getNeighbours()) {
      if (neighbour.getDepth() == MAX_VALUE) {
        iterArtPoints(neighbour, root);
        numSubTrees++;
      }

    }
    if (numSubTrees > 1) {
      articulationPoints.add(root);
    }

    return articulationPoints;

  }

  public void iterArtPoints(Node start, Node root) {
    Stack<ArticulationNode> stack = new Stack<>();
    stack.push(new ArticulationNode(start, 1, new ArticulationNode(root, 0, null)));

    while (!stack.isEmpty()) {
      ArticulationNode elem = stack.peek();
      Node node = elem.getNode();

      if (elem.getChildren() == null) { // FIRST TIME
        node.setDepth(elem.getReach());
        elem.setChildren(new ArrayDeque<Node>());

        // add all neighbouring nodes to this node's children except for the parent
        for (Node neighbour : node.getNeighbours()) {
          if (!neighbour.equals(elem.getParent().getNode())) {
            elem.addChild(neighbour);
          }
        }
      } else if (!elem.getChildren().isEmpty()) { // HAS CHILDREN TO PROCESS
        Node child = elem.getChildren().poll();

        // if been to child before: update its reachback
        if (child.getDepth() < MAX_VALUE) {
          elem.setReach(Math.min(elem.getReach(), child.getDepth()));

        } else {
          // else: add it to the stack
          stack.push(new ArticulationNode(child, (node.getDepth() + 1), elem));
        }
      } else { // DONE PROCESSING / CREATING CHILDREN / IF CONSIDERED / LAST TIME

        // make sure doesnt enter this part if first time
        if (!node.equals(start)) {
          // if its reachback is higher than its parent's count,
          if (elem.getReach() >= elem.getParent().getNode().getDepth()) {
            // then add its parent to the list of points and update the reachback
            this.articulationPoints.add(elem.getParent().getNode());
          }
          elem.getParent().setReach(Math.min(elem.getParent().getReach(), elem.getReach()));
        }
        elem.getNode().visit();
        stack.pop();
      }
    }
  }
}
