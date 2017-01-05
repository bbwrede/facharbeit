
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.IllegalArgumentException;


/**
 * Repräsentiert einen Kommando-String mit validem Syntax.
 */
class ValidCommand implements Command
{
    private Command.Type type;
    private String[] params;
    private String uuid;


    /**
     * Erstelle ein neues, noch leeres Kommando.
     */
    public ValidCommand ()
    {
        uuid = UUID.randomUUID ().toString ();
        params = new String[0];
    }

    /**
     * Erstelle ein neues Kommando aus dem übergebenen String cmd.
     *
     * <p>Hat cmd einen invaliden Syntax, so wird {@link Command.ParsingException}
     * gethrowt. In diesem Fall sollte statt eines ValidCommands ein
     * {@link InvalidCommand} erstellt werden.
     *
     * @param cmd das Kommando aus dem ein ValidCommand erstellt werden soll.
     */
    public ValidCommand (String cmd) throws ParsingException 
    {
        this ();

        String cmdOriginal = cmd;

        cmd = cmd.trim ();
        
        if (!(cmd.startsWith ("%") && cmd.endsWith ("%")))
        {
            throw new ParsingException ();            
        }

        // % vom Anfang und Ende entfernen
        cmd = cmd.substring (1, cmd.length () - 1);

        // Erstes Element des Kommandos ist der Kommandotyp
        String[] elems = cmd.split("\\$");

        try
        {
            type = Type.valueOf (elems[0].toUpperCase ());
        }
        catch (IllegalArgumentException e)
        {
            throw new ParsingException ();
        }

        // UUID speichern
        try
        {
            UUID.fromString (elems[elems.length-1]);
        }
        catch (IllegalArgumentException e)
        {
            throw new ParsingException ();
        }
        uuid = elems[elems.length-1];

        // Restliche Elemente als Parameter einlesen
        params = Arrays.copyOfRange (elems, 1, elems.length - 1);
        
        boolean valid;
        switch (type)
        {
            case MSG:
                valid = (params.length == 2);
                break;
            case RSP:
                valid = (params.length == 1);
                break;
            case HEARTBEAT:
                valid = (params.length == 0);
                break;
            case REG:
                valid = ((params.length == 1) && isValidNickname (params[0]));
                break;
            case GETSTAT:
                valid = (params.length == 0);
                break;
            case LEAVE:
                valid = (params.length == 0);
                break;
            case STAT:
                valid = (params.length == 0);
                break;
            default:
                valid = false;

            if (!valid)
            {
                throw new ParsingException ();
            }
        }
    }

    public String toString ()
    {
        String cmd = "";

        cmd += "%";
        cmd += type.toString ().toLowerCase ();
        cmd += "$";
        if (params.length > 0)
        {
            cmd += String.join ("$", params);       
            cmd += "$";
        }
        cmd += uuid;
        cmd += "%";

        cmd += "\n";

        return cmd;
    }
    
    /**
     * @return Den Typ dieses Commands gemäß protokoll.md
     */
    public Command.Type getType ()
    {
        return type;
    }

    /**
     * Setze den Typ dieses Commands gemäß protokoll.md
     */
    public void setType (Type pType)
    {
        type = pType;
    }

    /**
     * Setze die Parameter des Kommandos, ausgenommen die UUID.
     *
     * <p>Wichtig: Es wird keine Prüfung auf Validität der Parameter vorgenommen.
     *
     * @param pParams Ein String-Array aller Parameter in der Reihenfolge spezifiziert
     * in protokoll.md
     */
    public void setParams (String[] pParams)
    {
        params = pParams;
    }

    /**
     * @return Einen String-Array aller Parameter des Kommandos in der Reihenfolge
     * spezifiziert in protokoll.md
     */
    public String[] getParams ()
    {
        return params;
    }

    public String getUUID ()
    {
        return uuid;
    }

    public void setUUID (String pUuid)
    {
        uuid = pUuid;
    }
}
