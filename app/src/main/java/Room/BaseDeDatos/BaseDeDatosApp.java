package Room.BaseDeDatos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

import Room.DAO.CategoriaDAO;
import Room.DAO.EmpresaDAO;
import Room.DAO.OfertaDAO;
import Room.Entidades.Categoria;
import Room.Entidades.Empresa;
import Room.Entidades.Oferta;

@Database(entities = {Empresa.class, Oferta.class, Categoria.class}, version = 1)
public abstract class BaseDeDatosApp extends RoomDatabase {
    public abstract EmpresaDAO getEmpresaDAO();
    public abstract OfertaDAO getOfertaDAO();
    public abstract CategoriaDAO getCategoriaDAO();

    private static BaseDeDatosApp INSTANCE;

    public static BaseDeDatosApp recuperarBaseDatosApp(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BaseDeDatosApp.class, "marketing").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}