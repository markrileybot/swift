package com.facebook.swift.parser.model;

import com.google.common.collect.ImmutableList;

import java.text.BreakIterator;
import java.util.Collections;
import java.util.List;

public class BaseElement {

	private final List<String> docs;

	public BaseElement(List<String> docs) {
		this.docs = docs == null ? Collections.emptyList() : ImmutableList.copyOf(docs);
	}

	public List<String> getDocs() {
		return docs;
	}

	public String getDocsJoined() {
		StringBuilder sb = new StringBuilder();
		for (String doc : docs) {
			BreakIterator bi = BreakIterator.getLineInstance();
			bi.setText(doc);
			for (int start = bi.first(), end = bi.next();
			     end != BreakIterator.DONE;
			     start = end, end = bi.next()) {

				String line = getCleanLine(doc, start, end);
				if (!line.isEmpty()) {
					sb.append(line).append(' ');
				}
			}
		}
		return sb.toString().trim();
	}

	private static String getCleanLine(String doc, int start, int end) {
		String line = doc.substring(start, end).trim();
		if (line.length() > 2) {
			if (line.startsWith("/*")) {
				line = line.substring(2);
			}
			if (line.startsWith("*")) {
				line = line.substring(1);
			}
			if (line.endsWith("*/")) {
				if (line.length() > 2) {
					line = line.substring(0, line.length() - 2);
				} else {
					line = "";
				}
			}
		} else {
			line = "";
		}
		return line;
	}
}
