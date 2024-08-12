#!/bin/bash 

if [ $# -lt 1 ] 
then
	echo "Malku argumenti"
	exit 1
fi

if [ ! -f $1 ] 
then
	echo " Ne e file prviot argument"
	exit 1
fi

filename=$(ls -l | grep ".*\.java$" | grep "^.rw." | awk 'print {$10}')

for file in filename 
do
	cat $file >> $1
	echo ' ' >> $1
done 
