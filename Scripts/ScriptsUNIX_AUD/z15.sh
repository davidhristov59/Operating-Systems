#!/bin/bash

#221085@os2:~$ last | grep "^221045" | awk ' { print $10 } ' | sed 's/(//g' | sed 's/)//g' | awk -F: 'BEGIN {total=0;}  { total+= $1*60+$2 }
#END {print total}'


#1 cekor --> last | grep "^221045"
#2 cekor --> last | grep "^221045" | awk '{print $10} ' 
#3 cekor --> last | grep "^221045" | awk '{print $10} ' | sed 's/(//g' | sed 's/)//g'
#4 cekor --> last | grep "^221045" | awk '{print $10} ' | sed 's/(//g' | sed 's/)//g' | awk -F: '{print $1, $2}'
#5 cekor --> last | grep "^221045" | awk ' { print $10 } ' | sed 's/(//g' | sed 's/)//g' | awk -F: 'BEGIN {total=0;}  { total+= $1*60+$2 } 
#END {print total}'

#echo $total > out.txt 
#cat out.txt 

