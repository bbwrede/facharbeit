
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

    public Command (String cmd)
    {
        this ();

        // Erstes Element des Kommandos ist der Kommandotyp
        String[] elems = cmd.split("$");
        type = Type.valueOf (elems[0].toUpperCase ());

        // Restliche Elemente als Parameter einlesen
        params = Arrays.copyOfRange (elems, 1, elems.length);
    }

    public Command (String cmd, String pUuid)
    {
        this (cmd);
        uuid = pUuid;
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
