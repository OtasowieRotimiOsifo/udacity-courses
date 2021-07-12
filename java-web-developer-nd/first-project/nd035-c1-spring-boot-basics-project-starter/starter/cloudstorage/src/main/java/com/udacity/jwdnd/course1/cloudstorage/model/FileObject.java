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
public class FileObject {
	private @Getter @Setter Integer fileid;
	private @Getter @Setter String filename;
	private @Getter @Setter String filesize;
	private @Getter @Setter String contenttype;
	private @Getter @Setter int userid;
	private @Getter @Setter byte[] filedata;
	
	public String fileIdAsString() {
		return fileid.toString();
	}
}
