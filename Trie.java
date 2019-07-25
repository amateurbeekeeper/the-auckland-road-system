import java.util.ArrayList;
import java.util.List;


public class Trie {
	TrieNode root;
	List<TrieNode> trieNodes;
		
	public Trie() {
		root = new TrieNode(null, false);
		trieNodes = new ArrayList<TrieNode>();
			
	}

	public void add(char[] word, Road obj) {
		// Set	node to	the	root	 of	the	trie
		TrieNode node = root;
		boolean end = false;
		
		for (char c : word) {
			if(c == word[word.length-1]) end = true;
	
			//if (node’s	children	 do	not	contain	c)
			if(node.getChild(c) == null) 
				// create	a	new	child	of	node,	connecting	to	node via	 c
				node.addChild(c, new TrieNode(obj, end)); 
			// move 	node to	the	child	corresponding	to	c;
			node = node.getChild(c);
		}
		
		// add	obj into node.objects;
		node.add(obj);
	}
	
	public List<Road> get(char[] word) {
		// Set	node to	the	root	 of	the	trie;
		TrieNode node = root;
		
		for (char c : word) {
			//if (node’s	children	 do	not	contain	c)
			if(node.getChild(c) == null) 
				return null;
			// move	 node to 	the	child	corresponding	to	c
			node = node.getChild(c);
		}
		
		return node.getObjects();
	}

	public List<Road> getAll(char[] prefix) {
		List<Road> results = new ArrayList<Road>();
		// Set	node to	the	root	 of	the	trie;
		TrieNode node = root;
		
		for (char c : prefix) {
			// if (node’s	children	 do	not	contain	c)
			if(node.getChild(c) == null) 
				return null;
			//move	node to	the	child	corresponding	to	c
			node = node.getChild(c);
		}
		
		getAllFrom(node, results);
		return results;
	}
	
	public void getAllFrom(TrieNode node, List<Road> results) {
		// add	node.objects into	results;
		results.addAll(node.getObjects());
		
		for (TrieNode child : node.getChildren())
			getAllFrom(child, results);

	}	
	
}

