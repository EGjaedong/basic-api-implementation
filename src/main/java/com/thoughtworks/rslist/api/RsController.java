package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidPostParamException;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RsController {
    @Autowired
    private RsService rsService;

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEventEntity>> getList() throws InvalidRequestParamException {
            return ResponseEntity.ok().body(rsService.findAll());
    }

    @GetMapping("/rs/{id}")
    public ResponseEntity<RsEventEntity> getOne(@PathVariable int id) throws InvalidIndexException {
        if (id < 0)
            throw new InvalidIndexException();
        else
            return ResponseEntity.ok().body(rsService.findById(id));
    }

    @PostMapping("/rs/event")
    public ResponseEntity<String> addOneEvent(@RequestBody @Validated RsEvent rsEvent, BindingResult result) throws InvalidPostParamException {
        if (result.hasErrors())
            throw new InvalidPostParamException();
        int eventId = rsService.addEvent(rsEvent);
        if (eventId == 0)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.created(null)
                .header("eventId", String.valueOf(eventId)).build();
    }

    @PutMapping("/rs/{rsEventId}")
    public ResponseEntity<RsEventEntity> updateRsEventById(@PathVariable("rsEventId") int id, @RequestBody RsEvent rsEventUpdate) {
        RsEventEntity updatedRsEvent = rsService.updateById(id, rsEventUpdate);
        if (updatedRsEvent == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(updatedRsEvent);
    }

    @DeleteMapping("/rs/delete/{id}")
    public ResponseEntity<String> deleteRsEventById(@PathVariable int id) {

        return ResponseEntity.ok().body(null);
    }
}
