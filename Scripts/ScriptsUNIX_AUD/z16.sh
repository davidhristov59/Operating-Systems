#!/bin/bash

if [ $# -lt 1 ]
then
	echo "malku args" 
	exit 1
fi 

if [ -f out.txt ] 
then 
	rm out.tx #ako vise postoi izbrisi  
fi

processes=$(ps -ef | grep "$1" | awk '{print $2}')
processes2=$(ps -ef | grep "$1" | awk '{print $3}')

for process1 in $processes
do 
	for process2 in $processes2
	do 
		if [ $process1 -eq $process2 ] 
		then 
			count=$(( $count+1 ))
		fi
	done
	echo "$process1 $count" >> out.txt 
done

#./z16.sh out.txt











