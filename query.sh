if [ $# -eq 0 ]; then
   echo "Please provide a path to query server data"
   exit 1
fi

while true; do 
   read -p "> "

   IFS=" "
   arrQryArg=($REPLY)

   case ${arrQryArg[0]} in
       QUERY|query|Query)
           if ((${#arrQryArg[@]} != 7)); then 
               echo "---------------------------------------------------------------------------------"
               echo "Query is invalid."
               echo "Syntax - QUERY <Server IP> <Core> <Start Date> <Start Time> <End Date> <End Time>"
               echo "Usage  - QUERY 195.168.0.250 1 2019-02-23 11:40 2019-02-23 12:45"
               echo "---------------------------------------------------------------------------------" 
           else
               java -jar QueryLogs.jar $1 ${arrQryArg[1]} ${arrQryArg[2]} ${arrQryArg[3]} ${arrQryArg[4]} ${arrQryArg[5]} ${arrQryArg[6]}             
           fi
           ;;
       EXIT|exit|Exit)
           exit 1
           ;;
       *)
           echo "$REPLY is not a valid choice"
           ;;
   esac

   echo $query
done
