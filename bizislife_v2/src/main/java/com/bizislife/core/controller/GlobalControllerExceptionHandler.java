package com.bizislife.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bizislife.core.controller.GlobalControllerExceptionHandler.RestExceptionResponse;
import com.bizislife.core.exception.BizisLifeBaseException;
import com.bizislife.core.exception.NoUserCreate;
import com.bizislife.core.service.MessageFromPropertiesService;

import java.io.Serializable;
import java.util.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
    public static class RestExceptionResponse implements Serializable {
		private static final long serialVersionUID = -8381807732540995931L;
		private int reason = -1;
        private List<String> messages;
        private Object data;

        public RestExceptionResponse(Object data, int reason, List<String> messages) {
            this.messages = messages;
            this.data = data;
            if (reason>-1) {
            	this.reason = reason;
            }
        }

        public RestExceptionResponse(Object data, int reason, String... messages) {
            this.data = data;
            if (messages!=null && messages.length>0) {
                this.messages = Arrays.asList(messages);
            }
            if (reason>-1) {
            	this.reason = reason;
            }
        }

        public RestExceptionResponse(Object data, int reason, String message) {
            this.data = data;
            if (message!=null) {
                messages = Collections.singletonList(message);
            }
            if (reason>-1) {
            	this.reason = reason;
            }
        }

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

		public int getReason() {
			return reason;
		}

		public void setReason(int reason) {
			this.reason = reason;
		}
        
    }

	@Autowired
	private MessageFromPropertiesService messageService;

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<RestExceptionResponse> handleUnknownException(Exception ex, WebRequest request) {
        logger.error(ex.toString(), ex);
        return new ResponseEntity(new RestExceptionResponse(ex, -1, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(value = { BizisLifeBaseException.class })
    protected ResponseEntity<RestExceptionResponse> noUserException(BizisLifeBaseException ex, WebRequest request) {
        logger.error(ex.toString(), ex);
        if (ex.getMessages()!=null && ex.getMessages().size()>0) {
            return new ResponseEntity(new RestExceptionResponse(ex, ex.getReason(), ex.getMessages()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
        	return new ResponseEntity(new RestExceptionResponse(ex, ex.getReason(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    

	

}
