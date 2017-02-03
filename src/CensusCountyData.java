import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CensusCountyData {
	Map<String, Double> dataMap;
	String countyName;
	
	public CensusCountyData(String countyName){
		this.countyName = countyName.toUpperCase();
		dataMap = new HashMap<String, Double>();
		
	}
	
	public static CensusCountyData parseData(String county, String[] headers, String[] data){
		CensusCountyData ccd = new CensusCountyData(county.toUpperCase());
		for(int i = 1; i < Math.min(headers.length, data.length); i++){
			String head = headers[i].replaceAll(" ", "").toUpperCase();
			String value = data[i];
			try{
				ccd.dataMap.put(head, Double.parseDouble(value));
			}
			catch(NumberFormatException e){
				ccd.dataMap.put(head, null);
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