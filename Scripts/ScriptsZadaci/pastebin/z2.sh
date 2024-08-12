#!/bin/bash 

#MOZAM I SO 2 ECHO VO SKRIPTA SO EDEN OUTPUT DA GO NAPRAVAM ISTOTO

#1 opcija 

cat studenti.txt | grep -E "Sat|Sun" | awk '$10 ~ /^\([0-9][2-9]/' | awk '{print $1 "\t" $NF}' | sed 's/(//g' | sed 's/)//g' | sort -n -u | tee result.txt | wc -l >> result.txt
 
#grep -E za regular expression - nesto ptoocno kgoa baram
#awk '{print $1 "\t" $NF}' --> da se odeli so tab
#sort -n -u --> sortira liniite numericki i gi brise duplikat liniite so u

#tee koristime koga sakame da iamme primer 2 rezultati vo 1 file, prvo site studenti odeleni i sortirani gi stavam vo kraen text file i 
#posle so wc-l gi brojam i go preprakam outputot vo toj file

#2 opcija - putty 
#last | grep -E "Sat|Sun" | awk '$10 ~ /^\([0-9][2-9]/' | awk '{print $1 "\t" $NF}' | sed 's/(//g' | sed 's/)//g' | sort -n -u | tee result.txt | wc -l >> result.txt
#davidhristov@Davids-MacBook-Pro pastebin % cat result.txt
