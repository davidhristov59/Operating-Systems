#!/bin/bash

#vo putty 
last | awk '{print $10, " " , $1 }' | sed 's/(//g' | sed 's/)//g' | sort -n | grep ":" | tail -1

