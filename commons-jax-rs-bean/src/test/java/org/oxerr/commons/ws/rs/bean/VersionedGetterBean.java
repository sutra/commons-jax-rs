package org.oxerr.commons.ws.rs.bean;

import javax.persistence.Version;

public class VersionedGetterBean {

	private String name;

	private long version;

	public VersionedGetterBean(String name, long version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}