package com.bizislife.core.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.Enumeration;

public class JmsMessageSender {
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate value)
    {
        jmsTemplate = value;
    }

    public void send(Destination destination, final MapMessage msg) {

        MessageCreator mc = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();

                for (Enumeration e = msg.getMapNames();e.hasMoreElements();) {
                    String name = e.nextElement().toString();
//					  System.out.println(name + "=" + msg.getObject(name));
                    message.setObject(name, msg.getObject(name));
                }
                return message;
            }
        };

        jmsTemplate.send(destination, mc);
    }


}
