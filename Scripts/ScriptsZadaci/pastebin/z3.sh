#!/bin/bash

if [ $# -lt 1 ] 
then 
	echo "USAGE: 'basename $0' username"
	exit 1
fi

if [ -f out3zad.txt ] 
then
	rm out3zad.txt
fi


#nov red \ 

times=$(cat student3zad.txt | grep "223007" | awk ' {print  $NF}' | sed 's/(//g' | sed 's/)//g' | awk -F: 'BEGIN {total=0} {total+=$1*60+$2} END {print total}')

echo $times > out3zad.txt

cat out3zad.txt 

#./z3.sh 223007
