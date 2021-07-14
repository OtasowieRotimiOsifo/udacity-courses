package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
@ToString
public class Note {
	private @Getter @Setter Integer noteid;
	private @Getter @Setter String notetitle;
	private @Getter @Setter String notedescription;
	private @Getter @Setter Integer userid;
	
	public String getIdAsString() {
		return noteid.toString();
	}
}
