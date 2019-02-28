package com.query.logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryLogs {
	private static String filePath, ipAddr, serverCore, dateFrom, dateFromTime, dateTo, dateToTime = null;
	private static final Logger LOGGER = Logger.getLogger(QueryLogs.class.getName());

	
	public static void main(String argShell[]) {
		filePath = argShell[0];
		ipAddr = argShell[1];
		serverCore = argShell[2];
		dateFrom = argShell[3];
		dateFromTime = argShell[4];
		dateTo = argShell[5];
		dateToTime = argShell[6];

		searchFile();
	}

	/**
	 * Searches file from the query prompt
	 * @param  NA
	 * @return void
	 */
	private static void searchFile() {
		File dir = new File(filePath);

		Date datetimeFrom = null, datetimeTo = null;
		long unixTimeFrom = 0, unixTimeTo = 0;

		String filename = null;
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("Server_" + ipAddr + "_" + serverCore);
			}
		};
		String[] children = dir.list(filter);
		if (children == null) {
			System.out.println("Either dir does not exist or is not a directory");
		} else {
			for (int i = 0; i < children.length; i++) {
				filename = children[i];
			}
		}
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {

			datetimeFrom = sdf.parse(dateFrom + " " + dateFromTime);
			datetimeTo = sdf.parse(dateTo + " " + dateToTime);

			unixTimeFrom = (long) datetimeFrom.getTime() / 1000;
			unixTimeTo = (long) datetimeTo.getTime() / 1000;
			boolean blnRecTrue = parseFile(dir + "/" + filename, String.valueOf(unixTimeFrom), String.valueOf(unixTimeTo));
            if (blnRecTrue)
        			LOGGER.info("No Records matched the query criteria. Please try a different criteria.");
 		} catch (ParseException parseEx) {
			LOGGER.log(Level.SEVERE, "Error occur in parsing file. Please recheck the arguments usage.", parseEx.getMessage());
		} catch (FileNotFoundException fileEx) {
			LOGGER.log(Level.SEVERE, "Error occured in reading file.", fileEx.getMessage());
		}

	}

	/**
	 * Parses file for start and end time
	 * @param  fileName - file name to be parsed for server and core combination
	 * @param  strStartTime - start datetime
	 * @param  strEndTime -end datetime
	 * @return void
	 */
	private static boolean parseFile(String fileName, String strStartTime, String strEndTime)
			throws FileNotFoundException {
		Scanner scan = new Scanner(new File(fileName));
		boolean noRecords = true;
		while (scan.hasNext()) {
			String line = scan.nextLine().toString();
			if (line.contains(strStartTime)) {
				noRecords = false;
				formatStdOutput(line);
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					if (line.contains(strEndTime)) {
						formatStdOutput(line);
						break;
					}
					formatStdOutput(line);
				}
				break;
			}
		}
		scan.close();
		return noRecords;
	}

	/**
	 * Format Std. O/P from unix epochs time to user input and CPU usage with
	 * percentage
	 * @param  line
     * @return void
	 */
	private static void formatStdOutput(String line) {
		String[] serverData = line.split("\t");

		long unixSeconds = Long.parseLong(serverData[0]);
		Date date = new java.util.Date(unixSeconds * 1000L);
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-8"));

		StringBuilder toPrint = new StringBuilder();
		toPrint.append(sdf.format(date));
		toPrint.append(" ");
		toPrint.append(serverData[serverData.length - 1]);
		toPrint.append("%");
		System.out.println(toPrint);
	}
}