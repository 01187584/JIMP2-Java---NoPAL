package observer;

public class EventStatus {
    public final String code;
    public final String defaultMessage;
    //private String defaultMessage;
    public final boolean isFinal;

    public EventStatus(String code, String defaultMessage, boolean isFinal) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.isFinal = isFinal;
    }

    /*protected void setDefaultMessage(String to) {
        if (isFinal) throw new UnsupportedOperationException("Nie powinniśmy zmieniać komunikatów dla finalnych statusów.");
        defaultMessage = to;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }*/

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EventStatus)) return false;
        try {
            EventStatus oo = ((EventStatus)o);
            if (!oo.code.equals(code)) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}