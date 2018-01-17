package com.sylar.netty.server.spdy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.npn.NextProtoNego;

public class DefaultServerProvider implements NextProtoNego.ServerProvider {
	private static final List<String> PROTOCOLS =
			Collections.unmodifiableList(Arrays.asList("spdy/3", "http/1.1")); // 1
	private String protocol;
	
	public void unsupported() {
		protocol = "http/1.1";
	}

	public List<String> protocols() {
		return PROTOCOLS; 
	}
    
	public void protocolSelected(String protocol) {
		this.protocol = protocol; 
	}

	public String getSelectedProtocol() {
		return protocol; 
	}
}