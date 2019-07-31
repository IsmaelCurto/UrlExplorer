
public class UrlList {
	
	private String[] urls;
	private int index;
	private boolean visited;
	
	public UrlList(boolean v) {
		urls = new String[100000];
		index = 0;
		visited = v;
	}
	
	public void add(String u) {
		if(u!=null) {
			if(find(u)==-1) {
				if(index<urls.length) {
					urls[index]=u;
				} else {
					String[] urls2 = new String[urls.length+1];
					for(int i=0;i<urls.length;i++) {
						urls2[i]=urls[i];
					}
					urls2[index]=u;
					urls=urls2;
				}
				index++;
				
			}
		}
	}
	
	public int find(String u) {
		boolean found = false;
		int i=0;
		if(u!=null) {
			while(i<index && !found) {
				if(urls[i].equals(u)) {
					found = true;
				} else {
					i++;
				}
			}
		}
		if(found) {
			if(!visited) {
				if(DomainExplorer.visited_urls.find(u)==-1) {
					return -1;
				} else {
					return i;
				}
			} else {
				return i;
			}
		} else {
			return -1;
		}
	}
	
	public void delete(String u) {
		if(u!=null) {
			int n = find(u);
			if(n>-1) {
				for(int i=n;i<urls.length;i++) {
					if(i<urls.length-1) {
						urls[i]=urls[i+1];
					}
				}
				index--;
			}
		}
	}
	
	public String getFirst() {
		String u = urls[0];
		for(int i=0;i<urls.length;i++) {
			if(i<urls.length-1) {
				urls[i]=urls[i+1];
			}
		}
		index--;
		return u;
	}
	
	public String get(int i) {
		return urls[i];
	}
	
	public int amount() {
		return index;
	}

}
