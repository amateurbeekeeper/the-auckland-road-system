import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class ArticulationNode {
  Node node;
  int reach;
  Queue<Node> children;
  ArticulationNode parent;

  public ArticulationNode(Node node, int reach, ArticulationNode parent) {
    this.node = node;
    this.parent = parent;
    this.reach = reach;
  }
  
 
//  public void setChildren(Queue<Node> children) {
//      this.children = children;
//  }
//  

  public void setReach(int reach) {
    this.reach = reach;
  }

  public Queue<Node> getChildren() {
    return this.children;
  }

  public int getReach() {
    return this.reach;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public ArticulationNode getParent() {
    return this.parent;
  }

  public void setParent(ArticulationNode parent) {
    this.parent = parent;
  }

  public Node getNode() {
    return this.node;
  }

  public void addChild(Node child) {
    this.children.add(child);

  }


  public void setChildren(ArrayDeque<Node> arrayDeque) {
    this.children = arrayDeque;
    
  }

}
