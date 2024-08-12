#!/bin/bash

lista=$(ls|grep -i .txt | grep -v .txt)

for file in $lista
do 	
	name=echo "$file | awk -F.'{print $1}'
	mv "$file" "$name.txt"
done
