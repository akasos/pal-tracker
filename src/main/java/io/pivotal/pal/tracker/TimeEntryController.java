package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping(value ="/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
    }



    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
            TimeEntry newTimeEntry = timeEntryRepository.find(timeEntryId);
            if(newTimeEntry != null) {
                return new ResponseEntity(newTimeEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(newTimeEntry, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> newTimeEntryList = timeEntryRepository.list();
        return new ResponseEntity(newTimeEntryList, HttpStatus.OK);
    }

    @PutMapping("/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry newTimeEntry = timeEntryRepository.update(timeEntryId, expected);
        if(newTimeEntry != null) {
            return new ResponseEntity(newTimeEntry, HttpStatus.OK);
        }
        return new ResponseEntity(newTimeEntry, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
