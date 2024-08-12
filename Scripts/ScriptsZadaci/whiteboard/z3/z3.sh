#!/bin/bash 

cat ipadresi.txt | sed 's/(//g' | sed 's/)//g' | grep "^89" | grep "[0-9].[0-9]\{2,3\}.[0-9]\{3\}"

#outputot od file-ot go prakame vo pipeline, potooa gi trgame zagradite, barame ip adresi so pocnuvaat na 89 i gledame ...
#da trebase da ima 0 na kraj od ip adresata ke trebase grep "[0-9].[0-9]\{2,3\}.[0-9]\{3\}0$" illi .0$

#vo pUTTY --> who | awk '{print $5}' | sed 's/(//g' | sed 's/)//g' | grep "^89" | grep "[0-9].[0-9]\{2,3\}.[0-9]\{3\}0$" | sort | uniq
