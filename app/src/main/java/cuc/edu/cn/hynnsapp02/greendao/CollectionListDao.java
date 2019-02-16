package cuc.edu.cn.hynnsapp02.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;

import cuc.edu.cn.hynnsapp02.domain.CollectionList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COLLECTION_LIST".
*/
public class CollectionListDao extends AbstractDao<CollectionList, Long> {

    public static final String TABLENAME = "COLLECTION_LIST";

    /**
     * Properties of entity CollectionList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MovieNum = new Property(1, int.class, "movieNum", false, "MOVIE_NUM");
        public final static Property MovieIdList = new Property(2, String.class, "movieIdList", false, "MOVIE_ID_LIST");
    }

    private final StringConverter movieIdListConverter = new StringConverter();

    public CollectionListDao(DaoConfig config) {
        super(config);
    }
    
    public CollectionListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COLLECTION_LIST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MOVIE_NUM\" INTEGER NOT NULL ," + // 1: movieNum
                "\"MOVIE_ID_LIST\" TEXT);"); // 2: movieIdList
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COLLECTION_LIST\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CollectionList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMovieNum());
 
        List movieIdList = entity.getMovieIdList();
        if (movieIdList != null) {
            stmt.bindString(3, movieIdListConverter.convertToDatabaseValue(movieIdList));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CollectionList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMovieNum());
 
        List movieIdList = entity.getMovieIdList();
        if (movieIdList != null) {
            stmt.bindString(3, movieIdListConverter.convertToDatabaseValue(movieIdList));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CollectionList readEntity(Cursor cursor, int offset) {
        CollectionList entity = new CollectionList( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // movieNum
            cursor.isNull(offset + 2) ? null : movieIdListConverter.convertToEntityProperty(cursor.getString(offset + 2)) // movieIdList
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CollectionList entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMovieNum(cursor.getInt(offset + 1));
        entity.setMovieIdList(cursor.isNull(offset + 2) ? null : movieIdListConverter.convertToEntityProperty(cursor.getString(offset + 2)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CollectionList entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CollectionList entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CollectionList entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}