import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//import org.json.*;
		
import java.net.*;
import java.io.*;

public class bocCollector {
	
	public static void main(String args[]) throws IOException{

		//	/*
		String searchWords = "drilling, fracking, pumping, oil sands, off-shore drilling, jewlery, oil change, lubricants, blacksmithing, ironwork, glass, pottery, coins, completing wells, equipping wells, operating separators, emulsion breakers, desilting equipment, crude petroleum, crushing minerals, grinding minerals, washing minerals";
		File file = new File("cat21.csv");
      	file.createNewFile();
      	saveLinks(searchWords,file);
		//	*/

		//After Completed
			/*
		int saved = saveData(file);
		if(saved == 1) System.out.print("Data csv created!");
			*/

	}

	public static void saveData(File file) throws IOException{
		ArrayList<String> dataList = new ArrayList<String>();

		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);

		String line;
		String cvsSplitBy = "&&&&&&&&&&&&&";
		while((line = reader.readLine()) != null ){
			String[] splited = line.split(cvsSplitBy);
			String word = splited[1].replaceAll("// Data table response\n","").replaceAll("google.visualization.Query.setResponse(","").replaceAll(");","");
			parseJson(word);
		}
		


	}

	public void parseJson(String jsonText){

	}

	public static void saveLinks(String searchWords,File file) throws IOException{
		String[] searchArrays = searchWords.split(", ");

		int i = 0;
		boolean notDone = true;

		

		FileWriter fileWriter = new FileWriter(file);
		try{
			
			//fileWriter.write("Link");
			//fileWriter.write("\n");

			//System.out.println("Entered Writer.");

			ArrayList<String> links = new ArrayList<String>();

			while(notDone){
				int lastIndex = i;
				String searchterms = "";
				int help = 0;

				while(help < 5 && i < searchArrays.length){
					searchterms +=  "" + searchArrays[i].replaceAll(" ", "&20")+",";
					i++; 
					help++;
				}

				String trendsURL = new String("\"http://www.google.com/trends/fetchComponent?q="+searchterms.substring(0, searchterms.length()-1)+"&geo=CA&cid=TIMESERIES_GRAPH_0&export=3&&&&&&&&&&&&&\"");
				

				//System.out.println(trendsURL);
				links.add(trendsURL);

				if(i >= searchArrays.length) notDone = false;
			}

			//System.out.println("The generator made links.");

			for(String link : links){
					fileWriter.append(link);
					fileWriter.append("\n");
					//System.out.println("Appended: "+link);
			}

			

			fileWriter.flush();
			fileWriter.close();
			System.out.println("CSV file was created successfully !!!");
			
		}catch(Exception e){
			System.out.println("Error in CsvFileWriter !!!");
		}
	}

}