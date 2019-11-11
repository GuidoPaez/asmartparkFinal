package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.Oferta;

@Dao
public interface OfertaDAO {
    @Insert
    public void insert(Oferta... ofertas);

    @Update
    public void update(Oferta... ofertas);

    @Delete
    public void delete(Oferta oferta);

    @Query("SELECT * FROM oferta")
    public List<Oferta> getOfertas();

    @Query("SELECT * FROM oferta WHERE id = :id")
    public Oferta getOfertaPorId(int id);

    @Query("SELECT * FROM oferta WHERE id_empresa = :id_empresa")
    public List<Oferta> getOfertasPorEmpresa(int id_empresa);
}
