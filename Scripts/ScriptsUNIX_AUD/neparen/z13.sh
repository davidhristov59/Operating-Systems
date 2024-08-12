#!/bin/bash

if [ $# -lt 1 ]
then 
	echo "nema dovolno argumenti"
	exit 1	
fi

awk '{

	for(i=NF;i>=1;i--){
		printf("%s ", $i);
	}	
	printf("\n")
}' $1 #obavezno za prviot argument 


