#!/bin/bash 

toLower(){
#moze i so $1...
#toa sto e prateno argument zameni go od golemi vo mali bukvi
	echo $@ | tr '[A-Z]' '[a-z]'   #toa sto sme go pratile kako argument, da gi zamenime golemite bukvi so mali bukvi 
	#zamena pravime so translate funkcijata od golemi vo mali bukvi 
}

toLower $@ #site argumenti gi povkuvam za funkcijata 
