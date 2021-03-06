package com.crazy.taolove.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.crazy.taolove.entity.Contact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONTACT".
*/
public class ContactDao extends AbstractDao<Contact, Long> {

    public static final String TABLENAME = "CONTACT";

    /**
     * Properties of entity Contact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property Nickname = new Property(2, String.class, "nickname", false, "NICKNAME");
        public final static Property User_name = new Property(3, String.class, "user_name", false, "USER_NAME");
        public final static Property Birthday = new Property(4, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Face_url = new Property(5, String.class, "face_url", false, "FACE_URL");
        public final static Property Sex = new Property(6, String.class, "sex", false, "SEX");
        public final static Property Signature = new Property(7, String.class, "signature", false, "SIGNATURE");
        public final static Property Rename = new Property(8, String.class, "rename", false, "RENAME");
        public final static Property PyInitial = new Property(9, String.class, "pyInitial", false, "PY_INITIAL");
        public final static Property ConRemarkPYShort = new Property(10, String.class, "conRemarkPYShort", false, "CON_REMARK_PYSHORT");
        public final static Property State_marry = new Property(11, String.class, "state_marry", false, "STATE_MARRY");
        public final static Property Constellation = new Property(12, String.class, "constellation", false, "CONSTELLATION");
        public final static Property IsFromAdd = new Property(13, boolean.class, "isFromAdd", false, "IS_FROM_ADD");
    }


    public ContactDao(DaoConfig config) {
        super(config);
    }
    
    public ContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" TEXT NOT NULL UNIQUE ," + // 1: userId
                "\"NICKNAME\" TEXT," + // 2: nickname
                "\"USER_NAME\" TEXT," + // 3: user_name
                "\"BIRTHDAY\" TEXT," + // 4: birthday
                "\"FACE_URL\" TEXT," + // 5: face_url
                "\"SEX\" TEXT," + // 6: sex
                "\"SIGNATURE\" TEXT," + // 7: signature
                "\"RENAME\" TEXT," + // 8: rename
                "\"PY_INITIAL\" TEXT," + // 9: pyInitial
                "\"CON_REMARK_PYSHORT\" TEXT," + // 10: conRemarkPYShort
                "\"STATE_MARRY\" TEXT," + // 11: state_marry
                "\"CONSTELLATION\" TEXT," + // 12: constellation
                "\"IS_FROM_ADD\" INTEGER NOT NULL );"); // 13: isFromAdd
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUserId());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(3, nickname);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(4, user_name);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(5, birthday);
        }
 
        String face_url = entity.getFace_url();
        if (face_url != null) {
            stmt.bindString(6, face_url);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(8, signature);
        }
 
        String rename = entity.getRename();
        if (rename != null) {
            stmt.bindString(9, rename);
        }
 
        String pyInitial = entity.getPyInitial();
        if (pyInitial != null) {
            stmt.bindString(10, pyInitial);
        }
 
        String conRemarkPYShort = entity.getConRemarkPYShort();
        if (conRemarkPYShort != null) {
            stmt.bindString(11, conRemarkPYShort);
        }
 
        String state_marry = entity.getState_marry();
        if (state_marry != null) {
            stmt.bindString(12, state_marry);
        }
 
        String constellation = entity.getConstellation();
        if (constellation != null) {
            stmt.bindString(13, constellation);
        }
        stmt.bindLong(14, entity.getIsFromAdd() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUserId());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(3, nickname);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(4, user_name);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(5, birthday);
        }
 
        String face_url = entity.getFace_url();
        if (face_url != null) {
            stmt.bindString(6, face_url);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(8, signature);
        }
 
        String rename = entity.getRename();
        if (rename != null) {
            stmt.bindString(9, rename);
        }
 
        String pyInitial = entity.getPyInitial();
        if (pyInitial != null) {
            stmt.bindString(10, pyInitial);
        }
 
        String conRemarkPYShort = entity.getConRemarkPYShort();
        if (conRemarkPYShort != null) {
            stmt.bindString(11, conRemarkPYShort);
        }
 
        String state_marry = entity.getState_marry();
        if (state_marry != null) {
            stmt.bindString(12, state_marry);
        }
 
        String constellation = entity.getConstellation();
        if (constellation != null) {
            stmt.bindString(13, constellation);
        }
        stmt.bindLong(14, entity.getIsFromAdd() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Contact readEntity(Cursor cursor, int offset) {
        Contact entity = new Contact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // user_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // birthday
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // face_url
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sex
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // signature
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // rename
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // pyInitial
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // conRemarkPYShort
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // state_marry
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // constellation
            cursor.getShort(offset + 13) != 0 // isFromAdd
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Contact entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getString(offset + 1));
        entity.setNickname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUser_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBirthday(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFace_url(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSignature(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRename(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPyInitial(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setConRemarkPYShort(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setState_marry(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setConstellation(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIsFromAdd(cursor.getShort(offset + 13) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Contact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Contact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Contact entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
