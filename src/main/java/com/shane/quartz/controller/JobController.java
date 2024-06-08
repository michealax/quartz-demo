package com.shane.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.shane.quartz.component.QuartzManager;
import com.shane.quartz.domain.JobDetails;
import com.shane.quartz.domain.JobRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private QuartzManager manager;

    @SuppressWarnings("unchecked")
    private static Class<? extends QuartzJobBean> getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Class<? extends QuartzJobBean>) class1;
    }

    @PostMapping("/add")
    public void addJob(@RequestBody JobRequestDto request) throws Exception {
        manager.addOrUpdateJob(getClass(request.getJobClassName()), request.getJobClassName(), request.getJobGroupName(), request.getCronExpression());
    }

    @PostMapping("/pause")
    public void pauseJob(@RequestBody JobRequestDto request) {
        manager.pauseJob(request.getJobClassName(), request.getJobGroupName());
    }

    @PostMapping("/resume")
    public void resumeJob(@RequestBody JobRequestDto request) {
        manager.resumeJob(request.getJobClassName(), request.getJobGroupName());
    }

    @PostMapping("/reschedule")
    public void rescheduleJob(@RequestBody JobRequestDto request) throws Exception {
        manager.addJob(getClass(request.getJobClassName()), request.getJobClassName(), request.getJobGroupName(), request.getCronExpression());
    }

    @PostMapping("/delete")
    public void deleteJob(@RequestBody JobRequestDto request) {
        manager.deleteJob(request.getJobClassName(), request.getJobGroupName());
    }

    @GetMapping("/query")
    public Map<String, Object> queryJob(@RequestParam(value = "pageNum") Integer pageNum,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobDetails> jobAndTrigger = manager.queryAllJobBean(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }

    /**
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/addjob")
    public void addjob(@RequestParam(value = "jobClassName") String jobClassName,
                       @RequestParam(value = "jobGroupName") String jobGroupName,
                       @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        manager.addOrUpdateJob(getClass(jobClassName), jobClassName, jobGroupName, cronExpression);
    }

    /**
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/pausejob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName,
                         @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        manager.pauseJob(jobClassName, jobGroupName);
    }

    /**
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName,
                          @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        manager.resumeJob(jobClassName, jobGroupName);
    }

    /**
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        manager.addOrUpdateJob(getClass(jobClassName), jobClassName, jobGroupName, cronExpression);
    }

    /**
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestParam(value = "jobClassName") String jobClassName,
                          @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        manager.deleteJob(jobClassName, jobGroupName);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobDetails> jobAndTrigger = manager.queryAllJobBean(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }
}
