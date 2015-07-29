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
import com.bizislife.core.service.MessageFromPropertiesService;

import java.io.Serializable;
import java.util.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
    public static class RestExceptionResponse implements Serializable {
		private static final long serialVersionUID = -8381807732540995931L;
		private Boolean success;
        private List<String> messages;
        private Object data;

        public RestExceptionResponse(Boolean success, Object data, List<String> messages) {
            this.success = success;
            this.messages = messages;
            this.data = data;
        }

        public RestExceptionResponse(Boolean success, Object data, String... messages) {
            this.success = success;
            this.data = data;
            if (messages!=null && messages.length>0) {
                this.messages = Arrays.asList(messages);
            }
        }

        public RestExceptionResponse(Boolean success, Object data, String message) {
            this.success = success;
            this.data = data;
            if (message!=null) {
                messages = Collections.singletonList(message);
            }
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
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
    }

	@Autowired
	private MessageFromPropertiesService messageService;

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<RestExceptionResponse> handleUnknownException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);

        return new ResponseEntity<RestExceptionResponse>(new RestExceptionResponse(Boolean.FALSE, null, messageService.getMessageByLocale("message.error.default", null, Locale.ENGLISH)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

	

}
