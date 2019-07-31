import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Page{
	
	private String mainurl, url, content;

	public Page(String u) {
		DomainExplorer.threads++;
		content = null;
		url = u;
		if(DomainExplorer.visited_urls.find(url)==-1) {
			load(url);
			if(content!="" && content!=null) {
				getUrls();
			}
		}
	}
	
	private void load(String u) {
		try {
			URLConnection connection = new URL(u).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();

			BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    sb.append(line);
			}
			content = sb.toString();
			
			mainurl = u.split("//")[0]+"//"+u.split("/")[2]+"/";
			
		} catch (Exception e) {
			//System.out.println("Error with '"+u+"' : "+e.getMessage());
			content = "";
		}
		
		DomainExplorer.visited_urls.add(u);
	}
	
	private void getUrls() {
		String url;
		String part;
		int urls = content.split("href=\"/").length;
		int i = 1, e=0;
		while(i<urls) {
			url = mainurl;
			part = content.split("href=\"/")[i];
			while(!String.valueOf(part.charAt(e)).equals(" ") && !String.valueOf(part.charAt(e)).equals("\"") && !String.valueOf(part.charAt(e)).equals("\'") && e<part.length()) {
				url = url+part.charAt(e);
				e++;
			}
			if(url.contains(mainurl+mainurl)) {
				url=mainurl+url.split(mainurl)[2];
			}
			DomainExplorer.unvisited_urls.add(url);
			i++;
			e=0;
		}
	}
	

}
