package ai.sxr.shoppingla.auth.controller;


//  https://tanzu.vmware.com/developer/guides/spring-integration-lock/
//  How To Implement a Spring Distributed Lock
//
//     When designing microservices, the typical pattern is to design services that can handle multiple instances
//    running at the same time. However, there are situations where processing only on a single service is preferable.
//    For example, in the Leader Pattern, you cannot synchronize if the code runs in different pods. As a result,
//    you have to use an external method that is fraught with pitfalls during implementation1.
//
//    Thankfully, Spring has done a lot of the hard work. All you need to do is provide it with a database connection
//    and it will create a distributed lock. This example will show the lock with both Redis and JDBC.
import ai.sxr.shoppingla.utils.lock.LockService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LockController {
    private final LockService lockService;

    public LockController(LockService lockService) {
        this.lockService = lockService;
    }

    @PutMapping("/lock")
    public String lock(){
        return lockService.lock();
    }

    @PutMapping("/properLock")
    public String properLock(){
        return lockService.properLock();
    }

    @PutMapping("/failLock")
    public String failLock(){
        lockService.failLock();
        return "fail lock called, output in logs";
    }

    //    @PutMapping("/testWaitLock")
    //    public String waitLock(@RequestParam long waitTime, @RequestParam long sleepTime){
    //        return lockService.testWaitLock(waitTime, sleepTime);
    //    }
}