# CPULogGeneratorAndQuery

<p>
  <OL>
    <li> <b>generate.sh</b><br>
This is the shell script to take the path as input from user and then call the
CPUUsageGenerator that is a simulator to generate CPU logs for 2019-02-23 00:01
- 2019-02-24 00-01<br>
To run:<br>
./generate.sh &lt;path&gt; <br>
E.g.: ./generate.sh Users/rency/Desktop/Quantil/ServerData<br>
If the directory mentioned in the path does not exist, this script creates it.
Also, if this directory contains any previous files, it deletes all the files so as to add
new files.<br>
      It then executes the jar file for CPUUsageGenerator that finally creates the logs.</li>
      <li><b>CPUUsageGenerator</b><br>
This is a java program to simulate generation of CPU Logs for one day. The Ip
addresses considered are -<br>
192.168.0.0 - 192.168.0.250<br>
193.168.0.0 - 193.168.0.250<br>
194.168.0.0 - 194.168.0.250<br>
195.168.0.0 - 195.168.0.250<br>
2 cores 0 & 1 have been considered for each of the IP addresses.<br>
So, for each IP address and core combination, one output file each is created with
the name Server_<IP_Address>_<core no>_<Random value>.txt which contains
the unix timestamp, IP address, Core no and CPU usage.<br>
Starting from 2019-02-23 00:01, data is generated for the next 24 hours for every
minute.
The decision to create 1 file per server & core combination was made so that the
query command performs well.</li>
<li><b>query.sh</b><br>
This is the shell script to start a Interactive program that takes QUERY & EXIT
commands from the user.<br>
To run:<br>
./query.sh &lt;path&gt;<br>
E.g.: ./query.sh Users/rency/Desktop/Quantil/ServerData<br>
This opens a prompt where the user can Query the logs.<br>
To run:<br>
&gt; QUERY &lt;IP_address of CPU&gt; &lt;CPU Core No&gt; &ltStart date and time&gt; &lt;End Date
and time&gt;<br>
E.g.: QUERY 195.168.0.250 1 2019-02-23 11:40 2019-02-23 12:45<br>
&gt; EXIT<br>
To allow ease of use the commands accepted are - Query / QUERY / query and Exit
/ EXIT /exit.<br>
It then executes the jar file for QueryLogs that gives the output of the query fired.
Unless the user exits, the > prompt is shown. So the user does not need to execute
the script again and again for multiple input.</li>
<li><b>QueryLogs</b><br>
The QueryLogs program first searches for the file with the name Server_<IP
Address passed by the Query>_<Core No passed by the query>*.txt to get all the
data for that Server / Core combination.<br>
Then, the program searches for the start time in the file and prints out all the
records from the start time to the end time as entered by the user in the query.
Since, we are not searching through the entire 2000 files for the records, the
execution of QUERY command is extremely quick(within milliseconds).<br>
Care has been taken for UNIX time conversions.<br>
Error conditions handled are -<br>
Incorrect no of arguments<br>
Incorrect arguments<br>
No record Found</li>
  </ol>
  </p>
Logger has been used to Log information and errors.
