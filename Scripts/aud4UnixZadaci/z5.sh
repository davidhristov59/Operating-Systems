#!/bin/bash

count=$1 #inicijalizacija na promenlivata count, da bide ednakva na prviot argument

while [ "$count" -gt 0 ]
do
	echo $count #se pecati vrednosta na count 
	count=$(( $count - 1)) #se namaluva count za 1 
	sleep 1 #se pauzira skriptata za 1 sek
done
echo "zavrsi natprevarot"

#./z5.sh 5 za compile i ke se odbrojuva 5 4 ... 
