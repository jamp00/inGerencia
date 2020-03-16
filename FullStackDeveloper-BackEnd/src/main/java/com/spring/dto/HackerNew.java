package com.spring.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "HackerNew")
public class HackerNew {

	@Id
	@Column(name = "id")
	private Integer id;
	
	private String storyTitle;
	private String storyUrl;
	private String author;
	private LocalDateTime createdAt;

	private boolean deleted;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStoryTitle() {
		return storyTitle;
	}
	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	public String getStoryUrl() {
		return storyUrl;
	}
	public void setStoryUrl(String storyUrl) {
		this.storyUrl = storyUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	@Override
	public String toString() {
		return "HackerNew [id=" + id + ", storyTitle=" + storyTitle + ", storyUrl=" + storyUrl + ", author=" + author
				+ ", createdAt=" + createdAt + ", deleted=" + deleted + "]";
	}

}
