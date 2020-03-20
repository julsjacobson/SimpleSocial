import java.util.*;

class Graph{
	int v; //number of vertices 
	LinkedList <User> users[];
	
	Graph (int v) {
		this.v = v; 
		users = new LinkedList[v]; 

		for(int i = 0; i < v; i++) {
			users[i] = new LinkedList<>();
		}
		
	}
	
	
	static void addFriend (Graph graph, User src, User dest) { 
        graph.users[src.getID()].add(dest); 
        graph.users[dest.getID()].add(src); 
    }
	
	
	boolean isFriends(User src, User dest) {
        return users[src.getID()].contains(dest);
    }
	
	void removeFriend  (Graph graph, User src, User dest) {
		
		Iterator<User> it = users[src.getID()].iterator();
		
        while (it.hasNext()) {
            if (it.next() == dest) {
                it.remove();
                return;
            }
        }    
    }
	
	int getSize() {
		return users.length; 
	}
	
	int getID() {
		return v; 
	}

}