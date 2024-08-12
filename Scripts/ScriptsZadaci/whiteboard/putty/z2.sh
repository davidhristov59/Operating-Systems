#!/bin/bash

ps -ef | grep "nano" | sort | uniq | grep "^[0-9]\{6\}" | awk '{print $1}' | wc -l

