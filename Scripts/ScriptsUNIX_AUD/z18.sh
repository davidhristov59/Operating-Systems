#!/bin/bash

#top -b -n 1 | grep "221085" | awk '{print $11}' | sed 's/\./:/g' | awk -F: 'BEGIN {total=0} {total+=$1*1000*60+$2*1000+$3*10} END {print total}'

if [ $# -lt 1 ]
then 
	echo "Malce argumenti"
	exit 1
fi

PID=$1

#1 opcija 
#totaltime=$(top -b -n 1 | grep "221085" | awk '{print $11}' | sed 's/\./:/g' | awk -F: 'BEGIN {total=0} {total+=$1*1000*60+$2*1000+$3*10} END {print total}')

#echo "Total time in milliseconds for PID $PID: $total_time"

#2 opcija 
totalTime=$(top -b -n 1 | grep "221085" | awk '{print $11}' | sed 's/\./:/g' | awk -F: ' 

BEGIN{
	total=0
}

{
	total += $1 * 1000 * 60 + $2 * 1000 + $3 * 10 
}

END {
	print total
}')

echo "Total time in milliseconds for PID $PID: $total_time"


# ./z18.sh 221085 
