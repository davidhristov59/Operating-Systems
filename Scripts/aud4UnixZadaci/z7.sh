#!/bin/bash

for file in $* #gi prikazuvame site argumenti sto sme gi pratile 
do 
	if [ ! -f "$file"  ]	#prvo da proverime dali e datoteka, posle ja proveruvame negovata sodrzina
	then			#dali fajlot sto go iterirame -f e file, prvo odime so negacija 
		echo "greska"
		exit 1 
	fi	
	if [ -r "$file" ] #dali imame read privilegii na file-ot, ako imame mozeme da ja izlistame sodrzinata na file-ot  
	then
		cat -n "$file"  #prikazi ja sodrzinata - SI GI KORISTAM KOMANDITE, a -n ni gi dava brojkite na sekoj red 
	fi
done #go zatvorame i ciklusot

#./z7.sh results.txt vaka go prikazuvame file-ot
#tuka so argumenti rabotam, samo vnesuvam preku terminal



