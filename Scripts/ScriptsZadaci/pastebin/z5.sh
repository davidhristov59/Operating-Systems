#!/bin/bash

last | awk '$6 ~ /23/' | awk ' ($7 ~ /^17/) && ($9 ~ /^20/)' | sort -n -u -r | wc -l

