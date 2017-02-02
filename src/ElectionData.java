import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ElectionData {
	private Calendar date;
	private Map<String, ElectionCountyData> countyMap;
	
	public ElectionData(String filename, Calendar date){
		countyMap = new HashMap<>();
		this.date = Calendar.getInstance();
		this.date = date;
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
			countyMap.put(data[0].toUpperCase(), ElectionCountyData.parseData(data[0], headers, data));
		}
	}
	
	public Double get(String county, String data){
		county = county.toUpperCase();
		if(countyMap.containsKey(county)){
			return countyMap.get(county).get(data.replaceAll(" ", "").toUpperCase());
		}
		return null;
	}
}
