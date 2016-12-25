
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.IllegalArgumentException;

class ValidCommand implements Command
{
    private Command.Type type;
    private String[] params;
    private String uuid;

    public ValidCommand ()
    {
        uuid = UUID.randomUUID ().toString ();
    }

    public ValidCommand (String cmd) throws ParsingException 
    {
        this ();

        String cmdOriginal = cmd;

        cmd = cmd.trim ();
        
        if (!(cmd.startsWith ("$") && cmd.endsWith ("%")))
        {
            throw new ParsingException ();            
        }

        // $ vom anfang und % vom Ende entfernen
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

        cmd += "$";
        cmd += type.toString ().toLowerCase ();
        cmd += "$";
        cmd += String.join ("$", params);
        cmd += "$";
        cmd += uuid;
        cmd += "%";

        return cmd;
    }

    public boolean isValidNickname (String nick)
    {
        // TODO:
        // - implementieren
        // - in COmmand verschieben
        // - static machen
        return true;
    }
    
    public Command.Type getType ()
    {
        return type;
    }

    public void setType (Type pType)
    {
        type = pType;
    }

    public void setParams (String[] pParams)
    {
        params = pParams;
    }

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
