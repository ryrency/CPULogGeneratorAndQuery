package com.monitor.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CPUUsageGenerator {

	private static int HOURS_OF_DAY = 24 * 60;
	
	static int iMinCoreProcessors= 0;
    private static final Logger LOGGER = Logger.getLogger(CPUUsageGenerator.class.getName());
	static String startServerAddress[]= {"192.168.0.0","193.168.0.0","194.168.0.0","195.168.0.0"},endServerAddress[]
			= {"192.168.0.250","193.168.0.250","194.168.0.250","195.168.0.250"},runDate =null;

	static {
		LOGGER.info("Logger Name: "+LOGGER.getName());
		runDate = "2019-02-23";
		iMinCoreProcessors = 2;
	}
	
	public static void main(String args[]) {
		LOGGER.log(Level.INFO, " Generate CPU usage data begins ");
		
		for(int cntServer=0;cntServer<startServerAddress.length;cntServer++) {
			String[] startLastOctet = startServerAddress[cntServer].split("(?<=\\.)(?!.*\\.)");
			String[] endLastOctet = endServerAddress[cntServer].split("(?<=\\.)(?!.*\\.)");
			
			int firstLastOctet = Integer.parseInt(startLastOctet[1]);
			int lastOctet = Integer.parseInt(endLastOctet[1]);

			generateServerCPUUsage(firstLastOctet, lastOctet, args[0],runDate ,startLastOctet[0]);
			
		}
		
        LOGGER.log(Level.INFO, " Generation of Server CPU usage completed ");
        
	}

	/**
	 * Generates server data for last octet of IP address and
	 * for min CPU cores.
	 * @param firstOctet  - Start of Last octet of IP address
	 * @param lastOctet   - End of Last octet of IP address
	 * @param strFilePath - file path
	 * @param runDt       - Date for which the simulation has to run
	 * @param startLastOctet - Octect based on regular expression
	 * @return void
	 */
	private static void generateServerCPUUsage(int firstOctet, int lastOctet, String strFilePath, String runDt, String startLastOctet) {
		for (int i = firstOctet; i <= lastOctet; i++) {
			for (int j = 0; j < iMinCoreProcessors; j++) {

				String key = "Server_" + startLastOctet + i + "_" + j + "_";
				File file = null;
				Writer writer;
				final Calendar cal = Calendar.getInstance();

				try {
					file = File.createTempFile(key, ".txt", new File(strFilePath));
					writer = new OutputStreamWriter(new FileOutputStream(file));
					writer.write("TimeStamp\tIp Address\tCore\tCPU Usage\n");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					cal.setTime(sdf.parse(runDt));

					for (int k = 0; k <= HOURS_OF_DAY; k++) {
						cal.add(Calendar.MINUTE, 1);
						long unixTime = cal.getTimeInMillis() / 1000;
						writer.write(unixTime + "\t");
						writer.write(startLastOctet + i + "\t");
						writer.write(j + "\t");
						writer.write((int) Math.ceil(Math.random() * (100)) + "\t\n");
					}
					writer.close();

				} catch (FileNotFoundException fileEx) {
		            LOGGER.log(Level.SEVERE, "Error occured while reading file.", fileEx.getMessage());
				} catch (IOException ioEx) {
		            LOGGER.log(Level.SEVERE, "Error occur in File IO Handler.", ioEx.getMessage());
				} catch (ParseException parseEx) {
		            LOGGER.log(Level.SEVERE, "Error occured in file parsing.", parseEx.getMessage());
				}
			}
		}
	}
}
