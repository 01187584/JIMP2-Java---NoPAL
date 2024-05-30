package observer;

import java.util.HashMap;

// Zarówno HashSet jak i HashMap byłyby niewygodne
public class EventStatusSet {
    private HashMap<String, EventStatus> RealSet;
    protected EventStatusSet() {
        RealSet = new HashMap<>();
    }
    protected EventStatusSet(EventStatusSet S) {
        RealSet = new HashMap<>(S.RealSet);
    }
    protected void add(EventStatus Status) {
        put(Status);
    }
    protected void put(EventStatus Status) {
        RealSet.put(Status.code, Status);
    }
    protected void put(String code, String defaultMessage) {
        EventStatus ES = get(code);
        if (ES == null) throw new NullPointerException("Status "+code+" nie został wcześniej dodany.");
        put(new EventStatus(code, defaultMessage, ES.isFinal));
    }
    protected void put(String code, String defaultMessage, boolean isFinal) {
        put(new EventStatus(code, defaultMessage, isFinal));
    }
    protected EventStatus get(String code) {
        return RealSet.get(code);
    }
    protected boolean contains(String code) {
        return RealSet.keySet().contains(code);
    }
    protected int size() {
        return RealSet.size();
    }
}