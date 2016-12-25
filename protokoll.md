

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

    $msg$to$NACHRICHT$uuid%

mit  
*   `to`: Nickname des empfangenden Benutzers. Wenn Nachricht an alle gehen soll, `*`

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
(Ein Nickname darf nur aus Zahlen und kleinen und großen Buchstaben bestehen)

    $reg$nick$uuid%

mit  
*   `nick`: der gewünschte Nickname

---

*Fordere den Server auf, den Status des Chatrooms zurückzugeben*  
(Löst beim Server ein `$stat$...`-Kommando aus)

    $getstat$uuid%

---

*Signalisiere dem Server, dass der Client den Chatroom verlässt*

    $leave$uuid%



Server:
-------

*Gebe den Status des Servers zurück*

    $stat$user1,user2...$uuid%

mit:  
*   `user1,user2`: durch Kommas getrennte Liste der Nutzer, die momentan online sind.

