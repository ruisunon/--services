package ai.sxr.shoppingla.config;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

public class ApplicationListener {
    @EventListener
    public void handleContextRefreshEvent(ContextClosedEvent ctxStartEvt){
        System.out.println("CONTEXT CLOSED EVENT: " + ctxStartEvt.toString());    }
}
