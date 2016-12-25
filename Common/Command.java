
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: Syntax Error wenn Kommando behinderten Syntax hat

class Command
{
    public enum Type {
        MSG,
        RSP, 
        REG, 
        LEAVE,
        GETSTAT,
        STAT;
    }

    private Command.Type type;
    private String[] params;
    private String uuid;

    public Command ()
    {
        uuid = UUID.randomUUID ().toString ();
    }

    public Command (String cmd) // TODO: Throws CommandSyntaxException
    {
        this ();

        String cmdOriginal = cmd;

        cmd = cmd.trim ();
        
        if (!(cmd.startsWith ("$") && cmd.endsWith ("%")))
        {
            // TODO: throw CommandSyntaxException
            System.out.println ("Beschissener Syntax.");
        }

        // $ vom anfang und % vom Ende entfernen
        cmd = cmd.substring (1, cmd.length () - 1);

        // TODO: Mehr als 1 Element funktioniert noch nicht

        // Erstes Element des Kommandos ist der Kommandotyp
        String[] elems = cmd.split("\\$");
        type = Type.valueOf (elems[0].toUpperCase ());

        // Restliche Elemente als Parameter einlesen
        params = Arrays.copyOfRange (elems, 1, elems.length);
    
    }

    public String toString ()
    {
        String cmd = "";

        cmd += "$";
        cmd += type.toString ().toLowerCase ();
        cmd += String.join ("$", params);
        cmd += "%";

        return cmd;
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
}
