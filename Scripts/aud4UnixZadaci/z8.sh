#!/bin/bash

#proveruvame prvo dali brojot na argumenti soodvedstuva na baranjata na zadacata
#BITEN USLOV - ima na kol 
# $# --> broj na argumenti 

if [ $# -lt 3 ] #ako imame pomalku od 3 argumenti 
then
	echo "Brojot na argumenti ne e soodveten" #error message 
	exit 1
fi

OLD="$1" #stariot regularen izraz 
NEW="$2" #noviot reg expre


shift
shift

for f in $* #gi iterirame site argumenti 
do 
	if [ -f "$f" ] #dali e file 
	then
		newfile=$(echo "$f" | sed "s/${OLD}/${NEW}/g") #GO ZAMENUVAM, potocno starata vrednost so novata i go pravime globalno so g-celiot file
		#outputot da se smesti vo newfile promenlivata 

		if [ ! -f "$newfile" ] #ako ne postoi file-ot togas da ispecati deka e uspesno preimenuvano
		then
			echo "uspesen rename ${f} vo ${newfile}" 
			mv "$f" "$newfile"	#preimenuvanje pravime od stariot file vo novoto 
			
		fi
	fi
done

# ./z8.sh .txt .jpg result.txt
