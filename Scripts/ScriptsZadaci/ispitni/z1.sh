#!/bin/bash

if [ $# -lt 2 ]
then
	echo "USAGE: basename $0"
	exit 1
fi

from=$1
to=$2

if [ ! -d $from ]
then
	mkdir -p $from
fi

if [ ! -d $to ]
then
        mkdir -p $to
fi


files=$(ls -l ${from}| grep "^-" | awk ' { print $9 }' | grep "[0-9][A-Za-z]*\.out")

echo $files

#./z1.sh ./from/ ./to/

for file in $files
do
        cp ${from}${file} ${to}${file}
done

summedfiles=$(ls -l ${to}| grep "^-..x" | awk 'BEGIN {total=0} {total+=$5} END {print total}')

echo $summedfiles
