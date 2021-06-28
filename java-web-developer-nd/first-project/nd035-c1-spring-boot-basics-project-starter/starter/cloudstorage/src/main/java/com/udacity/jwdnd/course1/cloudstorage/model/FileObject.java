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
	private @Getter @Setter int fileId;
	private @Getter @Setter String fileName;
	private @Getter @Setter long fileSize;
	private @Getter @Setter String contenttype;
	private @Getter @Setter byte[] content;
}
