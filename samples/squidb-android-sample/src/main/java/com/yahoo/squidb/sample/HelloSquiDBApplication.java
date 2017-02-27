/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.sample;

import android.app.Application;
import android.net.Uri;

import com.yahoo.squidb.android.AndroidLogger;
import com.yahoo.squidb.android.AndroidOpenHelper;
import com.yahoo.squidb.android.UriNotifier;
import com.yahoo.squidb.data.AbstractModel;
import com.yahoo.squidb.data.ISQLiteOpenHelper;
import com.yahoo.squidb.data.SquidDatabase;
import com.yahoo.squidb.sample.database.OpenHelperCreator;
import com.yahoo.squidb.sample.database.TasksDatabase;
import com.yahoo.squidb.sample.models.Task;
import com.yahoo.squidb.sql.SqlTable;
import com.yahoo.squidb.utility.SquidbLog;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HelloSquiDBApplication extends Application {

    public static final Uri CONTENT_URI = Uri.parse("content://com.yahoo.squidb.sample/tasks");

    @Override
    public void onCreate() {
        super.onCreate();
        SquidbLog.setLogger(new AndroidLogger());
        OpenHelperCreator.setCreator(new OpenHelperCreator() {
            @Override
            protected ISQLiteOpenHelper createOpenHelper(String databaseName, SquidDatabase.OpenHelperDelegate delegate,
                    int version) {
                return new AndroidOpenHelper(HelloSquiDBApplication.this, databaseName, delegate, version);
            }
        });
        TasksDatabase.getInstance().registerDataChangedNotifier(new UriNotifier(getContentResolver(), Task.TABLE) {
            @Override
            protected boolean accumulateNotificationObjects(@Nonnull Set<Uri> accumulatorSet, @Nonnull SqlTable<?> table,
                    @Nonnull SquidDatabase database, @Nonnull DBOperation operation,
                    @Nullable AbstractModel modelValues, long rowId) {
                return accumulatorSet.add(CONTENT_URI);
            }
        });
    }
}
