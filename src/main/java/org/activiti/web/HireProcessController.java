package org.activiti.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.activiti.model.Applicant;
import org.activiti.service.HireProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HireProcessController {

    @Autowired
    private HireProcessService processService;
    
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String startHireProcess(@RequestBody Map<String, String> data) {
        Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
        String pid = processService.startProcess(applicant);
        return String.format("{\"process_instance_id\": \"%s\"}",pid);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/completeTelephoneInterview/{pid}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void completeTelephoneInterview(@PathVariable String pid, @RequestBody Map<String, String> data) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("telephoneInterviewOutcome", data.get("telephoneInterviewOutcome"));
        processService.completeTelephoneInterview(pid, taskVariables);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/completeFinancialNegotiation/{pid}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void completeFinancialNegotiation(@PathVariable String pid, @RequestBody Map<String, String> data) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("financialOk", false);
        processService.completeFinancialNegotiation(pid, taskVariables);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/completeTechInterview/{pid}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void completeTechInterview(@PathVariable String pid, @RequestBody Map<String, String> data) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("techOk", data.get("techOk"));
        processService.completeTechInterview(pid, taskVariables);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/tasks/{pid}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@PathVariable String pid) {
        List<Task> tasks = processService.getTasks(pid);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    static class TaskRepresentation {

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

         public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }

}