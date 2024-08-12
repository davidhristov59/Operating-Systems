#!/bin/bash

# $# - site argumenti 
if [ $# -ne 1 ] #dali brojot na argumenti mi e 1 
then
	echo "greska"
	exit 1
fi

if [ -f out.txt ] #ako postoi vise out.txt, briseme
then
	rm out.txt
fi

total=$(last | grep "^$1" | awk '{print $10}' | sed 's/(//g' \
| sed 's/)//g' \
| awk -F: 'BEGIN {total=0;} {total+=$1*60+$2;} END {print total;}' \ 
| grep -v "in")

echo "$total" > out.txt #output-ot go redirectirame vo out.txt 
