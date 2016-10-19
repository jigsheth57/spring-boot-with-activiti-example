package org.activiti.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.model.Applicant;
import org.activiti.model.ApplicantRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HireProcessService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private Environment env;

    private static final Log log = LogFactory.getLog(HireProcessService.class);

	@Transactional
    public String startProcess(Applicant applicant) {

		applicant = applicantRepository.save(applicant);
		log.info("Starting Hire Process for applicant: "+applicant);

        Map<String, Object> vars = Collections.<String, Object>singletonMap("applicant", applicant);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("hireProcessWithJpa", vars);

        String pid = processInstance.getProcessInstanceId();
        log.info("Instance ID: "+env.getProperty("CF_INSTANCE_INDEX"));
        log.info("Process Instance ID: "+pid);
        // Verify that we started a new process instance
        log.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());

        return pid;
    }

	@Transactional
    public List<Task> getTasks(String pid) {
        log.info("Instance ID: "+env.getProperty("CF_INSTANCE_INDEX"));
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(pid)
                .orderByTaskName().asc()
                .list();
        return tasks;
    }

	@Transactional
	public void completeTelephoneInterview(String pid, Map<String, Object> taskVariables) {
        log.info("Instance ID: "+env.getProperty("CF_INSTANCE_INDEX"));
        Task task = taskService.createTaskQuery()
                .processInstanceId(pid)
                .taskName("Telephone interview")
                .singleResult();
        taskService.complete(task.getId(), taskVariables);
	}

	@Transactional
	public void completeFinancialNegotiation(String pid, Map<String, Object> taskVariables) {
        log.info("Instance ID: "+env.getProperty("CF_INSTANCE_INDEX"));
        Task task = taskService.createTaskQuery()
                .processInstanceId(pid)
                .taskName("Financial negotiation")
                .singleResult();
        taskService.complete(task.getId(), taskVariables);
	}

	@Transactional
	public void completeTechInterview(String pid, Map<String, Object> taskVariables) {
        log.info("Instance ID: "+env.getProperty("CF_INSTANCE_INDEX"));
        Task task = taskService.createTaskQuery()
                .processInstanceId(pid)
                .taskName("Tech interview")
                .singleResult();
        taskService.complete(task.getId(), taskVariables);
	}
}
