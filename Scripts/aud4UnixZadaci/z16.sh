#!/bin/bash

if [ $# -ne 1 ]
then
	echo "greska"
	exit 1
fi

if [ -f out.txt ]
then
	rm out.txt
fi

processes=$(ps -ef | grep "$1" | awk '{print $2; }' )

for process in $processes
do 
	#echo "$process"
	count=$(ps -ef | grep "$1" | awk '{print $3}' | grep "${process}$" | wc -l )
	echo "$process $count" >> out.txt # >> append pravime, celo vreme se dodava na file-pt
done

