import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class test {
  
  Graph graph = new Graph();

  @Test
  void testRestrictions() {
    
    Set<Restriction> restrictions = new HashSet<>();
    
    for(Node n1 : graph.nodes.values()) {
      for(Node n2 : graph.nodes.values()) {
        for(Node n3  : graph.nodes.values())
          if(n1.restriction(n2, n3)) {
            restrictions.add(n1.getRestriction(n2, n3));
          }
      }
    }
    
    assertEquals(226, restrictions.size());
    
  }
  
  

}
