#!/bin/bash 

#ispitna zad - korisnikot kolku vreme bil najaven na odreden server

#who - koj e tekovno najaven
#last - informacija za site ssh sesii koi sto bile vospostaveni vo minatoto 

last | grep "^221248" | awk ' { print $10 } ' | sed 's/(//g' | sed 's/)//gi' 
| awk -F: 'BEGIN {total=0;} { total+=$1*60+$2 } END { print total; } '

#vo putty ova 
