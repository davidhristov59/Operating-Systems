#!/bin/bash

if [ $# -lt 3 ] 
then 
	echo "Brojot na argumenti treba da e 3 ili pogolem"
	exit 1
fi

OLD=$1
NEW=$2

shift
shift

for file in $*
do 
	if [ -f "$file" ]
	then
		newfile='echo "$f" | sed "s/${OLD}/${NEW}/g"
		if [ -f "$newfile" ] 
		then
			echo "error"
		else 
			echo "uspesen rename od ${file} vo ${newfile}"
			mv "$file" "$newfile"
		fi
	fi
done
