package com.gnd.oa.domain;

/**
 * 回复
 */
public class Reply extends Article {

	private static final long serialVersionUID = 1L;
	private Topic topic;  //所属的主题

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Reply [topic=" + topic + "]";
	}

}
