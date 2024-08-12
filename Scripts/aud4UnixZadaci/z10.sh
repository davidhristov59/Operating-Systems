#!/bin/bash

#definiranje na funkcija 
 #zamena na golemite bukvi so malite bukvi

toLower() {
	
	#isprintaj mi go argumentot sto sum go pisal ama smenet 
	echo $@ | tr '[A-Z]' '[a-z]' #translate - od golemi bukvi vo mali za prviot argument
	#toa sto sme go pratile kako argument da gi zamenam 	
}

toLower $@ #povikuvam so aergument

#moram da pratam argument -> ./z10.sh DAVID --> david 
