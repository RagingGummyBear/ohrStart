# ohrStart


##Napraveno i smeneto: 

-registracija/logiranje (bez enkripcija)
-dopolneto e dummyTestPhp so povici za reg/log

##Nachin za koristenje na dummyTestPhp

-samo treba da se ispratat i ostanatite parametri, kako sto se isprakjashe 'action':"call_this", so raznichno 'action' za log/reg + dopolnitelni argumenti.

##Kaj serverot izmeni i dopolnuvanja

-za nachinot kako se spravuva so porakite mozhe da se vidi vo WebSocketServerHandler classata vo onMessage methodot.
-za povrzuvanje so databazata se koristi DatabaseAdapter. Vo taa klasa ima 2 metodi za koristenje: getAllElementsFromTableWithConditionsAndSorted
i addNewObject. getAllElementsFromTableWithConditionsAndSorted pretstavuva select, a dodeka addNewObject pretstavuva insert. Za da se dodadat novi klassi potrebno e da se napravi classa za entitetot(pozhelno vo DatabaseEntities), kaj koja iminjata na polinjata ke bidat isti so iminjata na colonite vo databazata. I isto taka da se koristi String za polinjata bez razlika sto ke bide zachuvano, biejki nie podatocite gi dobivame preku json (ke pristignat kako string do serverot) i strukturata na addNewObject bi ni ovozmozhila da se namali mnogu kod. Ako e potrebno mozhe da se kastiraat odkako ke se zemat od databaza. 
-Podatocite za povrzuvanje so databazata se naogaat najdole vo DatabaseAdapter class-ata
