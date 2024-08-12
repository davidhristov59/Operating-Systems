#!/bin/bash

if [ -f output.txt ] 
then
	rm output.txt
fi

lista=$(ls -l | grep "^-" | grep "[a-zA-Z0-9]\{6\}")

for file in $lista
do 
	if [ -x $file ]
	then
		print=$(echo "$file" | awk -F"." '{print $1}' | wc -m)
		
	 echo "$file" > datoteka.txt
	fi
done 
