#!/bin/bash

mkdir paren
mkdir neparen

for f in * #gi listam site folderi i file-ovi 
do
	#echo "$f"
	
	if [ -f "$f" ]
	then
		case $(wc -c < "$f") in 
			*[02468]) cp "$f" ./paren/ ;; #ako zavrsuva so nekoi od cifrite togas go kopirame segasniot file vo paren
		 	*[13579]) cp "$f" ./neparen/ ;; #isto i za neparnite 
		esac #go zatvorame case-ot
	fi
done
