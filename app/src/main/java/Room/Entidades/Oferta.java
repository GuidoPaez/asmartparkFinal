package Room.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "oferta",
        indices = {
                @Index("id"),
                @Index("id_empresa"),
                @Index("id_categoria")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Categoria.class,
                        parentColumns = "id",
                        childColumns = "id_categoria",
                        onDelete = ForeignKey.NO_ACTION),
                @ForeignKey(
                        entity = Empresa.class,
                        parentColumns = "id",
                        childColumns = "id_empresa",
                        onDelete = ForeignKey.NO_ACTION)
        }
)
public class Oferta {

    private Float descuento;
    private int id_empresa;
    private int id_categoria;
    @PrimaryKey
    @NonNull
    private int id;

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}