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

import com.facebook.swift.parser.visitor.DocumentVisitor;
import com.facebook.swift.parser.visitor.Visitable;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractStruct
        extends Definition
{
    private final String name;
    private final List<ThriftField> fields;
    private final List<TypeAnnotation> annotations;


	public AbstractStruct(String name, List<ThriftField> fields, List<TypeAnnotation> annotations)
	{
		this(name, fields, annotations, null);
	}

    public AbstractStruct(String name, List<ThriftField> fields, List<TypeAnnotation> annotations, List<String> comment)
    {
	    super(comment);
	    this.name = checkNotNull(name, "name");
        this.fields = ImmutableList.copyOf(checkNotNull(fields, "fields"));
        this.annotations = ImmutableList.copyOf(checkNotNull(annotations, "annotations"));
    }

    @Override
    public String getName()
    {
        return name;
    }

    public List<ThriftField> getFields()
    {
        return fields;
    }

    public List<TypeAnnotation> getAnnotations()
    {
        return annotations;
    }

    @Override
    public void visit(final DocumentVisitor visitor) throws IOException
    {
        super.visit(visitor);

        Visitable.Utils.visitAll(visitor, getFields());
        Visitable.Utils.visitAll(visitor, getAnnotations());
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("name", name)
                          .add("fields", fields)
                          .add("annotations", annotations)
		                  .add("docs", getDocs())
                          .toString();
    }
}
