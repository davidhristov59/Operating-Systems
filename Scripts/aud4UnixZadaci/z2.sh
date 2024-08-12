#!/bin/bash

#proveruva dali prviot argument e 1,2,3

if [ "$1" = "1" ] #sve gleda kako string 
then 
	echo "ja izbravte prvata opcija" #./z2.sh 1 za proverka 
elif [ "$1" = "2" ]
then
	echo "ja izbravte vtorata opcija"
elif [ "$1" = "3" ]
then
	echo "ja izbravte tretata opcija"
else 
	echo "ne izbravte nisto, ve molam obidete se povtorno!"
fi 


