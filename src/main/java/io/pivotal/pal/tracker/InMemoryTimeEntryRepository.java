package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private ConcurrentHashMap<Long, TimeEntry> timeEntriesDB = new ConcurrentHashMap<>();
    private AtomicLong databaseIndex = new AtomicLong(0);

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long currentId = databaseIndex.incrementAndGet();
        timeEntriesDB.put(
                currentId,
                new TimeEntry(
                        currentId,
                        timeEntry.getProjectId(),
                        timeEntry.getUserId(),
                        timeEntry.getDate(),
                        timeEntry.getHours()
                )
        );
        return timeEntriesDB.get(databaseIndex.get());
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntriesDB.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntriesDB.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntriesDB.replace(id, new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours()));
        return timeEntriesDB.get(id);
    }

    @Override
    public void delete(long id) {
        timeEntriesDB.remove(id);
    }



}
