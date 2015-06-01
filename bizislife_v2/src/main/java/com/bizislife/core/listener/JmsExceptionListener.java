package com.bizislife.core.listener;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.jms.connection.CachingConnectionFactory;

public class JmsExceptionListener implements ExceptionListener {
//    private static final Logger logger = LoggerFactory.getLogger(JmsExceptionListener.class);
	
	private CachingConnectionFactory cachingConnectionFactory;
	
    public void onException( final JMSException e )
    {
        e.printStackTrace();
        
//        if (logger.isDebugEnabled()){
//			logger.debug("onException() is called!");
//        }
		cachingConnectionFactory.onException(e);        
        
    }
    
    public CachingConnectionFactory getCachingConnectionFactory() {
		return cachingConnectionFactory;
	}

	public void setCachingConnectionFactory(CachingConnectionFactory cachingConnectionFactory) {
		this.cachingConnectionFactory = cachingConnectionFactory;
	}    
}