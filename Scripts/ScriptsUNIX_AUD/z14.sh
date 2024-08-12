#!/bin/bash

mkdir paren
mkdir neparen

for file in *
do
	if [ -f "$file" ] 
	then
		length=${#file}

		if (( length % 2 == 0 ))
		then
			cp "$file" paren/
		else
			cp "$file" neparen/
		fi
	fi 				
done 
