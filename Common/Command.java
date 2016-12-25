
interface Command
{
    public class ParsingException extends Exception
    {
        public ParsingException () {  }
        
        public ParsingException (String message)
        {
            super (message);
        }
        
        public ParsingException (Throwable cause)
        {
            super (cause);
        }
        
        public ParsingException (String message, Throwable cause)
        {
            super (message, cause);
        }
    }    

    public enum Type {
        MSG,
        RSP, 
        REG,
        HEARTBEAT,
        LEAVE,
        GETSTAT,
        STAT;
    }

    public abstract String getUUID ();
}
