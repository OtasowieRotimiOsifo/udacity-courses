package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Credentials {
	private @Getter @Setter int credentialId;
	private @Getter @Setter String url;
	private @Getter @Setter String userName;
	private @Getter @Setter String key;
	private @Getter @Setter String password;
}
