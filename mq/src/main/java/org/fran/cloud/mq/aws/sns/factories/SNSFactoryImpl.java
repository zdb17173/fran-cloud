package org.fran.cloud.mq.aws.sns.factories;

import java.util.List;

import javax.annotation.PostConstruct;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.Topic;
import org.fran.cloud.mq.aws.exceptions.SNSInitializationException;
import org.fran.cloud.mq.aws.sns.interfaces.SNSFactory;
import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;

public class SNSFactoryImpl implements SNSFactory {


	private String region;
	private String accessKey;
	private String securityKey;
	AmazonSNS sns;
	List<SNSTopic> topics;
	
	public SNSClient getClient(SNSTopic topic){
		return new SNSClient(sns,topic);
	}
	
	@PostConstruct
	public void init() throws SNSInitializationException {
		ClientConfiguration config = new ClientConfiguration();
		AmazonSNSClientBuilder builder = AmazonSNSClient.builder().withClientConfiguration(config)
				.withCredentials(new AWSCredentialsProvider() {

					@Override
					public void refresh() {
						System.out.println("refresh");
					}

					@Override
					public AWSCredentials getCredentials() {
						return new AWSCredentials() {
							@Override
							public String getAWSSecretKey() {
								return securityKey;
							}
							@Override
							public String getAWSAccessKeyId() {
								return accessKey;
							}
						};
					}
				});
		builder.setRegion(region);
		sns = builder.build();
		
		ListTopicsResult remoteTopics = sns.listTopics();
		if(remoteTopics!= null && remoteTopics.getTopics()!= null && remoteTopics.getTopics().size()> 0){
			
			for(SNSTopic t : topics){
				for(Topic topic : remoteTopics.getTopics()){
					if(topic.getTopicArn()!= null && topic.getTopicArn().endsWith(":"+ t.getTopicName())){
						t.setArn(topic.getTopicArn());
					}
				}
				if(t.getArn() == null || t.getArn().equals(""))
					throw new SNSInitializationException("topic mapping arn error ["+ t.getTopicName() +"]");
			}
		}else{
			throw new SNSInitializationException("remote topics null");
		}
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public List<SNSTopic> getTopics() {
		return topics;
	}

	public void setTopics(List<SNSTopic> topics) {
		this.topics = topics;
	}
	
	
}