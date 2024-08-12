#!/bin/bash 

cat procesi.txt | grep -v "bash" | awk '{print $2, $5}' | grep -i ":2[0-9]" | sort | uniq | tail -1 | awk '{print $1}'

#grep -i --> case-sensitive

#./z5.sh

