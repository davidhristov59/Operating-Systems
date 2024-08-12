#!/bin/bash

ls -l | grep "^-" | grep "[A-Za-z0-9.\]\{5,\}c$" | awk '{print $NF}'

#"[A-Za-z0-9.\]\{5,\}c$ kade .\ e deka treba da ima i tocka, a so \ escape pravam 
