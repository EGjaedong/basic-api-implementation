package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = initList();

    private List<RsEvent> initList() {
        List<RsEvent> list = new ArrayList<>();
        list.add(new RsEvent(1, "第一条事件", "经济"));
        list.add(new RsEvent(2, "第二条事件", "社会"));
        list.add(new RsEvent(3, "第三条事件", "民生"));
        return list;
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getList(@RequestParam(required = false) Integer start,
                                 @RequestParam(required = false) Integer end) {
        if (null == start || null == end) {
            return rsList;
        }
        return rsList.subList(start - 1, end);
    }

    @GetMapping("/rs/{index}")
    public RsEvent getOne(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @PostMapping("/rs/event")
    public void addOneEvent(@RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @PutMapping("/rs/update/{id}")
    public void updateRsEventByIndex(@PathVariable int id, @RequestBody RsEvent rsEventUpdate) {
        if (rsEventUpdate.getEventName() != null)
            rsList.get(id - 1).setEventName(rsEventUpdate.getEventName());
        if (rsEventUpdate.getKeyWord() != null)
            rsList.get(id - 1).setKeyWord(rsEventUpdate.getKeyWord());
    }

    @DeleteMapping("/rs/delete/{id}")
    public void deleteRsEventByIndex(@PathVariable int id) {
        if (id <= rsList.size())
            rsList.remove(id - 1);
    }
}
