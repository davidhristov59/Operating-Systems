221085@os2:~$ ps -ef | grep -v "bash" | awk '{print $2, $5}' | grep ":" | grep "20"$ | sort | uniq | tail -1 | awk '{print $1}'

#tail -1 za da go najdam najgolemiot sto znaci od dole zemi mi go prviot ,a od dole bidejki mi e vo rastecki redosled sortirano
#grep -v za da NE MI GI DADE TIE SO BASH --> SPROTIVNO 

