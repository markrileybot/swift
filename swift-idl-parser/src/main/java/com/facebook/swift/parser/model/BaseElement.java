/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.swift.parser.model;

import com.google.common.collect.ImmutableList;

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
			doc = doc.replaceAll("/\\*", "")
					.replaceAll("\\*/", " ")
					.replaceAll(" \\* ", "")
					.replace('*', ' ')
					.replace('\n', ' ')
					.trim();
			sb.append(doc).append(' ');
		}
		return sb.toString().trim();
	}
}
