#!/bin/bash

for file in $* #gi prikazuvame site argumenti sto sme gi pratile
do
        if [ ! -f "$file" ] #proveruvame prvo dali ne e file
        then
                echo "Greska"
                exit 1
        fi

        if [ -r "$file" ] #dali imame read privilegii za ovoj file
        then
                #prikazi ja sodrzinata na file-ot

                cat -n "$file" #so redni broevi da gi dade
        fi
done


#./z7.sh ./*  --> gi prikazuva site files vo segasnata datoteka 
