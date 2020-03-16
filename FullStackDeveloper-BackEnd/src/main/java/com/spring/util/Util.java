package com.spring.util;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Util {

	public int hashCode( String fecha, String autor ) {

		return new HashCodeBuilder(17, 37).
		        append(fecha).
		        append(autor).
		        toHashCode();
	}

}
