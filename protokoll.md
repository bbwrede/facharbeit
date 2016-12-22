

Protokoll
=========

> *Anmerkung:* alles was in Caps geschrieben wird ist ein benutzerdefinierter String
> `$` beginnt eine Nachricht und ist Trennelement zwischen Parametern
> `%` ist EOL-Zeichen


Gemeinsam:
----------

    $msg$from$to$NACHRICHT$timestamp$uuid%

mit  
*   `from`: Nickname des sendenden Benutzers. Wenn falsch, gibt Server Error zurück (s.u.)
*   `to`: Nickname des empfangenden Benutzers. Wenn Nachricht an alle gehen soll, `*`
*   `timestamp`: Zeitstempel der Nachricht
*   `uuid`: UUID der Nachricht in Normalform. Die infolge der Nachricht gesendete `$rsp$...`-Nachricht hat dieselbe UUID.*

Sendet eine Nachricht von Server zu Client oder umgekehrt.


    $rsp$code$uuid%

mit  
*   `code`: ein Statuscode (s.u.)
*   `uuid`: UUID des zugehörigen Befehls in Normalform.

Antwort des Servers / des Clients auf ein Kommando des Anderen.



Client:
-------

