package com.spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HitsWrapper {

	@JsonProperty("hits")
	private List<JSONHackNew> hits;

	public HitsWrapper() {
		super();		
	}

	public HitsWrapper(List<JSONHackNew> hits) {
		this.hits = hits;
	}

	public List<JSONHackNew> getJSONHackNew() {
		return this.hits;
	}

	public void setJSONHackNew(List<JSONHackNew> hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "HitsWrapper [hits=" + hits + "]";
	}
	
}
