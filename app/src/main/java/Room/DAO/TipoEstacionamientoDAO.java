package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.TipoEstacionamiento;

@Dao
public interface TipoEstacionamientoDAO {
    @Insert
    public void insert(TipoEstacionamiento... tipoestacionamientos);

    @Update
    public void update(TipoEstacionamiento... tipoestacionamientos);

    @Delete
    public void delete(TipoEstacionamiento tipoestacionamiento);

    @Query("SELECT * FROM tipoestacionamiento")
    public List<TipoEstacionamiento> getTipoEstacionamientos();

    @Query("SELECT * FROM tipoestacionamiento WHERE id = :id")
    public TipoEstacionamiento getTipoEstacionamientoPorId(int id);

    @Query("SELECT COUNT(*) FROM tipoestacionamiento")
    public int getTotalTipoEstacionamientos();
}
