package com.github.philippheuer.restapi.management.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class MetaDataContributor implements InfoContributor {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> details = new HashMap<>();

        Calendar startupTime = Calendar.getInstance();
        startupTime.setTimeInMillis(ctx.getStartupDate());
        startupTime.set(Calendar.MILLISECOND, 0);
        details.put("startup", startupTime.toInstant().toString());

        builder.withDetail("context", details);
    }
}
