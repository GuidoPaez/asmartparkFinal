package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.Stripcenter;

@Dao
public interface StripcenterDAO {
    @Insert
    public void insert(Stripcenter... stripcenters);

    @Update
    public void update(Stripcenter... stripcenters);

    @Delete
    public void delete(Stripcenter stripcenter);

    @Query("SELECT * FROM stripcenter")
    public List<Stripcenter> getStripcenters();

    @Query("SELECT * FROM stripcenter WHERE id = :id")
    public Stripcenter getStripcenterPorId(int id);

    @Query("SELECT COUNT(*) FROM stripcenter")
    public int getTotalStripcenters();
}
