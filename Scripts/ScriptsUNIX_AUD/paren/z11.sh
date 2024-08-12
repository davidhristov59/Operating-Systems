#!/bin/bash

#kaj mene moram vaka so $(ls)
allFiles=$(ls) #gi izlistuvame site files vo folderot 

select file in $allFiles "EXIT"
do
	if [ -z "$file" ] #dali promenlivata e inicijalizirana so nekakva vrednost 
	then
		 continue
	fi
	
	if [ "$file" = "EXIT" ]
	then 
		break
	fi

	if [ ! -f "$file" ] #ako ne e file  
	then
		echo "${file} is not a regular file"
		continue
	fi
	
	cat "$file" #prikazi ja sodrzinata
	
done

#./z11.sh 
