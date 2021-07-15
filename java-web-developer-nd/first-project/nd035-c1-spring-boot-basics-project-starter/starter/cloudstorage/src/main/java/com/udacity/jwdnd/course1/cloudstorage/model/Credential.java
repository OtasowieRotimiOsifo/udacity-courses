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
public class Credential {
	private @Getter @Setter Integer credentialid;
	private @Getter @Setter String url;
	private @Getter @Setter String username;
	private @Getter @Setter String key;
	private @Getter @Setter String password;
	private @Getter @Setter String unencodedpassword;
	private @Getter @Setter Integer userid; 
}
