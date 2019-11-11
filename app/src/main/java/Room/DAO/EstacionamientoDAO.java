package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.Estacionamiento;

@Dao
public interface EstacionamientoDAO {
    @Insert
    public void insert(Estacionamiento... estacionamientos);

    @Update
    public void update(Estacionamiento... estacionamientos);

    @Delete
    public void delete(Estacionamiento estacionamiento);

    @Query("SELECT * FROM estacionamiento")
    public List<Estacionamiento> getEstacionamientos();

    @Query("SELECT * FROM estacionamiento WHERE id = :id")
    public Estacionamiento getEstacionamientoPorId(int id);

    @Query("SELECT COUNT(*) FROM estacionamiento")
    public int getTotalEstacionamientos();
}
