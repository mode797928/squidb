/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.sample.models;

import com.yahoo.squidb.annotations.ColumnName;
import com.yahoo.squidb.annotations.ConstraintSql;
import com.yahoo.squidb.annotations.PrimaryKey;
import com.yahoo.squidb.annotations.TableModelSpec;

@TableModelSpec(className = "Tag", tableName = "tags",
        tableConstraint = "FOREIGN KEY(taskId) references tasks(_id) ON DELETE CASCADE")
public class TagSpec {

    @PrimaryKey
    @ColumnName("_id")
    long id;

    @ConstraintSql("NOT NULL")
    String tag;

    @ConstraintSql("NOT NULL")
    long taskId;

}
