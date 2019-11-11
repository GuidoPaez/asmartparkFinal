package Room.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "stripcenter",
        indices = {
                @Index("id"),
                @Index("id_estacionamiento"),
                @Index("id_empresa")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Estacionamiento.class,
                        parentColumns = "id",
                        childColumns = "id_estacionamiento",
                        onDelete = ForeignKey.NO_ACTION),
                @ForeignKey(
                        entity = Empresa.class,
                        parentColumns = "id",
                        childColumns = "id_empresa",
                        onDelete = ForeignKey.NO_ACTION)
        }
)
public class Stripcenter {
    private int id_estacionamiento;
    private int id_empresa;
    @PrimaryKey
    @NonNull
    private int id;

    public int getId_estacionamiento() {
        return id_estacionamiento;
    }

    public void setId_estacionamiento(int id_estacionamiento) {
        this.id_estacionamiento = id_estacionamiento;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}