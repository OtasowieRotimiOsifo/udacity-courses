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
public class Notes {
	private @Getter @Setter int nodeId;
	private @Getter @Setter String notetitle;
	private @Getter @Setter int userid;
	private @Getter @Setter String notedescription;
}