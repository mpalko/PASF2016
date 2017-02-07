import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

public class CountyDataCensus {
	public static void main(String[] args) throws FileNotFoundException{
		FileOutputStream f = new FileOutputStream("file.txt");
		System.setOut(new PrintStream(f));
		String currentDir = System.getProperty("user.dir");
		CensusData cd1990 = new CensusData(currentDir + "/data/Census1990.csv", 1990);
		CensusData cd2000 = new CensusData(currentDir + "/data/Census2000.csv", 2000);
		CensusData cd2010 = new CensusData(currentDir + "/data/Census2010.csv", 2010);
		
		CensusData[] censi = {cd1990, cd2000, cd2010};
		
		Calendar election1988 = Calendar.getInstance();
		election1988.set(1988, Calendar.NOVEMBER, 8);
		
		Calendar election1992 = Calendar.getInstance();
		election1992.set(1992, Calendar.NOVEMBER, 3);
		
		Calendar election1996 = Calendar.getInstance();
		election1996.set(1996, Calendar.NOVEMBER, 5);
		
		Calendar election2000 = Calendar.getInstance();
		election2000.set(2000, Calendar.NOVEMBER, 7);
		
		Calendar election2004 = Calendar.getInstance();
		election2004.set(2004, Calendar.NOVEMBER, 2);
		
		Calendar election2008 = Calendar.getInstance();
		election2008.set(2008, Calendar.NOVEMBER, 4);
		
		Calendar election2012 = Calendar.getInstance();
		election2012.set(2012, Calendar.NOVEMBER, 6);
		
		Calendar election2016 = Calendar.getInstance();
		election2016.set(2016, Calendar.NOVEMBER, 8);
		
		Calendar[] dates = {election1988, election1992, election1996, election2000, election2004, election2008, election2012, election2016};
		
		ElectionData ed1988 = new ElectionData(currentDir+"/data/Election1988.csv", election1988);
		ElectionData ed1992 = new ElectionData(currentDir+"/data/Election1992.csv", election1992);
		ElectionData ed1996 = new ElectionData(currentDir+"/data/Election1996.csv", election1996);
		ElectionData ed2000 = new ElectionData(currentDir+"/data/Election2000.csv", election2000);
		ElectionData ed2004 = new ElectionData(currentDir+"/data/Election2004.csv", election2004);
		ElectionData ed2008 = new ElectionData(currentDir+"/data/Election2008.csv", election2008);
		ElectionData ed2012 = new ElectionData(currentDir+"/data/Election2012.csv", election2012);
		ElectionData ed2016 = new ElectionData(currentDir+"/data/Election2016.csv", election2016);
		
		ElectionData[] elections = {ed1988, ed1992, ed1996, ed2000, ed2004, ed2008, ed2012, ed2016};
		
		System.out.print("County\t");
		int n = 0;
		for(String demographic : cd2000.countyMap.get("ADAMS").dataMap.keySet()){
			if(demographic.equals("NOTES:")){
				continue;
			}
			System.out.print(demographic+"\t");
		}
		System.out.println();
			for(String county : cd2010.countyMap.keySet()){
				for(int i = 0; i < elections.length-1;i++){
				System.out.print(county+"\t");
				if(county.length() < 8){
					//System.out.print("\t");
				}
				
				for(String demographic : cd2000.countyMap.get("ADAMS").dataMap.keySet()){
					if(demographic.equals("NOTES:")){
						continue;
					}
					Double v1 = CensusData.interpolateDasGupta(getBestCensi(dates[i], censi)[0], getBestCensi(dates[i], censi)[1], county, demographic, dates[i]);
					Double v2 = CensusData.interpolateDasGupta(cd2000, cd2010, county, demographic, election2016);
					System.out.print(String.format("%.4f\t", v2-v1));
				}
				
				Double e2 = ed2016.get(county, "PercentVotedRepublican");
				Double e1 = elections[i].get(county, "PercentVotedRepublican");
				System.out.println(e2-e1);
			}
		}
	}
	
	public static CensusData[] getBestCensi(Calendar date, CensusData[] censi){
		if(date.getTimeInMillis() < censi[0].date.getTimeInMillis()){
			return new CensusData[]{censi[0], censi[1]};
		}
		for(int i = 0; i < censi.length-1; i++){
			if(date.getTimeInMillis() > censi[i].date.getTimeInMillis()){
				return new CensusData[]{censi[i], censi[i+1]};
			}
		}
		return new CensusData[]{censi[censi.length-2], censi[censi.length-1]};
	}
}