#!/bin/bash 

count=$1

while [ $count -gt 0 ]
do
	echo $count 
	count=$(( $count - 1 ))
	sleep 1
done 
echo "pocna odbrojuvanjeto"
 
