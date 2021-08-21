package com.wayapay.payment.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;

import javax.persistence.EntityManager;

@Aspect
public class EnableFilterAspect {

    @AfterReturning(pointcut = "bean(entityManagerFactory) && execution(* createEntityManager(..))", returning = "retVal")
    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {
        if (retVal != null && EntityManager.class.isInstance(retVal)) {
            Session session = ((EntityManager) retVal).unwrap(Session.class);
            //session.enableFilter("myAccount").setParameter("myParameter", "myValue");
        }
    }

}
