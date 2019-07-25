import java.util.Collection;
import java.util.List;

public class ANode implements Comparable<ANode> {
  Node node;
  Node prev;
  float g;
  double f;

  public ANode(Node node, Node prev, float g, double f) {
    this.node = node;
    this.prev = prev;
    this.g = g;
    this.f = f;

  }

  public Node getNode() {
    return node;
  }

  public Node getPrev() {
    return prev;
  }

  public void setPrev(Node n) {
    prev = n;
  }

  public float g() {
    return g;
  }

  @Override
  public int compareTo(ANode n) {
    // fringe will put greatest priority at top
    if (this.f > n.f()) { // if f less, will have greater priority
      return 1;
    } else if (this.f < n.f()) { // if higher h
      return -1; // lower priority
    } else {
      return 0;
    }
  }

  public double f() {
    return f;
  }

  @Override
  public String toString() {
    return "ANode [node=" + node.getID() + ", prev=" + prev + ", g=" + g + ", f=" + f + "]";
  }

}
