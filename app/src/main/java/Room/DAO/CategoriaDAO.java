package Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Room.Entidades.Categoria;

@Dao
public interface CategoriaDAO {
    @Insert
    public void insert(Categoria... empresas);

    @Update
    public void update(Categoria... categorias);

    @Delete
    public void delete(Categoria categoria);

    @Query("SELECT * FROM categoria")
    public List<Categoria> getCategorias();

    @Query("SELECT * FROM categoria WHERE id = :id")
    public Categoria getCategoriaPorId(int id);

    @Query("SELECT COUNT(*) FROM categoria")
    public int getTotalCategorias();
}
