package org.fran.cloud.mq.aws.sqs.factories;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class SQSMessageBody {

	static protected ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {  
		    private static final long serialVersionUID = 1L;
		    @Override  
		    public String nameForSetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {  
		        return method.getName().substring(3);  
		    }
		    @Override  
		    public String nameForGetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {  
		        return method.getName().substring(3);  
		    }  
		});
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static String encode(String message) throws JsonProcessingException{
		SNSBody body = new SNSBody();
		body.setMessage(message);
		return mapper.writeValueAsString(body);
	}
	
	public static String decode(String body) throws JsonParseException, JsonMappingException, IOException{
		SNSBody snsbody = mapper.readValue(body,SNSBody.class);											
		return snsbody.getMessage();
	}
}
