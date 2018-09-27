package com.movit.rwe.modules.bi.base.entity.mysql;

import java.util.Date;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : JobsLog.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 组对象
 *
 */
public class JobsLog extends DataEntity<JobsLog>{

	private static final long serialVersionUID = -6388898835927033559L;
	
	private Integer idJob;
	private String channelId;
	private String jobName;
	private String status;
	private Integer linesRead;
	private Integer linesWritten;
	private Integer linesUpdated;
	private Integer linesInput;
	private Integer linesOutput;
	private Integer linesRejected;
	private String errors;
	private Date startDate;
	private Date endDate;
	private Date logDate;
	private Date depDate;
	private Date replayDate;
	private String logField;
	public Integer getIdJob() {
		return idJob;
	}
	public void setIdJob(Integer idJob) {
		this.idJob = idJob;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLinesRead() {
		return linesRead;
	}
	public void setLinesRead(Integer linesRead) {
		this.linesRead = linesRead;
	}
	public Integer getLinesWritten() {
		return linesWritten;
	}
	public void setLinesWritten(Integer linesWritten) {
		this.linesWritten = linesWritten;
	}
	public Integer getLinesUpdated() {
		return linesUpdated;
	}
	public void setLinesUpdated(Integer linesUpdated) {
		this.linesUpdated = linesUpdated;
	}
	public Integer getLinesInput() {
		return linesInput;
	}
	public void setLinesInput(Integer linesInput) {
		this.linesInput = linesInput;
	}
	public Integer getLinesOutput() {
		return linesOutput;
	}
	public void setLinesOutput(Integer linesOutput) {
		this.linesOutput = linesOutput;
	}
	public Integer getLinesRejected() {
		return linesRejected;
	}
	public void setLinesRejected(Integer linesRejected) {
		this.linesRejected = linesRejected;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Date getDepDate() {
		return depDate;
	}
	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}
	public Date getReplayDate() {
		return replayDate;
	}
	public void setReplayDate(Date replayDate) {
		this.replayDate = replayDate;
	}
	public String getLogField() {
		return logField;
	}
	public void setLogField(String logField) {
		this.logField = logField;
	}
	

}
