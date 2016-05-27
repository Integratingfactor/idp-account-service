package com.integratingfactor.idp.account.api.ping;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integratingfactor.idp.lib.client.filter.IdpApiAuthFilter;
import com.integratingfactor.idp.lib.client.rbac.IdpApiRbacDetails;
import com.integratingfactor.idp.lib.client.rbac.IdpRbacPolicy;

@RestController
public class Hello {
    private static Logger LOG = Logger.getLogger(Hello.class.getName());

    @RequestMapping(value = { "/ping" })
    public Pong ping(HttpServletRequest request) {
        LOG.info("Ping request from " + request.getRemoteAddr());
        return new Pong("Hello World!");
    }

    @RequestMapping(value = { "/v1/ping" })
    @IdpRbacPolicy()
    public Pong securedPing(HttpServletRequest request) {
        LOG.info("Ping request from " + request.getRemoteAddr());
        IdpApiRbacDetails details = IdpApiAuthFilter.getRbacDetails();
        return new Pong("Hello " + details.getAccountId() + "!");
    }

    public static class Pong {
        private String message;

        public Pong(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
