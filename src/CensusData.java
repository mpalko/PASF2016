import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CensusData {
	private Calendar date;
	public Map<String, CensusCountyData> countyMap;
	
	public CensusData(String filename, int year){
		countyMap = new HashMap<>();
		this.date = Calendar.getInstance();
		date.set(year, Calendar.APRIL, 1);
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
			countyMap.put(data[0].toUpperCase(), CensusCountyData.parseData(data[0], headers, data));
		}
	}
	
	public Double get(String county, String data){
		county = county.toUpperCase();
		if(countyMap.containsKey(county)){
			return countyMap.get(county).get(data.replaceAll(" ", "").toUpperCase());
		}
		return null;
	}
	
	public static Double interpolateDasGupta(CensusData data1, CensusData data2, String county, String demographic, Calendar date){
		long dbd = TimeUnit.DAYS.convert(data2.date.getTime().getTime() - data1.date.getTime().getTime(), TimeUnit.MILLISECONDS);
		long daysToToday = TimeUnit.DAYS.convert(date.getTime().getTime() - data1.date.getTime().getTime(), TimeUnit.MILLISECONDS);
		Double value1 = data1.get(county, demographic);
		Double value2 = data2.get(county, demographic);
		if(value1 == null || value2 == null){
			return null;
		}
		return value1 * Math.pow(value2 / value1, 1.0 * daysToToday/dbd); 
	}
}
