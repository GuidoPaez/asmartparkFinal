package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.Empresa;

@Dao
public interface EmpresaDAO {
    @Insert
    public void insert(Empresa... empresas);

    @Update
    public void update(Empresa... empresas);

    @Delete
    public void delete(Empresa empresa);

    @Query("SELECT * FROM empresa")
    public List<Empresa> getEmpresas();

    @Query("SELECT * FROM empresa WHERE id = :id")
    public Empresa getEmpresaPorId(int id);

    @Query("SELECT COUNT(*) FROM empresa")
    public int getTotalEmpresas();
}
