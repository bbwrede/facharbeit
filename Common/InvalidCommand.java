
import java.util.UUID;
import java.lang.IllegalArgumentException;

/**
 * Repräsentiert ein Kommando-String mit invalidem Syntax.
 *
 * <p>Wenn möglich wird die UUID des Kommandos extrahiert.
 */
class InvalidCommand implements Command
{
    private String uuid;
    
    public InvalidCommand (String cmd)
    {
        String[] tmp = cmd.split ("\\$");
        String uuidTmp = tmp[tmp.length - 1];
        uuidTmp = uuidTmp.substring (0, uuidTmp.length () - 1);
        try
        {
            UUID.fromString (uuidTmp);
            uuid = uuidTmp;
        }
        catch (IllegalArgumentException e)
        {
            uuid = null;
        }
    }

    public String getUUID ()
    {
        return uuid;
    }

    /**
     * Gib die String-Repräsentation des Commands zurück.
     *
     * <p>Wichtig: Gibt nicht den String zurück, aus dem das Command erstellt wurde.
     * Stattdessen:
     *
     * <li>War der String vollständig unlesbar, "%INVAL$NOUUID%</li>
     * <li>War es möglich, die UUID aus dem String zu extrahierem, "%INVAL$ - uuid - %"</li>
     */
    public String toString ()
    {
        if (uuid == null)
        {
            return "%INVAL$NOUUID%\n";
        }
        else
        {
            return "%INVAL$" + uuid + "%\n";
        }
    }
}
