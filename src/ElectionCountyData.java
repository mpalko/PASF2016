import java.util.HashMap;
import java.util.Map;

public class ElectionCountyData {
	Map<String, Double> dataMap;
	String countyName;
	
	public ElectionCountyData(String countyName){
		this.countyName = countyName.toUpperCase();
		dataMap = new HashMap<String, Double>();
		
	}
	
	public static ElectionCountyData parseData(String county, String[] headers, String[] data){
		ElectionCountyData ccd = new ElectionCountyData(county);
		for(int i = 1; i < Math.min(headers.length, data.length); i++){
			String head = headers[i].replaceAll(" ", "").toUpperCase();
			String value = data[i];
			try{
				ccd.dataMap.put(head, Double.parseDouble(value));
			}
			catch(NumberFormatException e){
				ccd.dataMap.put(head.toUpperCase(), null);
			}
		}
		
		return ccd;
	}

	public Double get(String string) {
		if(dataMap.containsKey(string.toUpperCase())){
			return dataMap.get(string.toUpperCase());
		}
		return null;
	}
}
