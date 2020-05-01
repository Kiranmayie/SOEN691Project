package datamining.visitors;


public class Node {
//	public MethodDeclaration Method;

	
	public String Method;
	public String Class;
	public String Package;
	public Node(String m, String c, String p) {
		super();
		Method = m;
		Class = c;
		Package = p;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node)obj;
		if(other.Class.equals(this.Class)&&other.Package.equals(this.Package)&&other.Method.equals(this.Method)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public int hashCode() {
		
		final int prime = 7;
		int result = 1;
		result = prime * result + ((Method == null) ? 0 : Method.hashCode());
		result = prime * result + ((Class == null) ? 0 : Class.hashCode());
		result = prime * result + ((Package == null) ? 0 : Package.hashCode());
		return result;

	}

}
