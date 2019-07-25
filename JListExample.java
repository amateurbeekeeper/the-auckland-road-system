 import javax.swing.DefaultListModel; 
import javax.swing.JFrame; 
import javax.swing.JList; 



/**
 * 
 * 
 * 
 * @author https://www.codejava.net/java-se/swing/jlist-basic-tutorial-and-examples
 *
 */
public class JListExample extends JFrame { 
    private JList<String> countryList;
    
    public JListExample() { 
        //create the model and add elements 
        DefaultListModel<String> listModel = new DefaultListModel<>(); 
        listModel.addElement("mayfair pl"); 
        listModel.addElement("mayfair cres"); 
        listModel.addElement("mayfair cl"); 
        listModel.addElement("mayfair dr"); 
        
        //create the list 
        countryList = new JList<>(listModel); 
        add(countryList); 
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setTitle("Suggestions");        
        this.setSize(200,200); 
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
    } 
    
       
}