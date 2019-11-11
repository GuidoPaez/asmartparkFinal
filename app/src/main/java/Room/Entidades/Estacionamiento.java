package Room.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "estacionamiento",
        indices = {
                @Index("id"),
                @Index("id_tipo")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = TipoEstacionamiento.class,
                        parentColumns = "id",
                        childColumns = "id_tipo",
                        onDelete = ForeignKey.NO_ACTION)
        }
)
public class Estacionamiento {
    private Double latitud;
    private Double longitud;
    private String nombre;
    private int id_tipo;
    @PrimaryKey
    @NonNull
    private int id;

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}