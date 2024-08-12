#!/bin/bash

awk '{ 
	for (i=NF;i>=1;i--){ #NF(number of Fields) - ni gi prebrojuva kolonite koi sto postojat 
	printf("%s ", $i);
	}
	printf("\n")
}' $1 #ke go povikame na prviot argument 

#./z13.sh fruit_prices.txt

