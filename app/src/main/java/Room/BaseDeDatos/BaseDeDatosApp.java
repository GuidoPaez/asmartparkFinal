package Room.BaseDeDatos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

import Room.DAO.CategoriaDAO;
import Room.DAO.EmpresaDAO;
import Room.DAO.EstacionamientoDAO;
import Room.DAO.OfertaDAO;
import Room.DAO.StripcenterDAO;
import Room.DAO.TipoEstacionamientoDAO;
import Room.Entidades.Categoria;
import Room.Entidades.Empresa;
import Room.Entidades.Estacionamiento;
import Room.Entidades.Oferta;
import Room.Entidades.Stripcenter;
import Room.Entidades.TipoEstacionamiento;

@Database(
        entities = {
            Empresa.class,
            Oferta.class,
            Categoria.class,
            Estacionamiento.class,
            Stripcenter.class,
            TipoEstacionamiento.class
        },
        version = 1)
public abstract class BaseDeDatosApp extends RoomDatabase {
    public abstract EmpresaDAO getEmpresaDAO();
    public abstract OfertaDAO getOfertaDAO();
    public abstract CategoriaDAO getCategoriaDAO();
    public abstract EstacionamientoDAO getEstacionamientoDAO();
    public abstract StripcenterDAO getStripcenterDAO();
    public abstract TipoEstacionamientoDAO getTipoEstacionamientoDAO();

    private static BaseDeDatosApp INSTANCE;

    public static BaseDeDatosApp recuperarBaseDatosApp(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BaseDeDatosApp.class, "smartpark").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}