#!/bin/bash 

month=$(date | awk '{print $2})

array=$(cat comedians.txt)

for i in $array
do 
	m=$(echo $i | awk '{print $3}')
	y=$(echo $i | awk '{print $5}')
	company=$(echo $i | awk '{print $6}')

	if [ "$month" == "$m" ] && [ "$y" -gt 2013 ] && [ "$company" == "$1" ]; 
	then
        	name=$(echo $i | awk '{print $1}')
        	surname=$(echo $i | awk '{print $2}')

		echo "$name $surname"
	fi  
done
