

Protokoll
=========

> *Anmerkung:* alles was in Caps geschrieben wird ist ein benutzerdefinierter String
>
> `$` beginnt eine Nachricht und ist Trennelement zwischen Parametern 
>
> `%` ist EOL-Zeichen
> 
> Jedes Kommando hat als letztes Argument eine UUID in Normalform. Das erlaubt, dass auf eine
> Nachricht Bezug genommen werden kann.


Gemeinsam:
----------

*sendet eine Nachricht von Server zu Client oder umgekehrt.*

    $msg$to$NACHRICHT$timestamp$uuid%

mit  
*   `to`: Nickname des empfangenden Benutzers. Wenn Nachricht an alle gehen soll, `*`
*   `timestamp`: Zeitstempel der Nachricht

---

*Antwort des Servers / des Clients auf ein Kommando des Anderen.*

    $rsp$code$uuid%

mit  
*   `code`: ein Statuscode (s.u.)



Client:
-------

*"Heartbeat:" Signalisiere dem Server dass der Client noch aktiv ist*  
(Muss alle 2 Minuten gesendet werden, ansonsten schließt der Server die Verbindung)

    $heartbeat$uuid%

---

*Registriere den Client unter einem Nicknamen*

    $reg$nick$uuid%

mit  
*   `nick`: der gewünschte Nickname

---

*Fordere den Server auf, den Status des Chatrooms zurückzugeben*  
(Löst beim Server ein `$stat$...`-Kommando aus)

    $getstat$uuid%

---

TODO

