#!/bin/bash

allFiles=$(ls) #ke ja izlistame sekoja datoteka 

select file in $allFiles "Exit Program"
do
	if [ -z "$file" ]	#dali ima inicijalizirano vrednost dadenata promenliva  		  
	then
		continue #ako ima inicijalizirano vrednost, ne e prazen stringot 
	fi
	if [ "$file" = "Exit Program" ] 
	then
		break 
	fi
	if [ ! -f "$file" ] #dali vrednosta sto se izminuva e file 
	then
		echo "${file} ne e file"
		continue
	fi
	cat "$file"  #ke ja ispecatime vrednosta na file-ot 
done	

#./z11.sh eavtomatski ke mi gi izlista site file-ovi vo tekovniot folder zosto najgore staviv ls kako argument 
