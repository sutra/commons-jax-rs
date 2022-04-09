package org.oxerr.commons.ws.rs.bean;

import javax.persistence.Version;

public class VersionedFieldBean {

	private String name;

	@Version
	private long version;

	public VersionedFieldBean(String name, long version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}