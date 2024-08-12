#!/bin/bash

#1 opcija 
cat lista.txt | awk '{print $1, $5, $6}' | grep "Apr 14" | awk '{print $1}' | grep "[02468]$" | awk '{print $1}'

#2 opcija 
#cat lista.txt | awk '{print $1, $5, $6}' | grep "Apr 14" | awk '{print $1}' | awk '$1 ~ /[02468]$/{print $1}'

#./z4.sh
