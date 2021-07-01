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
public class User {
	private @Getter @Setter Integer userid;
	private @Getter @Setter String username;
	private @Getter @Setter String salt;
	private @Getter @Setter String password;
	private @Getter @Setter String firstname;
	private @Getter @Setter String lastname;
}
