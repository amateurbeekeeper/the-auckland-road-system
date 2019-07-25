import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TrieNode {
	private ArrayList<Road> objects;
	private HashMap<Character, TrieNode> children;
	private boolean endOfWord;
	
	public TrieNode(Road road, boolean end) {
		objects = new ArrayList<Road>();
		children = new HashMap<Character, TrieNode>();
		endOfWord = end;
		
	}

	public void addChild(char c, TrieNode node) {
		children.put(c, node);
		
	}

	public TrieNode getChild(char c) {
		if(children.get(c) == null || children.size() == 0) return null;
		
		return children.get(c);
	}

	public void add(Road obj) {
		objects.add(obj);
	}

	public ArrayList<Road> getObjects() {
		return objects;
	}

	public ArrayList<TrieNode> getChildren() {
		
		return new ArrayList<>(children.values());
	}

	public boolean isEndOfWord() {
		return endOfWord;
	}
	
}