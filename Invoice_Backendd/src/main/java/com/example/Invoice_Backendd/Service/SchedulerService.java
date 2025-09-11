package com.example.Invoice_Backendd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    private MemberService memberService;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void markInactiveMembers() {
        memberService.updateInactiveMembers();
    }
}