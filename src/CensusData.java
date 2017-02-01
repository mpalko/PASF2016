import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CensusData {
	Calendar date;
	Map<String, CensusCountyData> countyMap;
	
	public CensusData(String filename, int year){
		countyMap = new HashMap<String, CensusCountyData>();
		this.date = Calendar.getInstance();
		date.set(year, 4, 1);
		try {
			parseData(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseData(String filename) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String[] headers = in.readLine().split(",");
		String dataLine;
		while((dataLine = in.readLine()) != null){
			String[] data = dataLine.split(",");
			countyMap.put(data[0], CensusCountyData.parseData(data[0], headers, data));
		}
	}
	
	public Double get(String county, String data){
		county = county.toUpperCase();
		if(countyMap.containsKey(county)){
			return countyMap.get(county).get(data.replace(" ", "").toUpperCase());
		}
		return null;
	}
}
