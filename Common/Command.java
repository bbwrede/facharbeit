

/**
 * Repräsentiert ein Kommando das vom Server zum Client und umgekehrt gesendet wird.
 */
interface Command
{
    /**
     * Tritt auf wenn beim Parsen eines Strings in ein {@link ValidCommand} ein
     * Fehler auftritt.
     *
     * <p>Wenn aufgetreten, sollte statt eines {@link ValidCommands} ein 
     * {@link InvalidCommand} aus diesem String erstellt werden.
     */
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

    /**
     * Repräsentiert den Typ eines Commands, gemäß dem Protokoll zu finden in protokoll.md
     */
    public enum Type {
        MSG,
        RSP, 
        REG,
        HEARTBEAT,
        LEAVE,
        GETSTAT,
        STAT;
    }

    /**
     * @return die UUID eines Commands.
     */
    public abstract String getUUID ();
}
