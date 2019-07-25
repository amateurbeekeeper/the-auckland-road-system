/**
 * restrictions.tab ================ A restriction prohibits traveling through
 * one path of an intersection.
 * 
 * The file has one line for each restriction. Each line has five values:
 * nodeID-1, roadID-1, nodeID, roadID-2, nodeID-2.
 * 
 * The middle NodeID specifies the intersection involved. The restriction
 * specifies that it is not permitted to turn from the road segment of roadID-1
 * going between nodeID-1 and the intersection into the road segment of roadID-2
 * going between the intersection and nodeID-2
 * -----------------------------------------------------------------------------
 */

public class Restriction {

  Node fromNode;
  Node toNode;

  // public Restriction(Node nodeID1, Road roadID1, Node nodeID, Road roadID2, Node nodeID2) {
  public Restriction(Node fromNode, Node toNode) {

    this.fromNode = fromNode;
    this.toNode = toNode;
  }
  
  // road --> edges?
  
  public boolean check(Node fromNode, Node toNode ) {
    if (this.fromNode.equals(fromNode) && this.toNode.equals(toNode)) 
      return true;
    
    return false;
  }

}
