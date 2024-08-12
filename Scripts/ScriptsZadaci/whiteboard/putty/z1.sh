#!/bin/bash 

last | awk '{print $1}' | sort | uniq | grep "^[0-9]\{6\}" | wc -l

