
import java.util.UUID;
import java.lang.IllegalArgumentException;

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

    public String toString ()
    {
        if (uuid == null)
        {
            return "$INVAL$NOUUID%";
        }
        else
        {
            return "$INVAL$" + uuid + "%";
        }
    }
}
