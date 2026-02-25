package com.lockinapp.focusblocker.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FocusSessionDao_Impl implements FocusSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FocusSessionEntity> __insertionAdapterOfFocusSessionEntity;

  private final EntityDeletionOrUpdateAdapter<FocusSessionEntity> __updateAdapterOfFocusSessionEntity;

  public FocusSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFocusSessionEntity = new EntityInsertionAdapter<FocusSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `focus_sessions` (`id`,`startTimeMillis`,`endTimeMillis`,`targetDurationMillis`,`isStrict`,`completedSuccessfully`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FocusSessionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTimeMillis());
        if (entity.getEndTimeMillis() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getEndTimeMillis());
        }
        statement.bindLong(4, entity.getTargetDurationMillis());
        final int _tmp = entity.isStrict() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final Integer _tmp_1 = entity.getCompletedSuccessfully() == null ? null : (entity.getCompletedSuccessfully() ? 1 : 0);
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
      }
    };
    this.__updateAdapterOfFocusSessionEntity = new EntityDeletionOrUpdateAdapter<FocusSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `focus_sessions` SET `id` = ?,`startTimeMillis` = ?,`endTimeMillis` = ?,`targetDurationMillis` = ?,`isStrict` = ?,`completedSuccessfully` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FocusSessionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTimeMillis());
        if (entity.getEndTimeMillis() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getEndTimeMillis());
        }
        statement.bindLong(4, entity.getTargetDurationMillis());
        final int _tmp = entity.isStrict() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final Integer _tmp_1 = entity.getCompletedSuccessfully() == null ? null : (entity.getCompletedSuccessfully() ? 1 : 0);
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertSession(final FocusSessionEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFocusSessionEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSession(final FocusSessionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFocusSessionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<FocusSessionEntity> observeActiveSession() {
    final String _sql = "SELECT * FROM focus_sessions WHERE endTimeMillis IS NULL LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"focus_sessions"}, new Callable<FocusSessionEntity>() {
      @Override
      @Nullable
      public FocusSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMillis");
          final int _cursorIndexOfEndTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMillis");
          final int _cursorIndexOfTargetDurationMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDurationMillis");
          final int _cursorIndexOfIsStrict = CursorUtil.getColumnIndexOrThrow(_cursor, "isStrict");
          final int _cursorIndexOfCompletedSuccessfully = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSuccessfully");
          final FocusSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTimeMillis;
            _tmpStartTimeMillis = _cursor.getLong(_cursorIndexOfStartTimeMillis);
            final Long _tmpEndTimeMillis;
            if (_cursor.isNull(_cursorIndexOfEndTimeMillis)) {
              _tmpEndTimeMillis = null;
            } else {
              _tmpEndTimeMillis = _cursor.getLong(_cursorIndexOfEndTimeMillis);
            }
            final long _tmpTargetDurationMillis;
            _tmpTargetDurationMillis = _cursor.getLong(_cursorIndexOfTargetDurationMillis);
            final boolean _tmpIsStrict;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStrict);
            _tmpIsStrict = _tmp != 0;
            final Boolean _tmpCompletedSuccessfully;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCompletedSuccessfully)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfCompletedSuccessfully);
            }
            _tmpCompletedSuccessfully = _tmp_1 == null ? null : _tmp_1 != 0;
            _result = new FocusSessionEntity(_tmpId,_tmpStartTimeMillis,_tmpEndTimeMillis,_tmpTargetDurationMillis,_tmpIsStrict,_tmpCompletedSuccessfully);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FocusSessionEntity>> observeAllSessions() {
    final String _sql = "SELECT * FROM focus_sessions ORDER BY startTimeMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"focus_sessions"}, new Callable<List<FocusSessionEntity>>() {
      @Override
      @NonNull
      public List<FocusSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeMillis");
          final int _cursorIndexOfEndTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeMillis");
          final int _cursorIndexOfTargetDurationMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDurationMillis");
          final int _cursorIndexOfIsStrict = CursorUtil.getColumnIndexOrThrow(_cursor, "isStrict");
          final int _cursorIndexOfCompletedSuccessfully = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSuccessfully");
          final List<FocusSessionEntity> _result = new ArrayList<FocusSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FocusSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTimeMillis;
            _tmpStartTimeMillis = _cursor.getLong(_cursorIndexOfStartTimeMillis);
            final Long _tmpEndTimeMillis;
            if (_cursor.isNull(_cursorIndexOfEndTimeMillis)) {
              _tmpEndTimeMillis = null;
            } else {
              _tmpEndTimeMillis = _cursor.getLong(_cursorIndexOfEndTimeMillis);
            }
            final long _tmpTargetDurationMillis;
            _tmpTargetDurationMillis = _cursor.getLong(_cursorIndexOfTargetDurationMillis);
            final boolean _tmpIsStrict;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStrict);
            _tmpIsStrict = _tmp != 0;
            final Boolean _tmpCompletedSuccessfully;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCompletedSuccessfully)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfCompletedSuccessfully);
            }
            _tmpCompletedSuccessfully = _tmp_1 == null ? null : _tmp_1 != 0;
            _item = new FocusSessionEntity(_tmpId,_tmpStartTimeMillis,_tmpEndTimeMillis,_tmpTargetDurationMillis,_tmpIsStrict,_tmpCompletedSuccessfully);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
