#!/bin/bash


top -b -n 1 | grep "221085" | awk '{print $11} ' | sed 's/\./:/gi' | awk -F: 'BEGIN {total=0;} {total+= $1*60*1000+$2*1000+$3*10; } END  {print total;} '

