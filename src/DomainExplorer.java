import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DomainExplorer {
	
	public static UrlList visited_urls;
	public static UrlList unvisited_urls;
	public static int threads;
	private static String filename, url;
	private static String[] tosave;
	private static int sindex;
	
	
	private static void presave() throws IOException {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
			for(int i = 0;i<sindex;i++) {
				writer.append(tosave[i]+"\n");
				tosave[i]="";
			}
			writer.close();
			sindex=0;
	}
	
	private static void removeDuplicates() throws IOException {
		new File(filename).delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
		for(int i = 0;i<visited_urls.amount();i++) {
			writer.append(visited_urls.get(i)+"\n");
			tosave[i]="";
		}
		writer.close();
	}

	public static void main(String[] args) {
		try {
			if(args.length != 0) {
				url = args[0];
				if((url.contains("http://") || url.contains("https://")) && String.valueOf(url.charAt(url.length()-1)).equals("/")) {
					filename = url.split("://")[1].split("/")[0]+"-urls"+(int)(Math.random()*10000)+".txt";
					tosave = new String[11];
					sindex = 0;
					threads = 0;
					visited_urls = new UrlList(true);
					unvisited_urls = new UrlList(false);
					
					System.out.println(" [Starting job with "+url+" you can find the links at "+filename+" ]\n\n");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// do nothing
					}
					
					unvisited_urls.add(url);
					
					String u;
					while((u = unvisited_urls.getFirst())!=null) {
						
						new Page(u);
						
						tosave[sindex]=u;
						sindex++;
						if(sindex>10) {
							presave();
						}
						System.out.println("	visited: "+visited_urls.amount()+", unvisited: "+unvisited_urls.amount());
					}
					
					removeDuplicates();
					System.out.println(" [ Work finished for "+url+" with "+visited_urls.amount()+" urls found! ]");
				} else {
					System.out.println(" [ Url format is not correct ]");
				}
			} else {
				System.out.println(" [ Please, set a domain to start with. example: http://example.com/ ]");
			}
		} catch(Exception e) {
			System.out.println(" [ Fatal error, be sure you have admin rights if necessary. ]");
		}
		System.exit(0);
		
	}

}
