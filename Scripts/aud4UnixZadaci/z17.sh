#!/bin/bash

if [ $# -lt 2 ] 
then
	echo "USAGE: $(basename $0 ./src_folder/ .dest_folder) "
	exit 1	
fi

from=$1
to=$2

if [ ! -d $to ] #ako ne postoi folderot 
then
	mkdir -p $to #togas kreiraj go 
fi

files=$(ls -l ${from} | grep "^-" | awk '{print $9}' | grep "^[0-9][a-z]" | grep "\.out$" )

#grep vaka ke se izvrsuva vo tekovniot folder, nie treba da go napravime da se izvrsuva vo izvorniot folder i zatoa vnesuvame argumenti

for f in $files
do 
	cp ${from}${f} ${to}${f} #od src folder, toj i toj file vo dest_folder kopiraj go
done


sum=$(ls -l ${to} | grep "^-..x" | awk '{print $5;}' | awk 'BEGIN {total=0;} { total+=$5; } END { print total; }' )

echo "$sum"
