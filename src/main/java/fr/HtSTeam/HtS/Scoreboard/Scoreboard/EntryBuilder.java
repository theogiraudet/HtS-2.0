package fr.HtSTeam.HtS.Scoreboard.Scoreboard;

import java.util.LinkedList;
import java.util.List;

public final class EntryBuilder {

    private final LinkedList<Entry> entries = new LinkedList<>();

    public EntryBuilder blank() {
        return next("");
    }

    public EntryBuilder next(String string) {
        entries.add(new Entry(adapt(string), entries.size()));
        return this;
    }

    public List<Entry> build() {
        for (Entry entry : entries) {
            entry.setPosition(entries.size() - entry.getPosition());
        }
        return entries;
    }

    private String adapt(String entry) {
        if (entry.length() > 48) entry = entry.substring(0, 47);
        return Strings.format(entry);
    }

}