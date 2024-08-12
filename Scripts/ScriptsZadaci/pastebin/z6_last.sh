#!/bin/bash

files=$(ls -l | grep ".[tT][xX][tT]$")

for file in $files 
do 
	newfile="echo $file | sed 's/[tT][xX][tT]/txt/g'"
	mv $file $newfile
done
